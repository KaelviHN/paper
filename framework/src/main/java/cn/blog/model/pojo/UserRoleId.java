package cn.blog.model.pojo;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author KaelvihN
 */
@Data
@Embeddable
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id", nullable = false)
    @Comment(value = "用户id")
    private Integer userId;

    @Column(name = "role_id", nullable = false)
    @Comment(value = "角色id")
    private Integer roleId;

}