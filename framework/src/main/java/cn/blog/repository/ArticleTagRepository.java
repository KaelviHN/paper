package cn.blog.repository;

import cn.blog.model.pojo.ArticleTag;
import cn.blog.model.pojo.ArticleTagId;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/6
 **/
@Repository
public interface ArticleTagRepository extends BaseRepository<ArticleTag, ArticleTagId> {

    int deleteById_TagId(Integer tagId);


    List<ArticleTag> findById_ArticleId(Integer articleId);

    int deleteById_ArticleId(Integer articleId);
}
