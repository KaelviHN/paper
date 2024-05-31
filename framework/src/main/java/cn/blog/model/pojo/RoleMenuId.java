package cn.blog.model.pojo;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author KaelvihN
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "role_id", nullable = false)
    @Comment(value = "角色id")
    private Integer roleId;

    @Column(name = "menu_id", nullable = false)
    @Comment(value = "菜单id")
    private Integer menuId;

}