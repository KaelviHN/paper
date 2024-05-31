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
import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/1
 **/

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 菜单类型 - 菜单
     */
    public static final Character MENU_TYPE_MENU = 'M';
    /**
     * 菜单类型 - 按钮
     */
    public static final Character MENU_TYPE_BUTTON = 'B';
    /**
     * 菜单类型 - 目录
     */
    public static final Character MENU_TYPE_CATALOG = 'C';
    /**
     * 状态 - 正常
     */
    public static final Character STATUS_NORMAL = '1';
    /**
     * 父菜单id - 顶级菜单id
     */
    public static final Integer TOP_MENU_PARENT_ID = 0;
    /**
     * 状态 - 删除
     */
    public static final Character STATUS_DELETE = '2';

    public static final String ORDER_NUM = "orderNum";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment(value = "菜单id")
    private Integer id;

    @Column(name = "name", length = 50, nullable = false)
    @Comment(value = "菜单名称")
    private String name;

    @Column(name = "parent_id", columnDefinition = "int default 0")
    @Comment(value = "父菜单id")
    private Integer parentId;

    @Column(name = "order_num", columnDefinition = "int default 0")
    @Comment(value = "显示顺序")
    private Integer orderNum;

    @Column(name = "path")
    @Comment(value = "路由地址")
    private String path;

    @Column(name = "component")
    @Comment(value = "组件路径")
    private String component;

    @Column(name = "frame", columnDefinition = "char(1) default 0")
    @Comment(value = "是否为外链(0 否 , 1 是)")
    private Character frame;

    @Column(name = "menuType", columnDefinition = "char(1) default ''")
    @Comment(value = "菜单类型(M 菜单 , C 目录 , B 按钮)")
    private Character menuType;

    @Column(name = "visible")
    @Comment(value = "菜单是否可见 (0 隐藏 , 1 显示)")
    private Character visible;

    @Column(name = "status")
    @Comment(value = "菜单状态(0 停用 , 1 正常 , 2 删除)")
    private Character status;

    @Column(name = "perms",columnDefinition = "varchar(100)")
    @Comment(value = "权限标识")
    private String perms;

    @Column(name = "icon",columnDefinition = "varchar(100) default '#'")
    @Comment(value = "菜单图标")
    private String icon;

    @Column(name = "remark",columnDefinition = "varchar(500) default ''")
    @Comment(value = "备注")
    private String remark;

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
