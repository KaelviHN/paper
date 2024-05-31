package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author KaelvihN
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {

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
     * 所属分类名
     */
    private String categoryName;
    /**
     * 缩略图
     */
    private String thumbnail;


    /**
     * 访问量
     */
    private Integer viewCount;

    private Date createTime;


}
