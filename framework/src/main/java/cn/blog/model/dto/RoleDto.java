package cn.blog.model.dto;

import cn.blog.annotation.AdminMatch;
import cn.blog.group.Update;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/

@Data
public class RoleDto {
    @AdminMatch(message = "不能修改超级管理员", groups = {Update.class})
    @NotNull(message = "id不能为空", groups = {Update.class})
    private Integer id;
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    @NotBlank(message = "权限字符不能为空")
    private String roleKey;
    @Min(value = 0, message = "角色排序最小值为1")
    @NotNull(message = "角色排序不能为空")
    private Integer roleSort;
    @NotNull(message = "状态不能为空", groups = {Update.class})
    private Character status;
    private String remark;
    private Integer createBy;
    private Date createTime;
    private List<Integer> menuIds;
}
