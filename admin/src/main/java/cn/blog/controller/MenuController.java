package cn.blog.controller;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.model.ResponseResult;
import cn.blog.model.pojo.Menu;
import cn.blog.model.vo.MenuTreeVo;
import cn.blog.model.vo.RoleMenuTreeSelectVo;
import cn.blog.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeSelect")
    public ResponseResult<List<MenuTreeVo>> treeSelect() {
        return ResponseResult.okResult(menuService.treeSelect());
    }

    /**
     * 根据id构建
     * @return
     */
    @GetMapping("roleMenuTreeSelect/{roleId}")
    public ResponseResult<RoleMenuTreeSelectVo> treeSelectById(@PathVariable("roleId") Integer roleId) {
        return ResponseResult.okResult(menuService.treeSelectById(roleId));
    }

    /**
     * 分页&&条件查询menu
     * @param menu
     * @return
     */
    @GetMapping("list")
    public ResponseResult<List<Menu>> findMenuList(Menu menu) {
        return ResponseResult.okResult(menuService.findMenuList(menu));
    }


    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu) {
        menuService.addMenu(menu);
        return ResponseResult.okResult();
    }

    /**
     * 根据菜单编号获取详细信息
     * @param menuId
     */
    @GetMapping(value = "/{menuId}")
    public ResponseResult getInfo(@PathVariable Integer menuId) {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "修改菜单'" + menu.getName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateMenu(menu);
        return ResponseResult.okResult();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public ResponseResult removeMenu(@PathVariable("menuId") Integer[] menuIds) {
        List<Integer> ids = Arrays.stream(menuIds).collect(Collectors.toList());
        // 如果存在未删除的子菜单,则不能删除
        boolean existChild = ids.stream().anyMatch(id -> menuService.hasChild(id));
        if (existChild) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "存在子菜单不允许删除");
        }
        // 删除菜单
        menuService.removeByIds(menuIds);
        return ResponseResult.okResult();
    }
}