package cn.blog.service;

import cn.blog.model.pojo.Menu;
import cn.blog.model.pojo.Role;
import cn.blog.model.vo.MenuTreeVo;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.RoleMenuTreeSelectVo;
import cn.blog.model.vo.RoutersVo;
import cn.blog.utils.SystemConverter;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
public interface MenuService {

    /**
     * 根据角色id查询权限
     * @param roles
     * @param menuTypes
     * @return
     */
    List<Menu> findPermissionsByRolesAndType(List<Role> roles,List<Character> menuTypes);

    /**
     * 创建菜单树
     * @return
     */
    RoutersVo findRouterMenuTree();

    /**
     * 构建菜单树
     * @return
     */
    List<MenuTreeVo> treeSelect();


    /**
     * 根据角色id查询菜单树
     * @param id
     * @return
     */
    RoleMenuTreeSelectVo treeSelectById(Integer id);

    /**
     * 条件分页查询菜单
     * @param menu
     * @return
     */
    List<Menu>  findMenuList( Menu menu);

    /**
     * 新增菜单
     * @param menu
     */
    void addMenu(Menu menu);

    /**
     * 根据id查询未删除的菜单
     * @param menuId
     * @return
     */
    Menu getById(Integer menuId);

    /**
     * 更新菜单
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 参看该菜单是否有子菜单
     * @param menuId
     * @return
     */
    boolean hasChild(Integer menuId);

    /**
     * 删除
     * @param menuIds
     */
    void removeByIds(Integer[] menuIds);
}
