package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author KaelvihN
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "sys_user")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable, UserDetails {
    public static final Integer ID_DEFAULT_USER = 1;

    public static final Character ADMIN_TYPE = '1';

    public static final Character STATUS_DELETE = '2';

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment(value = "用户id")
    private Integer id;

    @Column(name = "username", nullable = false, length = 64)
    @Comment(value = "用户名")
    private String username;


    @Column(name = "nick_name", nullable = false, length = 64)
    @Comment(value = "用户昵称")
    private String nickName;

    @Column(name = "password", nullable = false, length = 64)
    @Comment(value = "用户密码")
    private String password;

    @Column(name = "type")
    @Comment(value = "用户类型 (0 普通用户 , 1 管理员)")
    private Character type;

    @Column(name = "status")
    @Comment(value = "用户状态 (0 停用 , 1 正常 ,2 删除)")
    private Character status;

    @Column(name = "email", length = 64)
    @Comment(value = "邮箱")
    private String email;

    @Column(name = "phonenumber", length = 32)
    @Comment(value = "手机号")
    private String phonenumber;

    @Column(name = "sex")
    @Comment(value = "用户性别（0 男 , 1 女 , 2 未知)")
    private Character sex;

    @Column(name = "avatar", length = 128)
    @Comment(value = "用户图标")
    private String avatar;

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

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}