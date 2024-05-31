package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author KaelvihN
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "tb_article_tag")
public class ArticleTag {
    @EmbeddedId
    private ArticleTagId id;

    //TODO [JPA Buddy] generate columns from DB
}