package cn.blog.controller;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.group.Update;
import cn.blog.model.ResponseResult;
import cn.blog.model.dto.RoleDto;
import cn.blog.model.pojo.Role;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.RoleVo;
import cn.blog.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
     * 查询所有状态正常的role
     * @return
     */
    @GetMapping("listAllRole")
    public ResponseResult<RoleVo> listAllRole() {
        return ResponseResult.okResult(roleService.listAllRole());
    }

    /**
     * 条件分页查询角色
     * @param pageVo
     * @param role
     * @return
     */
    @GetMapping("list")
    public ResponseResult<PageVo> findRoleByPage(PageVo pageVo, Role role) {
        pageVo.checkParams();
        return ResponseResult.okResult(roleService.findRoleByPage(pageVo, role));
    }

    /**
     * 根据角色id获取信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult<RoleVo> getRoleById(@PathVariable("id") Integer id) {
        return ResponseResult.okResult(roleService.findRoleById(id));
    }

    /**
     * 更新角色信息
     * @param roleDto
     * @return
     */
    @PutMapping
    public ResponseResult updateRole(@RequestBody @Validated(value = {Default.class, Update.class}) RoleDto roleDto) {
        roleService.updateRole(roleDto);
        return ResponseResult.okResult();
    }

    /**
     * 更新角色状态
     * @param roleDto
     * @return
     */
    @PutMapping("changeStatus")
    public ResponseResult changStatus(@RequestBody @Validated(value = {Update.class}) RoleDto roleDto) {
        roleService.changStatus(roleDto);
        return ResponseResult.okResult();
    }

    /**
     * 根据角色id删除
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteRoleById(@PathVariable("id") Integer[] ids) {
        List<Integer> idList = Arrays.stream(ids).collect(Collectors.toList());
        if (idList.contains(Role.ID_DEFAULT_ROLE)) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "不能删除超级管理员");
        }
        roleService.deleteByRoleIds(idList);
        return ResponseResult.okResult();
    }
}