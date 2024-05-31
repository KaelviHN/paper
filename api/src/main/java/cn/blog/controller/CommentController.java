package cn.blog.controller;


import cn.blog.model.ResponseResult;
import cn.blog.model.pojo.Comment;
import cn.blog.service.CommentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author KaelvihN
 */
@Valid
@RestController
@RequestMapping("comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 获取文章评论
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("commentList")
    public ResponseResult commentList(@RequestParam(value = "articleId") @NotNull @Min(1) Integer articleId,
                                      @RequestParam(value = "pageNum") @NotNull @Min(0) Integer pageNum,
                                      @RequestParam(value = "pageSize") @NotNull @Min(1) @Max(30) Integer pageSize) {
        pageNum = pageNum - 1;
        return ResponseResult.okResult(commentService.commentList(Comment.ARTICLE_COMMENT, articleId, pageNum, pageSize));
    }

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @PostMapping
    public ResponseResult addComment(@RequestBody @Valid Comment comment) {
        commentService.addComment(comment);
        return ResponseResult.okResult();
    }


    /**
     * 获取友链评论
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        pageNum = pageNum - 1;
        return ResponseResult.okResult(commentService.commentList(Comment.LINK_COMMENT, null, pageNum, pageSize));
    }
}
