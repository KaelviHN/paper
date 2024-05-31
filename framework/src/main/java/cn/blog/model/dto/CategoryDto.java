package cn.blog.model.dto;

import cn.blog.annotation.AdminMatch;
import cn.blog.group.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/
@Data
public class CategoryDto {
    @AdminMatch(message = "默认分类不能修改", groups = {Update.class})
    @NotNull(message = "分类id不能为空", groups = {Update.class})
    private Integer id;
    @NotBlank(message = "分类名不能为空")
    private String name;
    private String description;
    @NotNull(message = "状态不能为空")
    private Character status;
}
