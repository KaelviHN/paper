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
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {

    private Integer id;

    private String title;

    private String content;

    private String summary;

    private Integer categoryId;

    private String thumbnail;

    private Character top;

    private Character status;

    private Integer viewCount;

    private Character canComment;

    private List<Integer> tags;

    private Date createTime;


}
