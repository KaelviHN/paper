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
public class CategoryVo {
    private Integer id;
    private String name;
    private String description;
    private Character status;

}
