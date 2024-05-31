package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KaelvihN
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Integer id;

    private String name;

    private String remark;

}
