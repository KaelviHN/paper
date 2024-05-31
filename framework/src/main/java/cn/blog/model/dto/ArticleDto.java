package cn.blog.model.dto;

import cn.blog.annotation.AdminMatch;
import cn.blog.group.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleDto {

    @NotNull(message = "id不能为空",groups = {Update.class})
    private Integer id;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String content;

    private String summary;

   private Integer categoryId;

    private String thumbnail;

    @NotNull(message = "是否置顶不能为空")
    private Character top;

    private Integer viewCount;

    @NotNull(message = "是否可评论不能为空")
    private Character canComment;

    private List<Integer> tags;

    @NotNull(message = "状态不能为空")
    private Character status;
}