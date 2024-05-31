package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KaelvihN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    private Integer id;

    private String name;

    private String logo;

    private String description;

    private String address;

    private Character status;


}
