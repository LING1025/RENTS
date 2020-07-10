package com.funtl.myshop.plus.business.service;

import com.funtl.myshop.plus.provider.api.AspnetMembershipService;
import com.funtl.myshop.plus.provider.api.AspnetUsersService;
import com.funtl.myshop.plus.provider.domain.AspnetMembership;
import com.funtl.myshop.plus.provider.domain.AspnetUsers;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

// 优化了认证逻辑
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "$2a$10$WhCuqmyCsYdqtJvM0/J4seCU.xZQHe2snNE5VFUuBGUZWPbtdl3GG";

    @Reference(version = "1.0.0")
    private AspnetUsersService aspnetUsersService;

    @Reference(version = "1.0.0")
    private AspnetMembershipService aspnetMembershipService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //查询用户
        AspnetUsers aspnetUsers = aspnetUsersService.get(s);
        if(aspnetUsers == null){
            return null;
        }

        //查询用户密码
        AspnetMembership aspnetMembership = aspnetMembershipService.selectByUserId(aspnetUsers.getUserId());

        // 默认所有用户拥有 USER 权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);
        // 用户存在
        if (aspnetUsers != null) {
            return new User(aspnetUsers.getUsername(), aspnetMembership.getPasswordCode(), grantedAuthorities);
        }

        // 用户不存在
        else {
            return null;
        }
    }

    /*@Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //查询用户
        AspnetUsers aspnetUsers = aspnetUsersService.get(s);
        // 默认所有用户拥有 USER 权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);
        // 用户存在
        if (aspnetUsers != null) {
            return new User(aspnetUsers.getUsername(), aspnetUsers.getPassword(), grantedAuthorities);
        }

        // 用户不存在
        else {
            return null;
        }
    }*/
}
