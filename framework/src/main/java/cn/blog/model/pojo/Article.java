package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/1
 **/

@Getter
@Setter
@ToString
@Entity
@Accessors(chain = true)
@Table(name = "tb_article")
@EntityListeners(AuditingEntityListener.class)
public class Article implements Serializable {
    public static final Character STATUS_PUBLIC = '1';
    public static final Character STATUS_DRAFT = '0';
    public static final Character STATUS_DELETE = '2';
    public static final String VIEW_COUNT = "viewCount";
    public static final String TOP = "top";
    public static final String CREATE_TIME = "createTime";


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment(value = "文章id")
    private Integer id;

    @Column(name = "title")
    @Comment(value = "文章标题")
    private String title;

    @Column(name = "content", columnDefinition = "longtext")
    @Comment(value = "文章内容")
    private String content;

    @Column(name = "summary", length = 1024)
    @Comment(value = "文章摘要")
    private String summary;

    @Column(name = "category_id")
    @Comment(value = "所属分类id")
    private Integer categoryId;

    @Column(name = "thumbnail")
    @Comment(value = "缩略图")
    private String thumbnail;

    @Column(name = "status")
    @Comment(value = "状态(0 编辑 , 1 已发布 , 2 删除)")
    private Character status;

    @Column(name = "top", columnDefinition = "char(1) default 0")
    @Comment(value = "是否置顶(0 否 , 1 是)")
    private Character top;

    @Column(name = "can_comment", columnDefinition = "int default 1")
    @Comment(value = "是否允许评论(0 否 , 1 是)")
    private Character canComment;

    @Column(name = "view_count",columnDefinition = "int default 0")
    @Comment(value = "浏览量")
    private Integer viewCount;

    @CreatedBy
    @Column(name = "create_by")
    @Comment(value = "创建人")
    private Integer createBy;

    @CreatedDate
    @Column(name = "create_time")
    @Comment(value = "创建时间")
    private Date createTime;

    @LastModifiedBy
    @Column(name = "update_by")
    @Comment(value = "更新人")
    private Integer updateBy;

    @LastModifiedDate
    @Column(name = "update_time")
    @Comment(value = "更新时间")
    private Date updateTime;
}
