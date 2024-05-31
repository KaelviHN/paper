package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author KaelvihN
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "tb_category", uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "name_index"))
public class Category implements Serializable {
    public static final Character STATUS_NORMAL = '1';
    public static final Character STATUS_DELETE = '2';
    public static final Integer DEFAULT_CATEGORY = 1;
    public static final String CREATE_TIME = "createTime";
    public static final Character STATUS_BAN = '0';
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment(value = "分类id(1为默认分类,不能修改)")
    private Integer id;

    @Column(name = "name", length = 128, nullable = false, unique = true)
    @Comment(value = "分类名称")
    private String name;

    @Column(name = "pid")
    @Comment(value = "父分类id,没有父类为-1")
    private Integer pid;

    @Column(name = "description", length = 512)
    @Comment(value = "分类描述")
    private String description;

    @Column(name = "status")
    @Comment(value = "状态(0 禁用,1 正常 , 2 删除)")
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


}