package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author KaelvihN
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@ToString
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "tb_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Character ARTICLE_COMMENT = '0';
    public static final Character LINK_COMMENT = '1';
    public static final Character STATUS_NORMAL = '1';
    public static final Character STATUS_DELETE = '2';
    public static final Character STATUS_CHECK = '0';
    public static final Integer ROOT_COMMENT = -1;
    public static final String CREATE_TIME = "createTime";
    public static final String LINK_COMMENT_DEFAULT_VALUE = "友链评论";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @org.hibernate.annotations.Comment(value = "评论id")
    private Integer id;

    @Column(name = "type")
    @org.hibernate.annotations.Comment(value = "评论类型 (0 文章 , 1 友链)")
    private Character type;

    @Column(name = "article_id")
    @org.hibernate.annotations.Comment(value = "文章id")
    private Integer articleId;

    @Column(name = "root_id")
    @org.hibernate.annotations.Comment(value = "根评论id")
    private Integer rootId;

    @NotBlank(message = "评论不能为空")
    @Column(name = "content", length = 512)
    @org.hibernate.annotations.Comment(value = "评论内容")
    private String content;

    @Column(name = "status")
    @org.hibernate.annotations.Comment(value = "状态 (0 待审核 , 1 正常 , 2 已删除)")
    private Character status;

    @Column(name = "to_comment_user_id")
    @org.hibernate.annotations.Comment(value = "被评论人id")
    private Integer toCommentUserId;

    @Column(name = "to_comment_id")
    @org.hibernate.annotations.Comment(value = "评论人id")
    private Integer toCommentId;

    @CreatedBy
    @Column(name = "create_by")
    @org.hibernate.annotations.Comment(value = "创建人")
    private Integer createBy;

    @CreatedDate
    @Column(name = "create_time")
    @org.hibernate.annotations.Comment(value = "创建时间")
    private Date createTime;

    @LastModifiedBy
    @Column(name = "update_by")
    @org.hibernate.annotations.Comment(value = "更新人")
    private Integer updateBy;

    @LastModifiedDate
    @Column(name = "update_time")
    @org.hibernate.annotations.Comment(value = "更新时间")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(type, comment.type) && Objects.equals(articleId, comment.articleId) && Objects.equals(rootId, comment.rootId) && Objects.equals(content, comment.content) && Objects.equals(status, comment.status) && Objects.equals(toCommentUserId, comment.toCommentUserId) && Objects.equals(toCommentId, comment.toCommentId) && Objects.equals(createBy, comment.createBy) && Objects.equals(createTime, comment.createTime) && Objects.equals(updateBy, comment.updateBy) && Objects.equals(updateTime, comment.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, articleId, rootId, content, status, toCommentUserId, toCommentId, createBy, createTime, updateBy, updateTime);
    }
}