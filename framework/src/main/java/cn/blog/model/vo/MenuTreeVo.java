package cn.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @author KaelvihN
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeVo {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Integer id;

    /**
     * 节点名称
     */
    private String label;

    private Integer parentId;

    /**
     * 子节点
     */

    private List<MenuTreeVo> children;
}
