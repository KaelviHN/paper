package cn.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_tag", uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "name_index"))
public class Tag implements Serializable {

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;

    public static final Character STATUS_NORMAL = '1';

    public static final Character STATUS_DELETE = '0';

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment(value = "标签id")
    private Integer id;

    @Column(name = "name", length = 128, unique = true, nullable = false)
    @Comment(value = "标签名称")
    private String name;

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

    @Column(name = "status")
    @Comment(value = "状态 (0  删除, 1 正常)")
    private Character status;

    @Column(name = "remark", length = 500)
    @Comment(value = "备注")
    private String remark;

}