package cn.blog.service;

import cn.blog.model.dto.UserDto;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
public interface UserRoleService {
    /**
     * 通过userDto插入 User-Role
     * @param userDto
     */
    void addUserRoleByUserDto(UserDto userDto);

    /**
     * 根据角色id更新角色用户信息
     * @param roleIds
     */
    void updateUserRoleByRoleId(List<Integer> roleIds);
}
