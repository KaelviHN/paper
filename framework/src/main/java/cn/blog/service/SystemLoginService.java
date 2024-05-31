package cn.blog.service;

import cn.blog.model.pojo.User;
import cn.blog.model.vo.AdminUserInfoVo;
import cn.blog.model.vo.BlogUserLoginVo;

import java.util.Map;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
public interface SystemLoginService {
    /**
     * 后台登录
     * @param user
     * @return
     */
    Map<String,String> login(User user);

    /**
     * 后台退出
     * @return
     */
    void logout();

    /**
     * 获取用户信息
     * @return
     */
    AdminUserInfoVo getUserInfo();
}
