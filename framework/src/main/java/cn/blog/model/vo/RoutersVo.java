package cn.blog.model.vo;

import cn.blog.model.pojo.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author KaelvihN
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<MenuVo> menus;
}
