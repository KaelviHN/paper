package cn.blog.service.impl;

import cn.blog.model.dto.UserDto;
import cn.blog.model.pojo.Role;
import cn.blog.model.pojo.UserRole;
import cn.blog.model.pojo.UserRoleId;
import cn.blog.repository.UserRoleRepository;
import cn.blog.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleRepository userRoleRepository;

    /**
     * 通过userDto插入 User-Role
     * @param userDto
     */
    @Override
    public void addUserRoleByUserDto(UserDto userDto) {
        List<UserRole> userRoles = userDto.getRoleIds()
                .stream()
                .map(roleId -> new UserRole().setId(new UserRoleId(userDto.getId(), roleId)))
                .collect(Collectors.toList());
        userRoleRepository.saveAll(userRoles);
    }

    /**
     * 根据角色id更新角色用户信息
     * @param roleIds
     */
    @Override
    public void updateUserRoleByRoleId(List<Integer> roleIds) {
        List<UserRole> userRoles = userRoleRepository.findById_RoleIdIn(roleIds)
                .stream()
                .map(userRole -> {
                    Integer userId = userRole.getId().getUserId();
                    return new UserRole().setId(new UserRoleId(userId, Role.ID_COMMON_USER));
                })
                .collect(Collectors.toList());
        userRoleRepository.saveAll(userRoles);
    }
}
