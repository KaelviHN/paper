package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.pojo.*;
import cn.blog.model.vo.MenuTreeVo;
import cn.blog.model.vo.MenuVo;
import cn.blog.model.vo.RoleMenuTreeSelectVo;
import cn.blog.model.vo.RoutersVo;
import cn.blog.repository.MenuRepository;
import cn.blog.repository.RoleMenuRepository;
import cn.blog.service.MenuService;
import cn.blog.service.RoleService;
import cn.blog.utils.BeanCopyUtils;
import cn.blog.utils.SecurityUtils;
import cn.blog.utils.SystemConverter;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuRepository menuRepository;
    @Resource
    private RoleMenuRepository roleMenuRepository;

    @Resource
    private RoleService roleService;

    /**
     * 根据角色id查询菜单
     * @param roles
     * @param menuTypes
     * @return
     */
    @Override
    public List<Menu> findPermissionsByRolesAndType(List<Role> roles, List<Character> menuTypes) {
        List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        // role -> roleIds => MenuRole -> menuIds => Menu -> permsList
        List<RoleMenu> roleMenus = roleMenuRepository.findById_RoleIdIn(roleIds);
        List<Integer> menuIds = roleMenus.stream().map(roleMenu -> roleMenu.getId().getMenuId()).collect(Collectors.toList());
        return menuRepository.findDistinctByIdInAndMenuTypeInAndStatusOrderByParentIdAscOrderNumAsc(menuIds, menuTypes, Menu.STATUS_NORMAL);
    }

    /**
     * 创建菜单树
     * @return
     */
    @Override
    public RoutersVo findRouterMenuTree() {
        // 获取用户基本信息
        User user = SecurityUtils.getLoginUser();
        // 获取角色
        List<Role> roles = roleService.findRolesByUser(user);
        // 获取菜单
        List<Character> menuTypes = Lists.newArrayList(Menu.MENU_TYPE_BUTTON, Menu.MENU_TYPE_MENU, Menu.MENU_TYPE_CATALOG);
        List<Menu> menus = findPermissionsByRolesAndType(roles, menuTypes);
        // 构建菜单树
        return buildTree(menus, Menu.TOP_MENU_PARENT_ID);
    }

    /**
     * 构建菜单树
     * @return
     */
    @Override
    public List<MenuTreeVo> treeSelect() {
        List<Menu> menus = menuRepository.findByStatusNot(Menu.STATUS_DELETE);
        return SystemConverter.buildMenuSelectTree(menus);

    }

    /**
     * 根据角色id查询菜单树
     * @param id
     * @return
     */
    @Override
    public RoleMenuTreeSelectVo treeSelectById(Integer id) {
        // 查询角色-菜单
        List<Integer> menuIds = roleMenuRepository.findById_RoleIdIn(Lists.newArrayList(id))
                .stream()
                .map(roleMenu -> roleMenu.getId().getMenuId())
                .collect(Collectors.toList());
        // 查询所有未删除的菜单
        List<MenuTreeVo> menuTreeVos = treeSelect();
        return new RoleMenuTreeSelectVo(menuIds, menuTreeVos);
    }

    /**
     * 条件分页查询菜单
     * @param menu
     * @return
     */
    @Override
    public List<Menu> findMenuList(Menu menu) {
        Sort sort = Sort.by(Sort.Direction.ASC, Menu.ORDER_NUM);
        QMenu qMenu = QMenu.menu;
        BooleanBuilder builder = new BooleanBuilder();
        // 菜单名称动态查询
        if (StringUtils.isNotBlank(menu.getName())) {
            builder.and(qMenu.name.containsIgnoreCase(menu.getName()));
        }
        // 状态动态查询
        if (menu.getStatus() == null) {
            builder.and(qMenu.status.ne(Menu.STATUS_DELETE));
        } else {
            builder.and(qMenu.status.eq(menu.getStatus()));
        }
        List<Menu> menus = Lists.newArrayList(menuRepository.findAll(builder, sort));
        return menus;
    }

    /**
     * 新增菜单
     * @param menu
     */
    @Override
    public void addMenu(Menu menu) {
        menuRepository.save(menu);
    }


    /**
     * 根据id查询未删除的菜单
     * @param menuId
     * @return
     */
    @Override
    public Menu getById(Integer menuId) {
        return menuRepository.findByIdAndStatusNot(menuId, Menu.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "该菜单不存在"));
    }

    /**
     * 更新菜单
     * @param menu
     */
    @Override
    public void updateMenu(Menu menu) {
        menuRepository.save(menu);
    }

    /**
     * 参看该菜单是否有子菜单
     * @param menuId
     * @return
     */
    @Override
    public boolean hasChild(Integer menuId) {
        return menuRepository.existsByParentIdAndStatusNot(menuId, Menu.STATUS_DELETE);
    }

    /**
     * 删除
     * @param menuIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(Integer[] menuIds) {
        List<Integer> ids = Arrays.stream(menuIds).collect(Collectors.toList());
        // 查询并修改status
        List<Menu> menus = menuRepository.findByIdInAndStatusNot(ids, Menu.STATUS_DELETE)
                .stream()
                .map(menu -> menu.setStatus(Menu.STATUS_DELETE))
                .collect(Collectors.toList());
        // 更新
        menuRepository.saveAll(menus);
        // 删除对应的Role-Menu
        roleMenuRepository.deleteById_MenuIdIn(ids);
    }

    /**
     * 构建菜单树
     * @param menus
     * @param parentId
     * @return
     */
    public RoutersVo buildTree(List<Menu> menus, Integer parentId) {
        List<MenuVo> menuVos = menus.stream()
                .filter(menu -> parentId.equals(menu.getParentId()))
                .map(menu -> {
                    MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
                    return menuVo.setChildren(getChildren(menuVo, menus));
                })
                .collect(Collectors.toList());
        return new RoutersVo(menuVos);
    }

    /**
     * 获取子菜单
     * @param menuVo
     * @param menus
     * @return
     */
    public List<MenuVo> getChildren(MenuVo menuVo, List<Menu> menus) {
        return menus.stream()
                // 菜单的parentId == menuVo的menu的id,则该菜单为menuVo中menu的子菜单
                .filter(menu -> menuVo.getId().equals(menu.getParentId()))
                // 属性拷贝
                .map(menu -> BeanCopyUtils.copyBean(menu, MenuVo.class))
                // 递归查询子菜单的菜单
                .map(vo -> vo.setChildren(getChildren(vo, menus)))
                .collect(Collectors.toList());
    }
}
