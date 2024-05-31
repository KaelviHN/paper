package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

/**
 * @author KaelvihN
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {

    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 所属分类id
     */
    private Integer categoryId;
    /**
     * 所属分类名
     */
    private String categoryName;
    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 文章内容
     */
    private String content;
    /**
     * 访问量
     */
    private Integer viewCount;

    private List<String> tags;

    private Date createTime;

}
