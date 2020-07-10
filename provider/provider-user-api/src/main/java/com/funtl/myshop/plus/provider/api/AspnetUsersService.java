package com.funtl.myshop.plus.provider.api;

import com.funtl.myshop.plus.provider.domain.AspnetUsers;
import com.funtl.myshop.plus.provider.dto.UserListDto;
import com.funtl.myshop.plus.provider.dto.UserListQueryParams;
import com.github.pagehelper.PageInfo;

public interface AspnetUsersService {
    /**
     * 获取用户
     * @param username 用户名
     * @return
     */
    AspnetUsers get(String username);

    /**
     * 更新用户
     * 仅允许更新 邮箱、分机、状态
     * @param aspnetUsers
     * @return
     */
//    Integer updateUser(AspnetUsers aspnetUsers);

    /**
     * 修改密码
     * @param username 用户名
     * @param password 明文密码
     * @return
     */
//    Integer modifyPassword(String username, String password);

    /**
     * 根据用户名/状态获取用户信息
     * @param userListQueryParams
     * @return
     */
    PageInfo<UserListDto> selectUserListDto(UserListQueryParams userListQueryParams);

    /**
     * 新增用户数据
     * @param aspnetUsers
     * @return
     */
    Long insert(AspnetUsers aspnetUsers);

    /**
     * 根据userAuto删除数据
     * @param userAuto
     * @return
     */
    Integer deleteById(Long userAuto);

    /**
     * 编辑用户数据
     * @param aspnetUsers
     * @return
     */
    Integer update(AspnetUsers aspnetUsers);

    /**
     * 根据userAuto获取用户数据
     * @param userAuto
     * @return
     */
    AspnetUsers selectById(Long userAuto);
}

