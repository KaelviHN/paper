package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author KaelvihN
 */
@Getter
@Setter
@Entity
@Table(name = "sys_role")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Role implements Serializable {

    public static final Integer ID_COMMON_USER = 2;
    private static final long serialVersionUID = 1L;

    public static final Character STATUS_DELETE = '2';

    public static final Character STATUS_NORMAL = '1';

    public static final Integer ID_DEFAULT_ROLE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment(value = "角色id")
    private Integer id;

    @Column(name = "role_name", nullable = false, length = 30)
    @Comment(value = "角色名称")
    private String roleName;

    @Column(name = "role_key", nullable = false, length = 100)
    @Comment(value = "角色权限字符串")
    private String roleKey;

    @Column(name = "role_sort", nullable = false)
    @Comment(value = "显示顺序")
    private Integer roleSort;

    @Column(name = "status", nullable = false)
    @Comment(value = "角色状态（0 禁用 , 1 正常 , 2 删除)")
    private Character status;

    @CreatedBy
    @Column(name = "create_by")
    @Comment(value = "创建人")
    private Integer createBy;

    @CreatedDate
    @Column(name = "create_time")
    @Comment(value = "创建时间")
    private Date createTime;

    @LastModifiedBy
    @Column(name = "update_by")
    @Comment(value = "更新人")
    private Integer updateBy;

    @LastModifiedDate
    @Column(name = "update_time")
    @Comment(value = "更新时间")
    private Date updateTime;

    @Column(name = "remark", length = 500)
    @Comment(value = "备注")
    private String remark;

}