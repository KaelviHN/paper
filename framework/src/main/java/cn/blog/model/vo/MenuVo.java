package cn.blog.model.vo;

import cn.blog.model.pojo.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;


/**
 * @author KaelvihN
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuVo {
    private Integer id;

    private String name;

    private Integer parentId;


    private Integer orderNum;


    private String path;


    private String component;

    private Character frame;

    private Character menuType;


    private Character visible;


    private Character status;


    private String perms;

    private String icon;


    private String remark;


    private Integer createBy;


    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private List<MenuVo> children;
}
