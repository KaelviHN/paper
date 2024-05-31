package cn.blog.model.dto;

import cn.blog.annotation.AdminMatch;
import cn.blog.group.Insert;
import cn.blog.group.Update;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@Data
@Accessors(chain = true)
public class UserDto {

    @AdminMatch(message = "默认用户不能修改", groups = {Update.class})
    @NotNull(message = "id不能为空", groups = {Update.class})
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名称长度必须介于 2 和 20 之间")
    private String username;

    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "您的手机号不合法")
    private String phonenumber;

    @NotBlank(message = "邮箱不能为空")
    @Email(regexp = "^\\w+(-+.\\w+)*@\\w+(-.\\w+)*.\\w+(-.\\w+)*$", message = "您输入的邮箱不合法")
    private String email;

    @NotNull(message = "邮箱不能为空")
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotNull(message = "密码不能为空", groups = {Insert.class})
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{6,20}$",
            message = "密码必须包含英文字母和数字,且长度必须介于 6 和 20 之间")
    private String password;

    @NotNull(message = "性别不能为空")
    private Character sex;

    @NotNull(message = "状态不能为空", groups = {Default.class, Update.class})
    private Character status;

    private List<Integer> roleIds;
}
