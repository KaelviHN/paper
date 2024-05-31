package cn.blog.model.dto;

import cn.blog.group.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/5
 **/
@Data
public class TagDto {
    @NotNull(message = "id不能为空",groups = {Update.class})
    private Integer id;
    @NotBlank(message = "标签名不能为空")
    private String name;
    private String remark;

}
