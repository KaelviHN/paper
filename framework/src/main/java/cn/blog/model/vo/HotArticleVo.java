package cn.blog.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KaelvihN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Integer id;
    /**
     * 标题
     */
    private String title;

    /**
     * 访问量
     */
    private Integer viewCount;
}
