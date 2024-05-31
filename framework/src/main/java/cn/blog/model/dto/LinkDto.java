package cn.blog.model.dto;

import cn.blog.group.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/

@Data
public class LinkDto {
    @NotNull(message = "id不能为空",groups = {Update.class})
    private Integer id;
    @NotBlank(message = "名称不能为空")
    private String name;
    private String logo;
    @Pattern(regexp = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?",
            message = "友链地址不合法")
    @NotBlank(message = "友链地址不能为空")
    private String address;
    private String description;
    @NotNull(message = "状态不能为空")
    private Character status;

}
