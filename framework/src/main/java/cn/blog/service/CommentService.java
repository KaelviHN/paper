package cn.blog.service;

import cn.blog.model.ResponseResult;
import cn.blog.model.pojo.Comment;
import cn.blog.model.vo.CommentVo;
import cn.blog.model.vo.PageVo;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/2
 **/
public interface CommentService {
    /**
     * 查询评论列表
     * @param commentType
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageVo commentList(Character commentType, Integer articleId, Integer pageNum, Integer pageSize);

    /**
     * 新增评论
     * @param comment
     */
    void addComment(Comment comment);

    /**
     * 分页查询评论
     * @param pageVo
     * @param status
     * @return
     */
    PageVo pageComment(PageVo pageVo, Character status);

    /**
     * 更新状态
     * @param commentVo
     */
    void changeStatus(CommentVo commentVo);

    /**
     * 根据id删除
     * @param ids
     */
    void removeByIds(Integer[] ids);
}
