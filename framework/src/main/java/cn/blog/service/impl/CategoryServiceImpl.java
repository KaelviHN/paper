package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.dto.CategoryDto;
import cn.blog.model.pojo.Category;
import cn.blog.model.pojo.QCategory;
import cn.blog.model.vo.CategoryVo;
import cn.blog.model.vo.PageVo;
import cn.blog.repository.CategoryRepository;
import cn.blog.service.ArticleService;
import cn.blog.service.CategoryService;
import cn.blog.utils.BeanCopyUtils;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/2
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ArticleService articleService;

    /**
     * 获取可用的分类
     * @return
     */
    @Override
    public List<CategoryVo> getCategoryList() {
        List<Category> categoryList = categoryRepository.findByStatus(Category.STATUS_NORMAL);
        return BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
    }

    /**
     * 分页查询友链
     * @param pageVo
     * @param category
     * @return
     */
    @Override
    public PageVo getCategoryByPage(PageVo pageVo, Category category) {
        PageRequest pageRequest = PageRequest.of(pageVo.getPageNum(), pageVo.getPageSize());
        QCategory qCategory = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();
        // link名称的动态查询
        if (StringUtils.isNotBlank(category.getName())) {
            builder.and(qCategory.name.contains(category.getName()));
        }
        // link状态的动态查询
        if (category.getStatus() != null) {
            builder.and(qCategory.status.eq(category.getStatus()));
        } else {
            builder.and(qCategory.status.ne(Category.STATUS_DELETE));
        }

        Page<Category> linkPage = categoryRepository.findAll(builder, pageRequest);
        return new PageVo(linkPage.toList(), (int) linkPage.getTotalElements());
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @Override
    public CategoryVo findById(Integer id) {
        Category category = categoryRepository.findByIdAndStatusNot(id, Category.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "分类不存在"));
        return BeanCopyUtils.copyBean(category, CategoryVo.class);
    }

    /**
     * 更新分类
     * @param categoryDto
     */
    @Override
    public void updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findByIdAndStatusNot(categoryDto.getId(), Category.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "分类不存在"));
        category.setName(categoryDto.getName())
                .setDescription(categoryDto.getDescription())
                .setStatus(categoryDto.getStatus());
        if (Category.STATUS_BAN.equals(category.getStatus())) {
            articleService.deleteArticleCategory(categoryDto.getId());
        }
        categoryRepository.save(category);
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void removeById(Integer id) {
        // 删除分类
        Category category = categoryRepository.findByIdAndStatusNot(id, Category.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "分类不存在"));
        category.setStatus(Category.STATUS_DELETE);
        categoryRepository.save(category);
        // 跟新文章分类信息
        articleService.deleteArticleCategory(id);
    }

    /**
     * 新增分类
     * @param categoryDto
     * @return
     */
    @Override
    public void addLink(CategoryDto categoryDto) {
        Category category = categoryRepository.findByName(categoryDto.getName())
                .orElseGet(Category::new);
        if (Category.STATUS_NORMAL.equals(category.getStatus()) || Category.STATUS_BAN.equals(category.getStatus())) {
            throw new SystemException(AppHttpCodeEnum.DATA_EXIST.getCode(), "分类已存在");
        }
        category.setName(categoryDto.getName())
                .setDescription(categoryDto.getDescription())
                .setStatus(Category.STATUS_NORMAL);
        categoryRepository.save(category);
    }

    /**
     * 删除分类
     * @param ids
     */
    @Override
    public void removeByIds(List<Integer> ids) {
        // 根据id查询未删除的category,并修改状态
        List<Category> categories =
                categoryRepository.findByIdInAndStatusNot(ids, Category.STATUS_DELETE)
                        .stream()
                        .map(category -> category.setStatus(Category.STATUS_DELETE))
                        .collect(Collectors.toList());
        // 更新状态
        categoryRepository.saveAll(categories);
    }
}
