package cn.blog.controller;

import cn.blog.group.Update;
import cn.blog.model.ResponseResult;
import cn.blog.model.dto.ArticleDto;
import cn.blog.model.pojo.Article;
import cn.blog.model.vo.ArticleVo;
import cn.blog.model.vo.PageVo;
import cn.blog.service.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /**
     * 分页查询article
     * @param pageVo
     * @param article
     * @return
     */
    @GetMapping("list")
    public ResponseResult<PageVo> findArticleByPage(PageVo pageVo, Article article) {
        pageVo.checkParams();
        return ResponseResult.okResult(articleService.findArticleByPage(pageVo, article));
    }

    /**
     * 根据id查询文章信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult<ArticleVo> getArticleById(@PathVariable(value = "id") Integer id) {
        return ResponseResult.okResult(articleService.getArticleById(id));
    }

    /**
     * 更新文章
     * @param articleDto
     * @return
     */
    @PutMapping
    public ResponseResult updateArticle(@RequestBody @Validated(value = {Default.class, Update.class}) ArticleDto articleDto) {
        articleService.updateArticle(articleDto);
        return ResponseResult.okResult();
    }

    /**
     * 新增文章
     * @param articleDto
     * @return
     */
    @PostMapping
    public ResponseResult addArticle(@RequestBody @Validated ArticleDto articleDto) {
        articleService.addArticle(articleDto);
        return ResponseResult.okResult();
    }


    @DeleteMapping("{id}")
    public ResponseResult deleteArticleById(@PathVariable("id") Integer[] ids) {
        articleService.deleteArticleByIds(Arrays.stream(ids).collect(Collectors.toList()));
        return ResponseResult.okResult();
    }
}