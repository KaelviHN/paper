package cn.blog.utils;


import cn.blog.model.pojo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author KaelvihN
 */
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static User getLoginUser() {
        //未登录 getAuthentication().getPrincipal()=anonymousUser
        Authentication authentication = getAuthentication();
        Object principal = authentication.getPrincipal();
        return (User) principal;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取登录用户id
     * @return
     */
    public static Integer getUserId() {
        return getLoginUser().getId();
    }
}