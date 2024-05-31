package cn.blog.controller;

import cn.blog.annotation.SystemLog;
import cn.blog.model.ResponseResult;
import cn.blog.model.pojo.User;
import cn.blog.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author KaelvihN
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("userInfo")
    public ResponseResult userInfo() {
        return ResponseResult.okResult(userService.userInfo());
    }

    @PutMapping("userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return ResponseResult.okResult();
    }

    @PostMapping("register")
    public ResponseResult register(@RequestBody @Validated User user) {
        userService.register(user);
        return ResponseResult.okResult();
    }
}
