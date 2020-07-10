package com.funtl.myshop.plus.provider.service;

import com.funtl.myshop.plus.commons.utils.PageInfoUtils;
import com.funtl.myshop.plus.provider.api.AspnetMembershipService;
import com.funtl.myshop.plus.provider.api.AspnetUsersService;
import com.funtl.myshop.plus.provider.domain.AspnetMembership;
import com.funtl.myshop.plus.provider.domain.AspnetUsers;
import com.funtl.myshop.plus.provider.domain.UserList;
import com.funtl.myshop.plus.provider.dto.UserListDto;
import com.funtl.myshop.plus.provider.dto.UserListQueryParams;
import com.funtl.myshop.plus.provider.mapper.AspnetUsersMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service(version = "1.0.0")
public class AspnetUsersServiceImpl implements AspnetUsersService {

    @Resource
    private AspnetUsersMapper aspnetUsersMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public AspnetUsers get(String username) {
        Example example = new Example(AspnetUsers.class);
        example.createCriteria().andEqualTo("username",username).andEqualTo("applicationId","73663109-DDA2-4C2D-8311-337946B5C373");
        return aspnetUsersMapper.selectOneByExample(example);
    }

    /*@Override
    public Integer updateUser(AspnetUsers aspnetUsers) {
        // 获取原始用户信息
        AspnetUsers oldUser = get(aspnetUsers.getUsername());
        AspnetMembership aspnetMembership =
        //仅更新 邮箱、分机、状态
        oldUser.setEmail(aspnetUsers.getEmail());
        oldUser.setMobilePIN(aspnetUsers.getMobilePIN());
        oldUser.setIsOn(aspnetUsers.getIsOn());
        return aspnetUsersMapper.updateByPrimaryKey(oldUser);
    }*/

   /* @Override
    public Integer modifyPassword(String username, String password) {
        AspnetUsers aspnetUsers = get(username);
        aspnetUsers.setPassword(passwordEncoder.encode(password));
        return aspnetUsersMapper.updateByPrimaryKey(aspnetUsers);
    }*/

    @Override
    public PageInfo<UserListDto> selectUserListDto(UserListQueryParams userListQueryParams) {
        PageHelper.startPage(userListQueryParams.getPageNum(),userListQueryParams.getPageSize());
        PageInfo<UserList> pageInfo = new PageInfo<>(aspnetUsersMapper.selectUserListDto(userListQueryParams));
        PageInfo<UserListDto> result = PageInfoUtils.pageInfo2PageInfoDTO(pageInfo,UserListDto.class);
        return result;
    }

    @Override
    public Long insert(AspnetUsers aspnetUsers) {
        Integer i = aspnetUsersMapper.insertUseGeneratedKeys(aspnetUsers);
        return i == 1 ? aspnetUsers.getUserAuto() : 0;
    }

    @Override
    public Integer deleteById(Long userAuto) {
        return aspnetUsersMapper.deleteByPrimaryKey(userAuto);
    }

    @Override
    public Integer update(AspnetUsers aspnetUsers) {
        return aspnetUsersMapper.updateByPrimaryKeySelective(aspnetUsers);
    }

    @Override
    public AspnetUsers selectById(Long userAuto) {
        return aspnetUsersMapper.selectByPrimaryKey(userAuto);
    }
}

