package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.dto.UserDto;
import cn.blog.model.pojo.QUser;
import cn.blog.model.pojo.Role;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.UserInfoAndRoleIdsVo;
import cn.blog.model.vo.UserInfoVo;
import cn.blog.model.vo.UserVo;
import cn.blog.repository.RoleRepository;
import cn.blog.repository.UserRepository;
import cn.blog.repository.UserRoleRepository;
import cn.blog.service.UserRoleService;
import cn.blog.service.UserService;
import cn.blog.utils.BeanCopyUtils;
import cn.blog.utils.SecurityUtils;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public UserInfoVo userInfo() {
        // 根据id获取用户信息
        Integer userId = SecurityUtils.getUserId();
        Optional<User> optional = userRepository.findById(userId);
        User user = optional.orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND));
        // 信息拷贝
        return BeanCopyUtils.copyBean(user, UserInfoVo.class);
    }

    /**
     * 更新用户信息
     * @param user
     */
    @Override
    public void updateUserInfo(User user) {
        User u = userRepository.findById(user.getId())
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "该用户不存在"));
        u.setNickName(user.getNickName());
        u.setAvatar(user.getAvatar());
        u.setSex(user.getSex());
        userRepository.save(u);
    }

    /**
     * 注册用户
     * @param user
     */
    @Override
    public void register(User user) {
        // 不看重复信息的校验
        if (userRepository.existsByUsernameOrNickNameOrEmail(user.getUsername(), null, null)) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (userRepository.existsByUsernameOrNickNameOrEmail(null, user.getNickName(), null)) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (userRepository.existsByUsernameOrNickNameOrEmail(null, null, user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        userRepository.save(user);
    }

    /**
     * 条件分页查询用户
     * @param pageVo
     * @param user
     * @return
     */
    @Override
    public PageVo findUserByPage(PageVo pageVo, User user) {
        // 分页
        PageRequest pageRequest = PageRequest.of(pageVo.getPageNum(), pageVo.getPageSize());
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        // 用户名称动态查询
        if (StringUtils.isNotBlank(user.getUsername())) {
            builder.and(qUser.username.containsIgnoreCase(user.getUsername()));
        }
        // 手机号码动态查询
        if (StringUtils.isNotBlank(user.getPhonenumber())) {
            builder.and(qUser.phonenumber.containsIgnoreCase(user.getPhonenumber()));
        }
        // 状态动态查询
        if (user.getStatus() != null) {
            builder.and(qUser.phonenumber.containsIgnoreCase(user.getPhonenumber()));
        } else {
            builder.and(qUser.status.ne(User.STATUS_DELETE));
        }
        Page<User> userPage = userRepository.findAll(builder, pageRequest);
        return new PageVo(BeanCopyUtils.copyBeanList(userPage.toList(), UserVo.class), (int) userPage.getTotalElements());
    }

    /**
     * 新增用户
     * @param userDto
     */
    @Override
    public void saveUser(UserDto userDto) {
        // 校验该用户名是否存在
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        // 插入用户
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        // 密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user = userRepository.save(user);
        // 插入User-Role
        userRoleService.addUserRoleByUserDto(userDto.setId(user.getId()));
    }

    /**
     * 更新用户状态
     * @param userDto
     */
    @Override
    public void changeStatus(UserDto userDto) {
        User user = userRepository.findByIdAndStatusNot(userDto.getId(), User.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "改用户不存在"));
        user.setStatus(userDto.getStatus());
        userRepository.save(user);
    }

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Override
    public UserInfoAndRoleIdsVo findByUserId(Integer userId) {
        // 根据id查询用户信息
        User user = userRepository.findByIdAndStatusNot(userId, User.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "改用户不存在"));
        // 查询UserRole
        List<Integer> roleIds = userRoleRepository.findById_UserId(userId)
                .stream()
                .map(userRole -> userRole.getId().getRoleId())
                .collect(Collectors.toList());
        // 查询所有未被删除的角色信息
        List<Role> roles = roleRepository.findByStatusNot(Role.STATUS_DELETE);
        return new UserInfoAndRoleIdsVo(user, roles, roleIds);
    }

    /**
     * 更新用户
     * @param userDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserDto userDto) {
        // 角色校验
        User user = userRepository.findByIdAndStatusNot(userDto.getId(), User.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "用户不存在"));
        // 更新用户
        User userBean = BeanCopyUtils.copyBean(userDto, User.class);
        userBean.setCreateBy(user.getCreateBy()).setCreateTime(user.getCreateTime()).setPassword(user.getPassword());
        userRepository.save(userBean);
        // 更新用户-角色
        userRoleRepository.deleteById_UserId(userDto.getId());
        userRoleService.addUserRoleByUserDto(userDto);
    }

    /**
     * 根据idList删除
     * @param idList
     */
    @Override
    public void removeByIds(List<Integer> idList) {
        // 修改user状态
        List<User> users = userRepository.findAllById(idList)
                .stream()
                .map(user -> user.setStatus(User.STATUS_DELETE))
                .collect(Collectors.toList());
        // 删除user-role
        userRoleRepository.deleteById_UserIdIn(idList);
        userRepository.saveAll(users);
    }


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByUsername(username);
        User user = optional.orElseThrow(() -> new RuntimeException("用户不存在"));
        return user;
    }
}
