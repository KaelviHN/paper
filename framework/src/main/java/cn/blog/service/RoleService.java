package cn.blog.service;

import cn.blog.model.dto.RoleDto;
import cn.blog.model.pojo.Role;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.RoleVo;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
public interface RoleService {
    /**
     * 根据用户查询角色
     * @param user
     * @return
     */
    List<Role> findRolesByUser(User user);

    /**
     * 查找所有正常状态的role
     * @return
     */
    List<RoleVo> listAllRole();

    /**
     * 条件分页查询
     * @param pageVo
     * @param role
     * @return
     */
    PageVo findRoleByPage(PageVo pageVo,Role role);

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    Role findRoleById(Integer id);

    /**
     * 更新用户
     * @param roleDto
     */
    void updateRole(RoleDto roleDto);

    /**
     * 更新角色状态
     * @param roleDto
     */
    void changStatus(RoleDto roleDto);

    /**
     * 根据id删除角色
     * @param id
     */
    void deleteByRoleIds(List<Integer> id);
}
