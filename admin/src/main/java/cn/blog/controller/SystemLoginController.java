package cn.blog.controller;

import cn.blog.model.ResponseResult;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.AdminUserInfoVo;
import cn.blog.model.vo.RoutersVo;
import cn.blog.service.MenuService;
import cn.blog.service.SystemLoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/

@RestController
public class SystemLoginController {

    @Resource
    private SystemLoginService systemLoginService;

    @Resource
    private MenuService menuService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        return ResponseResult.okResult(systemLoginService.login(user));
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getUserInfo() {
        return ResponseResult.okResult(systemLoginService.getUserInfo());
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        return ResponseResult.okResult(menuService.findRouterMenuTree());
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        systemLoginService.logout();
        return ResponseResult.okResult();
    }
}