package cn.blog.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author KaelvihN
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {

    private Integer id;

    private String nickName;

    private String avatar;

    private Character sex;

    private String email;


}