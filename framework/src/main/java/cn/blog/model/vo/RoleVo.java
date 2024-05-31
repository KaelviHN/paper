package cn.blog.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@Data
public class RoleVo {

    private Integer id;

    private String roleName;

    private Integer roleSort;

    private Date createTime;

    private Character status;

    private String roleKey;
}
