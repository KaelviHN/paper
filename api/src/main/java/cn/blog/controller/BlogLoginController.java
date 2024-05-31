package cn.blog.controller;


import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.ResponseResult;
import cn.blog.model.pojo.User;
import cn.blog.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author KaelvihN
 */
@RestController
public class BlogLoginController {
    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return ResponseResult.okResult(blogLoginService.login(user));
    }

    @PostMapping("logout")
    public ResponseResult logout() {
        blogLoginService.logout();
        return ResponseResult.okResult();
    }
}
