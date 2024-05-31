package cn.blog.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Data
@Accessors(chain = true)
public class UserVo {

    private Integer id;

    private String username;

    private String nickName;

    private Character status;

    private String email;

    private String phonenumber;

    private Character sex;

    private String avatar;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

}
