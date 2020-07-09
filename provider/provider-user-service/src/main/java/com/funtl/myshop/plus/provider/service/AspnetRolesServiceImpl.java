package com.funtl.myshop.plus.provider.service;

import com.funtl.myshop.plus.provider.domain.AspnetRoles;
import com.funtl.myshop.plus.provider.domain.RolesNameList;

import javax.annotation.Resource;
import com.funtl.myshop.plus.provider.mapper.AspnetRolesMapper;
import com.funtl.myshop.plus.provider.api.AspnetRolesService;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(version = "1.0.0")
public class AspnetRolesServiceImpl implements AspnetRolesService{

    @Resource
    private AspnetRolesMapper aspnetRolesMapper;

    @Override
    public List<RolesNameList> selectRoleName(String roleName) {
        return aspnetRolesMapper.selectRoleName(roleName);
    }

    @Override
    public AspnetRoles selectByRoleName(String roleName) {
        Example example = new Example(AspnetRoles.class);
        example.createCriteria().andEqualTo("roleName",roleName);
        return aspnetRolesMapper.selectOneByExample(example);
    }

    @Override
    public List<RolesNameList> selectByRolesId(List<Long> roleIds) {
        return aspnetRolesMapper.selectByRolesId(roleIds);
    }

    @Override
    public List<AspnetRoles> selectByUserId(Object userId) {
        return aspnetRolesMapper.selectByUserId(userId);
    }
}
