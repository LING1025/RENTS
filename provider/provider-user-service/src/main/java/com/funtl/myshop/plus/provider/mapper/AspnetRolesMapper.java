package com.funtl.myshop.plus.provider.mapper;

import com.funtl.myshop.plus.provider.domain.AspnetRoles;
import com.funtl.myshop.plus.provider.domain.RolesNameList;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface AspnetRolesMapper extends MyMapper<AspnetRoles> {
    List<RolesNameList> selectRoleName(@Param("roleName") String roleName);

    List<RolesNameList> selectByRolesId(@Param("param") List<Long> roleIds);

    List<AspnetRoles> selectByUserId(@Param("param") Object userId);

}
