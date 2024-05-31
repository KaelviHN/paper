package cn.blog.service.impl;

import cn.blog.constants.SystemConstants;
import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.BlogUserLoginVo;
import cn.blog.model.vo.UserInfoVo;
import cn.blog.service.BlogLoginService;
import cn.blog.utils.BeanCopyUtils;
import cn.blog.utils.JwtUtil;
import cn.blog.utils.RedisCache;
import cn.blog.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/2
 **/
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    private AuthenticationManager manager;

    @Resource
    private RedisCache redisCache;

    /**
     * 前台登录
     * @param user
     * @return
     */
    @Override
    public BlogUserLoginVo login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = manager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        //获取userid 生成token
        User loginUser = (User) authenticate.getPrincipal();
        String userId = loginUser.getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.PREFIX_BLOG + userId, loginUser,30, TimeUnit.MINUTES);
        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser, UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return vo;
    }

    /**
     * 前台退出
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
        redisCache.deleteObject(SystemConstants.PREFIX_BLOG + userId);
    }
}
