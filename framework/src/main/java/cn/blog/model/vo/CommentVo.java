package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author KaelvihN
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Integer id;
    private Integer articleId;
    private Integer rootId;
    private String content;
    private Integer toCommentUserId;
    private String toCommentUserName;
    private Integer toCommentId;
    private Integer createBy;
    private Date createTime;
    private Character status;

    /**
     * 评论人的昵称
     */
    private String username;
    /**
     * 子评论
     */
    private List<CommentVo> children;
    /**
     * 文章名 / 友链评论
     */
    private String name;
}
