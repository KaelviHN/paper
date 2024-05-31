package cn.blog.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author KaelvihN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    public static final int DEFAULT_PAGE_NUM = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    @JsonIgnore
    private Integer pageSize;
    @JsonIgnore
    private Integer pageNum;
    private List rows;
    private Integer total;

    public void checkParams() {
        pageSize = pageSize == null || pageSize > 50 || pageSize < 0 ? DEFAULT_PAGE_SIZE : pageSize;
        pageNum = pageNum == null || pageNum < 1 ? DEFAULT_PAGE_NUM : pageNum - 1;
    }

    public PageVo(List rows, Integer total) {
        this.rows = rows;
        this.total = total;
    }

    public PageVo(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }
}
