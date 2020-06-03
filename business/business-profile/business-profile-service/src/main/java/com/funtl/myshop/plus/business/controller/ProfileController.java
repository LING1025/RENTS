package com.funtl.myshop.plus.business.controller;

import com.funtl.myshop.plus.business.dto.UserDTO;
import com.funtl.myshop.plus.business.dto.params.PasswordParam;
import com.funtl.myshop.plus.business.dto.params.ProfileParam;
import com.funtl.myshop.plus.commons.dto.ResponseResult;
import com.funtl.myshop.plus.provider.api.UserService;
import com.funtl.myshop.plus.provider.domain.User;
import com.funtl.myshop.plus.business.dto.UserParamDto;
import com.funtl.myshop.plus.provider.dto.UserListQueryParam;
import com.funtl.myshop.plus.provider.dto.UserListQueryParams;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 个人信息管理
 */

@Api(tags = "个人信息管理相关操作")
@RestController
@RequestMapping(value = "profile")
public class ProfileController {
    @Reference(version = "1.0.0")
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 获取个人信息
     * @param username 用户名
     * @return
     */
    @ApiOperation(value = "获取个人信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string", paramType = "path")
    @GetMapping(value = "info/{username}")
    public ResponseResult<UserDTO> info(@PathVariable String username){
        User user = userService.get(username);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        if(user == null){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"用户不存在",null);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"获取个人信息",userDTO);
    }

    /**
     * 更新个人信息
     * @param profileParam
     * @return
     */
    @ApiOperation(value = "更新个人信息")
    @PostMapping(value = "update")
    public ResponseResult<Void> update(@RequestBody ProfileParam profileParam) {
        User newUmsAdmin = new User();
        BeanUtils.copyProperties(profileParam, newUmsAdmin);
        Integer result = userService.update(newUmsAdmin);

        // 成功
        if (result > 0) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "更新个人信息成功");
        }

        // 失败
        else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "更新个人信息失败");
        }
    }

    /**
     * 修改密码
     * @param passwordParam
     * @return
     */
    @ApiOperation(value = "修改密码")
    @PostMapping(value = "modify/password")
    public ResponseResult<Void> modifyPassword(@RequestBody PasswordParam passwordParam) {
        User umsAdmin = userService.get(passwordParam.getUsername());

        // 旧密码正确
        if (passwordEncoder.matches(passwordParam.getOldPassword(), umsAdmin.getPassword())) {
            int result = userService.modifyPassword(umsAdmin.getUsername(), passwordParam.getNewPassword());
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

    @ApiOperation(value = "获取员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "isAdmin", value = "是否是管理员", required = false, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "byte", paramType = "path")
    })
    @GetMapping(value = "query")
    public ResponseResult<List<User>> query(@RequestParam(name = "username", required = false) String username,
                                            @RequestParam(name = "isAdmin", required = false) Integer isAdmin,
                                            @RequestParam(name = "status", required = false) Byte status) {
        UserListQueryParam userListQueryParam = new UserListQueryParam(username, isAdmin, status);
        List<User> list = userService.selectUserListDto(userListQueryParam);
        if(list.size() == 0){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "未查询到员工信息", null);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功", list);
    }

    /**
     * 新增员工账号
     * @param user
     * @return
     */
    @ApiOperation(value = "新增员工账号")
    @PostMapping(value = "insert")
    public ResponseResult<User> insert(@RequestBody User user) {
        User admin = userService.get(user.getUsername());

        if (admin != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户名已存在，请重新输入", null);
        }

        int result = userService.insert(user);

        // 注册成功
        if (result > 0) {
            User addUser = userService.get(user.getUsername());
            return new ResponseResult<User>(ResponseResult.CodeStatus.OK, "保存成功", addUser);
        }
        return new ResponseResult<User>(ResponseResult.CodeStatus.FAIL, "保存失败", null);
    }

    @ApiOperation(value = "修改账户信息")
    @PutMapping(value = "updateUser")
    public ResponseResult<String> updateUser(@RequestBody UserParamDto userParamDto) {
        User user = userService.selectById(userParamDto.getUserId());
        if (user == null){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "未找到账户信息", null);
        }
        BeanUtils.copyProperties(userParamDto,user);
        Integer i = userService.updateUser(user);
        if(i == 1){
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "修改成功", null);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "修改失败", null);
    }

    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "string", paramType = "path")
    @PutMapping(value = "reset/{username}")
    public ResponseResult<String> resetPwd(@PathVariable("username")String username) {
        User user = userService.get(username);
        if(user == null){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "账号不存在", null);
        }
        user.setPassword(passwordEncoder.encode("123456"));
        Integer i = userService.updateUser(user);
        if(i == 0){
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "重置密码失败", null);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "重置后密码:123456", null);
    }

    public ResponseResult<User> patch(Byte status, Integer userId) {
        User user = userService.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "未查询到员工信息", null);
        }
        user.setStatus(status);
        Integer i = userService.update(user);
        if (i == 1) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "修改成功", null);
        } else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "修改失败", null);
        }
    }

    @ApiOperation(value = "正常状态")
    @ApiImplicitParam(name = "userId", value = "员工id", required = true, dataType = "int", paramType = "path")
    @PatchMapping("/start/{userId}")
    public ResponseResult<User> patchStart(@PathVariable(value = "userId") Integer userId) {
        return patch((byte)1, userId);
    }

    @ApiOperation(value = "停用状态")
    @ApiImplicitParam(name = "userId", value = "员工id", required = true, dataType = "int", paramType = "path")
    @PatchMapping("/stop/{userId}")
    public ResponseResult<User> patchStop(@PathVariable(value = "userId") Integer userId) {
        return patch((byte)0, userId);
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "userId", value = "员工id", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/delete/{userId}")
    public ResponseResult<User> delete(@PathVariable(value = "userId") Integer userId) {
        return patch((byte)2, userId);
    }
}
