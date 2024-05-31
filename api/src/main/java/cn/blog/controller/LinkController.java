package cn.blog.controller;


import cn.blog.model.ResponseResult;
import cn.blog.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author KaelvihN
 */
@RestController
@RequestMapping("link")
public class LinkController {

    @Resource
    private LinkService linkService;

    /**
     * 获取所有已审核的友链
     * @return
     */
    @GetMapping("getAllLink")
    public ResponseResult getAllLink(){
        return ResponseResult.okResult(linkService.getAllLink());
    }
}
