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
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Table(name = "tb_link")
public class Link implements Serializable {

    public static final String CREATE_TIME = "createTime";
    private static final long serialVersionUID = 1L;

    public static final char STATUS_REVIEW_PASS = '1';
    public static final char STATUS_DELETE = '3';

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment(value = "友链id")
    private Integer id;

    @Column(name = "name", length = 256)
    @Comment(value = "友链昵称")
    private String name;

    @Column(name = "logo", length = 256)
    @Comment(value = "logo地址")
    private String logo;

    @Column(name = "description", length = 512)
    @Comment(value = "自我描述")
    private String description;

    @Column(name = "address", length = 128)
    @Comment(value = "友链地址")
    private String address;

    @Column(name = "status")
    @Comment(value = "审核状态 (0 审核未通过 , 1 审核通过 , 2 未审核 , 3 删除)")
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