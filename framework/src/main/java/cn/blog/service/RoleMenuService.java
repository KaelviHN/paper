package cn.blog.service;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
public interface RoleMenuService {

    /**
     * 更新RoleMenu
     * @param roleId
     * @param menuIds
     */
    void updateRoleMenu(Integer roleId, List<Integer> menuIds);
}
