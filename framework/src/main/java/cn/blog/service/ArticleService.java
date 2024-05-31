package cn.blog.service;

import cn.blog.model.ResponseResult;
import cn.blog.model.dto.ArticleDto;
import cn.blog.model.pojo.Article;
import cn.blog.model.vo.ArticleDetailVo;
import cn.blog.model.vo.ArticleVo;
import cn.blog.model.vo.HotArticleVo;
import cn.blog.model.vo.PageVo;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/1
 **/
public interface ArticleService {

    /**
     * 查询热门文章
     * @return
     */
    List<HotArticleVo> hotArticleList();

    /**
     * 根据分类分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    PageVo articleList(Integer pageNum,Integer pageSize,Integer categoryId);

    /**
     * 根系文章浏览量
     * @param id
     * @return
     */
    void updateViewCount(Integer id);

    /**
     * 根据id 获取具体文章
     * @param id
     * @return
     */
    ArticleDetailVo getArticleDetail(Integer id);

    /**
     * 分页查询文章
     * @param pageVo
     * @param article
     * @return
     */
    PageVo findArticleByPage(PageVo pageVo, Article article);

    /**
     * 根据id查询文章信息
     * @param id
     * @return
     */
    ArticleVo getArticleById(Integer id);

    /**
     * 根据分类更新文章信息
     * @param id
     * @return
     */
    void deleteArticleCategory(Integer id);

    /**
     * 跟新文章
     * @param articleDto
     */
    void updateArticle(ArticleDto articleDto);

    /**
     * 新增文章
     * @param articleDto
     */
    void addArticle(ArticleDto articleDto);

    /**
     * 根据id删除文章
     * @param ids
     */
    void deleteArticleByIds(List<Integer> ids);
}
