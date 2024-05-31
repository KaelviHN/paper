package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {
    /**
     * 权限集合
     */
    private List<String> permissions;
    /**
     * 角色集合
     */
    private List<String> roles;

    /**
     * 用户具体信息
     */
    private UserInfoVo userInfoVo;
}
