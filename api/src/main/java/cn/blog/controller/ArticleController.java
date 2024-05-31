package cn.blog.controller;

import cn.blog.model.ResponseResult;
import cn.blog.service.ArticleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/1
 **/
@Valid
@RestController
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 查询热门文章
     * @return
     */
    @GetMapping("hotArticleList")
    public ResponseResult hotArticleList() {
        return ResponseResult.okResult(articleService.hotArticleList());
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("articleList")
    public ResponseResult articleList(@RequestParam(value = "pageNum") @NotNull @Min(0) Integer pageNum,
                                      @RequestParam(value = "pageSize") @NotNull @Min(1) @Max(30) Integer pageSize,
                                      @RequestParam("categoryId") @NotNull @Min(0) Integer categoryId) {
        return ResponseResult.okResult(articleService.articleList(pageNum - 1, pageSize, categoryId));
    }

    /**
     * 更新浏览量
     * @param id
     * @return
     */
    @PutMapping("updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Integer id) {
        articleService.updateViewCount(id);
        return ResponseResult.okResult();
    }

    /**
     * 根据id获取具体文章
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Integer id) {
        return ResponseResult.okResult(articleService.getArticleDetail(id));
    }
}
