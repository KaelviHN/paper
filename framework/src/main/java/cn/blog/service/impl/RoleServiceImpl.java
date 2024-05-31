package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.dto.RoleDto;
import cn.blog.model.pojo.QRole;
import cn.blog.model.pojo.Role;
import cn.blog.model.pojo.User;
import cn.blog.model.pojo.UserRole;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.RoleVo;
import cn.blog.repository.RoleRepository;
import cn.blog.repository.UserRoleRepository;
import cn.blog.service.RoleMenuService;
import cn.blog.service.RoleService;
import cn.blog.service.UserRoleService;
import cn.blog.utils.BeanCopyUtils;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 根据用户查询角色
     * @param user
     * @return
     */
    @Override
    public List<Role> findRolesByUser(User user) {
        // userId => UserRole -> roleIds => Role -> roleIds(normal)
        List<UserRole> userRoles = userRoleRepository.findById_UserId(user.getId());
        List<Integer> roleIds =
                userRoles.stream().map(userRole -> userRole.getId().getRoleId()).collect(Collectors.toList());
        return roleRepository.findByIdInAndStatus(roleIds, Role.STATUS_NORMAL);
    }

    /**
     * 查找所有正常状态的role
     * @return
     */
    @Override
    public List<RoleVo> listAllRole() {
        List<Role> roles = roleRepository.findByStatusNot(Role.STATUS_DELETE);
        return BeanCopyUtils.copyBeanList(roles, RoleVo.class);
    }

    /**
     * 条件分页查询
     * @param pageVo
     * @param role
     * @return
     */
    @Override
    public PageVo findRoleByPage(PageVo pageVo, Role role) {
        PageRequest pageRequest = PageRequest.of(pageVo.getPageNum(), pageVo.getPageSize());
        QRole qRole = QRole.role;
        BooleanBuilder builder = new BooleanBuilder();
        // roleName 动态查询
        if (StringUtils.isNoneBlank(role.getRoleName())) {
            builder.and(qRole.roleName.containsIgnoreCase(role.getRoleName()));
        }
        // status 动态查询
        if (role.getStatus() == null) {
            builder.and(qRole.status.ne(Role.STATUS_DELETE));
        } else {
            builder.and(qRole.status.eq(role.getStatus()));
        }
        Page<Role> rolePage = roleRepository.findAll(builder, pageRequest);
        return new PageVo(BeanCopyUtils.copyBeanList(rolePage.toList(), RoleVo.class), (int) rolePage.getTotalElements());
    }

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    @Override
    public Role findRoleById(Integer id) {
        return roleRepository.findByIdAndStatusNot(id, Role.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "角色不存在"));
    }

    /**
     * 更新用户
     * @param roleDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleDto roleDto) {
        // 查询该数据是否存在
        Role role = roleRepository.findByIdAndStatusNot(roleDto.getId(), Role.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "角色不存在"));
        // 数据拷贝
        BeanUtils.copyProperties(roleDto, role);
        // 更新Role
        roleRepository.save(role);
        // 更新roleMenu
        roleMenuService.updateRoleMenu(role.getId(), roleDto.getMenuIds());
    }

    /**
     * 更新角色状态
     * @param roleDto
     */
    @Override
    public void changStatus(RoleDto roleDto) {
        // 获取角色
        Role role = roleRepository.findByIdAndStatusNot(roleDto.getId(), Role.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "该角色不存在"));
        // 更新状态
        role.setStatus(roleDto.getStatus());
        roleRepository.save(role);
    }

    /**
     * 根据id删除角色
     * @param ids
     */
    @Override
    public void deleteByRoleIds(List<Integer> ids) {
        // 修改role状态
        List<Role> roles = roleRepository.findAllById(ids)
                .stream()
                .map(role -> role.setStatus(Role.STATUS_DELETE))
                .collect(Collectors.toList());
        roleRepository.saveAll(roles);
        // 修改user-role(改为普通用户)
        userRoleService.updateUserRoleByRoleId(ids);
    }
}
