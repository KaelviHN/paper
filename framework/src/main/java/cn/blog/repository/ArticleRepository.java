package cn.blog.repository;

import cn.blog.model.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/1
 **/
@Repository
public interface ArticleRepository extends BaseRepository<Article, Integer> {
    Page<Article> findByStatus(Character status, Pageable pageable);

    Page<Article> findByStatusAndCategoryId(Character status, Integer categoryId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Article a set a.viewCount = ?1 where a.id = ?2")
    int updateViewCountById(@NonNull Integer viewCount, @NonNull Integer id);

    Optional<Article> findByIdAndStatus(Integer id, Character status);

    List<Article> findByIdInAndStatusNot(List<Integer> id, Character status);

    List<Article> findByCategoryId(Integer category);

    Optional<Article> findByIdAndStatusNot(Integer id, Character status);

    List<Article> findByStatusAndIdIn(Character status, List<Integer> ids);
}
