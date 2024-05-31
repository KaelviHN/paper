package cn.blog.controller;


import cn.blog.model.ResponseResult;
import cn.blog.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author KaelvihN
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 获取分列表类
     * @return
     */
    @GetMapping("getCategoryList")
    public ResponseResult getCategoryList() {
        return ResponseResult.okResult(categoryService.getCategoryList());
    }
}
