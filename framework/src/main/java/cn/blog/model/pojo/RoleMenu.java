package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author KaelvihN
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "sys_role_menu")
public class RoleMenu {
    @EmbeddedId
    private RoleMenuId id;

}