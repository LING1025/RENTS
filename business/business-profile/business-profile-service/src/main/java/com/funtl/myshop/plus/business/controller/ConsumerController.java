package com.funtl.myshop.plus.business.controller;

import com.funtl.myshop.plus.business.BusinessException;
import com.funtl.myshop.plus.business.BusinessStatus;
import com.funtl.myshop.plus.business.dto.UsersDto;
import com.funtl.myshop.plus.business.dto.UsersParamDto;
import com.funtl.myshop.plus.business.dto.params.PasswordParam;
import com.funtl.myshop.plus.commons.dto.ResponseResult;
import com.funtl.myshop.plus.provider.api.AspnetMembershipService;
import com.funtl.myshop.plus.provider.api.AspnetUsersService;
import com.funtl.myshop.plus.provider.api.EmpBaseService;
import com.funtl.myshop.plus.provider.domain.AspnetMembership;
import com.funtl.myshop.plus.provider.domain.AspnetUsers;
import com.funtl.myshop.plus.provider.domain.EmpBase;
import com.funtl.myshop.plus.provider.dto.UserListDto;
import com.funtl.myshop.plus.provider.dto.UserListQueryParams;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户信息管理
 */

@Api(tags = "用户信息管理相关操作")
@RestController
@RequestMapping(value = "consumer")
public class ConsumerController {
    @Reference(version = "1.0.0")
    private AspnetUsersService aspnetUsersService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Reference(version = "1.0.0")
    private AspnetMembershipService aspnetMembershipService;

    @Reference(version = "1.0.0")
    private EmpBaseService empBaseService;

    @ApiOperation(value = "获取个人信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string", paramType = "path")
    @GetMapping(value = "info/{username}")
    public ResponseResult<UsersDto> info(@PathVariable String username){
        AspnetUsers aspnetUsers = aspnetUsersService.get(username);
        if(aspnetUsers == null){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"用户不存在",null);
        }
        UsersDto usersDto = new UsersDto();
        BeanUtils.copyProperties(aspnetUsers,usersDto);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"获取个人信息",usersDto);
    }

    /*@ApiOperation(value = "更新个人信息")
    @PostMapping(value = "update")
    public ResponseResult<Void> update(@RequestBody ConsumerParam consumerParam) {
        AspnetUsers newUmsAdmin = new AspnetUsers();
        BeanUtils.copyProperties(consumerParam, newUmsAdmin);
        Integer result = aspnetUsersService.updateUser(newUmsAdmin);

        // 成功
        if (result > 0) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "更新个人信息成功");
        }

        // 失败
        else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "更新个人信息失败");
        }
    }*/

    @ApiOperation(value = "修改个人密码")
    @PostMapping(value = "modify/password")
    public ResponseResult<Void> modifyPassword(@RequestBody PasswordParam passwordParam) {
        AspnetUsers aspnetUsers = aspnetUsersService.get(passwordParam.getUsername());
        if(aspnetUsers == null){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"用户不存在",null);
        }
        AspnetMembership aspnetMembership = aspnetMembershipService.selectByUserId(aspnetUsers.getUserId());

        // 旧密码正确
        if (passwordEncoder.matches(passwordParam.getOldPassword(), aspnetMembership.getPasswordCode())) {
            Integer result = aspnetMembershipService.modifyPassword(aspnetUsers.getUsername(), passwordParam.getNewPassword());
            if (result > 0) {
                return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "修改密码成功");
            }
        }

        // 旧密码错误
        else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "旧密码不匹配，请重试");
        }

        return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "修改密码失败");
    }

    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "isOn", value = "状态", required = false, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "笔数", required = false, dataType = "int", paramType = "path")
    })
    @GetMapping(value = "query")
    public ResponseResult<PageInfo<UserListDto>> query(@RequestParam(name = "username", required = false) String username,
                                                       @RequestParam(name = "isOn", required = false) Integer isOn,
                                                       @RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum,
                                                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        UserListQueryParams userListQueryParams = new UserListQueryParams(username,isOn,pageNum,pageSize);
        PageInfo<UserListDto> pageInfo = aspnetUsersService.selectUserListDto(userListQueryParams);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功", pageInfo);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping(value = "updateUser")
    public ResponseResult<String> updateUser(@RequestBody UsersParamDto usersParamDto) {
        AspnetUsers aspnetUsers = aspnetUsersService.selectById(usersParamDto.getUserAuto());
        if (aspnetUsers == null){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户不存在", null);
        }

        AspnetMembership aspnetMembership = aspnetMembershipService.selectByUserId(aspnetUsers.getUserId());
        aspnetMembership.setMobilePIN(usersParamDto.getMobilePIN());
        aspnetMembership.setEmail(usersParamDto.getEmail());
        Integer i = aspnetMembershipService.update(aspnetMembership);
        if(i == 0){
            throw new BusinessException(BusinessStatus.UPDATE_FAILURE);
        }

        EmpBase empBase = empBaseService.selectById(aspnetUsers.getEmpBaseAuto());
        empBase.setIsOn(usersParamDto.getIsOn());
        empBase.setMDT(new Date());
        Integer i1 = empBaseService.update(empBase);
        if(i1 == 0){
            throw new BusinessException(BusinessStatus.UPDATE_FAILURE);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "修改成功", null);
    }

    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string", paramType = "path")
    @PutMapping(value = "reset/{username}")
    public ResponseResult<String> resetPwd(@PathVariable("username")String username) {
        AspnetUsers aspnetUsers = aspnetUsersService.get(username);
        if(aspnetUsers == null){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户不存在", null);
        }
        AspnetMembership aspnetMembership = aspnetMembershipService.selectByUserId(aspnetUsers.getUserId());
        aspnetMembership.setPasswordCode(passwordEncoder.encode("123456"));
        Integer i = aspnetMembershipService.update(aspnetMembership);
        if(i == 0){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "重置密码失败", null);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "重置后密码:123456", null);
    }

    /*public ResponseResult<AspnetUsers> patch(Integer isOn, Long userAuto) {
        AspnetUsers aspnetUsers = aspnetUsersService.selectById(userAuto);
        if (aspnetUsers == null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户不存在", null);
        }
        aspnetUsers.setIsOn(isOn);
        Integer i = aspnetUsersService.update(aspnetUsers);
        if (i == 0) {
            throw new BusinessException(BusinessStatus.UPDATE_FAILURE);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "修改成功", null);
    }


    @ApiOperation(value = "停用状态")
    @ApiImplicitParam(name = "userAuto", value = "用户id", required = true, dataType = "long", paramType = "path")
    @PatchMapping("/stop/{userAuto}")
    public ResponseResult<AspnetUsers> patchStop(@PathVariable(value = "userAuto") Long userAuto) {
        return patch(0, userAuto);
    }

    @ApiOperation(value = "正常状态")
    @ApiImplicitParam(name = "userAuto", value = "用户id", required = true, dataType = "long", paramType = "path")
    @PatchMapping("/start/{userAuto}")
    public ResponseResult<AspnetUsers> patchStart(@PathVariable(value = "userAuto") Long userAuto) {
        return patch(1, userAuto);
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "userAuto", value = "用户id", required = true, dataType = "long", paramType = "path")
    @DeleteMapping("/delete/{userAuto}")
    public ResponseResult<AspnetUsers> delete(@PathVariable(value = "userAuto") Long userAuto) {
        return patch(2, userAuto);
    }

*/
}
