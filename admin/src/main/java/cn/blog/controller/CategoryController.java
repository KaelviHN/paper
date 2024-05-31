package cn.blog.controller;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.group.Update;
import cn.blog.model.ResponseResult;
import cn.blog.model.dto.CategoryDto;
import cn.blog.model.pojo.Category;
import cn.blog.model.vo.CategoryVo;
import cn.blog.model.vo.PageVo;
import cn.blog.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 分页查询
     * @param pageVo
     * @param category
     * @return
     */
    @GetMapping("list")
    public ResponseResult<PageVo> findCategoryPage(PageVo pageVo, Category category) {
        pageVo.checkParams();
        return ResponseResult.okResult(categoryService.getCategoryByPage(pageVo, category));
    }

    /**
     * 查询对应id的友链信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult<CategoryVo> findCategoryById(@PathVariable(value = "id") Integer id) {
        return ResponseResult.okResult(categoryService.findById(id));
    }

    /**
     * 更新分类信息
     */
    @PutMapping
    public ResponseResult updateCategory(@RequestBody @Validated(value = {Default.class, Update.class}) CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto);
        return ResponseResult.okResult();
    }

    /**
     * 删除友链
     * @param ids
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteCategoryById(@PathVariable("id") Integer[] ids) {
        // 判断是否有默认分类
        List<Integer> idList = Arrays.stream(ids).collect(Collectors.toList());
        if (idList.contains(Category.DEFAULT_CATEGORY)) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "默认分类无法删除");
        }
        categoryService.removeByIds(idList);
        return ResponseResult.okResult();
    }


    /**
     * 添加友链
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody @Validated CategoryDto categoryDto) {
        categoryService.addLink(categoryDto);
        return ResponseResult.okResult();
    }

    /**
     * 所有已发布的分类
     * 查询
     * @return
     */
    @GetMapping("listAllCategory")
    public ResponseResult<List<CategoryVo>> listAllCategory() {
        return ResponseResult.okResult(categoryService.getCategoryList());
    }

}