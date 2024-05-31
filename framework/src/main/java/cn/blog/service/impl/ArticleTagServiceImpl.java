package cn.blog.service.impl;

import cn.blog.model.dto.ArticleDto;
import cn.blog.model.pojo.ArticleTag;
import cn.blog.model.pojo.ArticleTagId;
import cn.blog.repository.ArticleTagRepository;
import cn.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/
@Service
public class ArticleTagServiceImpl implements ArticleTagService {
    @Resource
    private ArticleTagRepository articleTagRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleTag(ArticleDto articleDto) {
        // 根据文章id删除
        articleTagRepository.deleteById_ArticleId(articleDto.getId());
        // 重新插入新的Article-Tag
        saveByArticleDto(articleDto);
    }

    /**
     * 根据ArticleDto保存
     * @param articleDto
     */
    @Override
    public void saveByArticleDto(ArticleDto articleDto) {
        // 重新插入新的Article-Tag
        List<ArticleTag> articleTagIds = articleDto.getTags()
                .stream()
                .map(tagId -> new ArticleTag().setId(new ArticleTagId(articleDto.getId(), tagId)))
                .collect(Collectors.toList());
        articleTagRepository.saveAll(articleTagIds);
    }

    /**
     * 根据id查询标签
     * @param articleId
     * @return
     */
    @Override
    public List<ArticleTag> findByArticleId(Integer articleId) {
        return articleTagRepository.findById_ArticleId(articleId);
    }


}
