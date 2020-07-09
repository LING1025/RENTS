package com.funtl.myshop.plus.provider.service;

import javax.annotation.Resource;

import com.funtl.myshop.plus.provider.api.AspnetUsersService;
import com.funtl.myshop.plus.provider.domain.AspnetMembership;
import com.funtl.myshop.plus.provider.domain.AspnetUsers;
import com.funtl.myshop.plus.provider.mapper.AspnetMembershipMapper;
import com.funtl.myshop.plus.provider.api.AspnetMembershipService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Service(version = "1.0.0")
public class AspnetMembershipServiceImpl implements AspnetMembershipService{

    @Resource
    private AspnetMembershipMapper aspnetMembershipMapper;

    @Reference(version = "1.0.0")
    private AspnetUsersService aspnetUsersService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Object insert(AspnetMembership aspnetMembership) {
        Integer i = aspnetMembershipMapper.insert(aspnetMembership);
        return i == 1 ? aspnetMembership.getUserId() : 0;
    }

    @Override
    public AspnetMembership selectByUserId(Object userId) {
        return aspnetMembershipMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Object deleteByUserId(Object userId) {
        return aspnetMembershipMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public Integer update(AspnetMembership aspnetMembership) {
        return aspnetMembershipMapper.updateByPrimaryKeySelective(aspnetMembership);
    }

    @Override
    public Integer modifyPassword(String username, String password) {
        AspnetUsers aspnetUsers = aspnetUsersService.get(username);
        AspnetMembership aspnetMembership = selectByUserId(aspnetUsers.getUserId());
        aspnetMembership.setPasswordCode(passwordEncoder.encode(password));
        aspnetMembership.setLastPasswordChangedDate(new Date());
        aspnetMembership.setComment("备注");
        return aspnetMembershipMapper.updateByPrimaryKey(aspnetMembership);
    }
}
