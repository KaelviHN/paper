package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuPageVo {
    private Integer id;

    //e
    private String menuName;

    private Integer parentId;

    private Integer orderNum;

    private String path;

    private String component;

    //e
    private Character isFrame;

    private String menuType;

    private String visible;

    private Character status;

    private String perms;

    private String icon;

    private String remark;



}