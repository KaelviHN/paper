package cn.blog.service.impl;

import cn.blog.model.pojo.RoleMenu;
import cn.blog.model.pojo.RoleMenuId;
import cn.blog.repository.RoleMenuRepository;
import cn.blog.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Resource
    private RoleMenuRepository roleMenuRepository;


    /**
     * 更新RoleMenu
     * @param roleId
     * @param menuIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenu(Integer roleId, List<Integer> menuIds) {
        // 根据角色id删除
        roleMenuRepository.deleteById_RoleId(roleId);
        // 重新插入roleMenu
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu().setId(new RoleMenuId(roleId, menuId)))
                .collect(Collectors.toList());
        roleMenuRepository.saveAll(roleMenus);
    }
}
