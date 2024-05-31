package cn.blog.service;

import cn.blog.model.dto.ArticleDto;
import cn.blog.model.pojo.ArticleTag;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/
public interface ArticleTagService {

    /**
     * 更新Article-Tag
     * @param articleDto
     */
    void updateArticleTag(ArticleDto articleDto);

    /**
     * 根据ArticleDto保存
     * @param articleDto
     */
    void saveByArticleDto(ArticleDto articleDto);

    /**
     * 根据id查询标签
     * @param articleId
     * @return
     */
    List<ArticleTag> findByArticleId(Integer articleId);
}
