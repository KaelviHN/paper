package cn.blog.service;

import cn.blog.model.dto.CategoryDto;
import cn.blog.model.pojo.Category;
import cn.blog.model.vo.CategoryVo;
import cn.blog.model.vo.PageVo;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/2
 **/
public interface CategoryService {
    /**
     * 获取可用的分类
     * @return
     */
    List<CategoryVo> getCategoryList();

    /**
     * 分页查询友链
     * @param pageVo
     * @param category
     * @return
     */
    PageVo getCategoryByPage(PageVo pageVo, Category category);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    CategoryVo findById(Integer id);

    /**
     * 更新分类
     * @param categoryDto
     */
    void updateCategory(CategoryDto categoryDto);

    /**
     * 删除分类
     * @param id
     */
    void removeById(Integer id);

    /**
     * 新增分类
     * @param categoryDto
     * @return
     */
    void addLink(CategoryDto categoryDto);

    /**
     * 删除分类
     * @param ids
     */
    void removeByIds(List<Integer> ids);

}
