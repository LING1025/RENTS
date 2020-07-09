package com.funtl.myshop.plus.provider.api;

import com.funtl.myshop.plus.provider.domain.AspnetRoles;
import com.funtl.myshop.plus.provider.domain.RolesNameList;

import java.util.List;

public interface AspnetRolesService{
    /**
     * 获取角色名称
     * @param roleName
     * @return
     */
    List<RolesNameList> selectRoleName(String roleName);

    /**
     * 根据角色名获取角色信息
     * @param roleName
     * @return
     */
    AspnetRoles selectByRoleName(String roleName);

    /**
     * 根据角色id集合获取角色信息
     * @param roleIds
     * @return
     */
    List<RolesNameList> selectByRolesId(List<Long> roleIds);

    /**
     * 根据UserId获取角色信息
     * @param userId
     * @return
     */
    List<AspnetRoles> selectByUserId(Object userId);
}
