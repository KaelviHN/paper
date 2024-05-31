package cn.blog.service;

import cn.blog.model.ResponseResult;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.BlogUserLoginVo;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/2
 **/
public interface BlogLoginService {
    /**
     * 前台登录
     * @param user
     * @return
     */
    BlogUserLoginVo login(User user);

    /**
     * 前台退出
     * @return
     */
    void logout();
}
