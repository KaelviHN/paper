package cn.blog.controller;

import cn.blog.model.ResponseResult;
import cn.blog.model.vo.CommentVo;
import cn.blog.model.vo.PageVo;
import cn.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/9
 **/
@Valid
@RestController
@RequestMapping("/content/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    /**
     * 动态分页查询
     * @param pageVo
     * @param status
     * @return
     */
    @GetMapping("list")
    public ResponseResult<PageVo> pageComment(PageVo pageVo, Character status) {
        pageVo.checkParams();
        return ResponseResult.okResult(commentService.pageComment(pageVo, status));
    }

    /**
     * 更新状态
     * @param commentVo
     * @return
     */
    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody CommentVo commentVo) {
        commentService.changeStatus(commentVo);
        return ResponseResult.okResult();
    }

    @DeleteMapping("{id}")
    public ResponseResult removeCommentByIds(@PathVariable("id") Integer[] ids) {
        commentService.removeByIds(ids);
        return ResponseResult.okResult();
    }
}