package cn.blog.service.impl;

import cn.blog.constants.SystemConstants;
import cn.blog.model.pojo.Menu;
import cn.blog.model.pojo.Role;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.AdminUserInfoVo;
import cn.blog.model.vo.BlogUserLoginVo;
import cn.blog.model.vo.UserInfoVo;
import cn.blog.repository.UserRoleRepository;
import cn.blog.service.MenuService;
import cn.blog.service.RoleService;
import cn.blog.service.SystemLoginService;
import cn.blog.utils.BeanCopyUtils;
import cn.blog.utils.JwtUtil;
import cn.blog.utils.RedisCache;
import cn.blog.utils.SecurityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Service
public class SystemLoginServiceImpl implements SystemLoginService {

    @Resource
    private AuthenticationManager manager;

    @Resource
    private RedisCache redisCache;
    @Resource
    private  RoleService roleService;
    @Resource
    private MenuService menuService;

    /**
     * 后台登录
     * @param user
     * @return
     */
    @Override
    public Map<String, String> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = manager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        User loginUser = (User) authenticate.getPrincipal();
        String userId = loginUser.getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.PREFIX_ADMIN_LOG + userId, loginUser);
        //把token封装 返回
        HashMap<@Nullable String, @Nullable String> map = Maps.newHashMap();
        map.put("token", jwt);
        return map;
    }

    /**
     * 后台退出
     * @return
     */
    @Override
    public void logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = (User) authentication.getPrincipal();
        //获取userid
        Integer userId = loginUser.getId();
        //删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.PREFIX_ADMIN_LOG + userId);
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public AdminUserInfoVo getUserInfo() {
        // 获取用户基本信息
        User user = SecurityUtils.getLoginUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 获取角色
        List<Role> roles = roleService.findRolesByUser(user);
        List<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        // 查询权限
        List<Character> menuTypes = Lists.newArrayList(Menu.MENU_TYPE_BUTTON, Menu.MENU_TYPE_MENU);
        List<Menu> menus = menuService.findPermissionsByRolesAndType(roles,menuTypes);
        List<String> permissions = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
        return new AdminUserInfoVo(permissions,roleNames,userInfoVo);
    }
}
