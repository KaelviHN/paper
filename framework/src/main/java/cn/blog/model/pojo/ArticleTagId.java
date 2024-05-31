package cn.blog.model.pojo;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author KaelvihN
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleTagId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "article_id", nullable = false)
    @Comment(value = "文章id")
    private Integer articleId;

    @Column(name = "tag_id", nullable = false)
    @Comment(value = "标签id")
    private Integer tagId;


}