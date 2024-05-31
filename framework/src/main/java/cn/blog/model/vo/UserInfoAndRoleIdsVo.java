package cn.blog.model.vo;


import cn.blog.model.pojo.Role;
import cn.blog.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author KaelvihN
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo {
    private User user;
    private List<Role> roles;
    private List<Integer> roleIds;



}
