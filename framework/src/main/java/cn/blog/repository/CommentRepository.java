package cn.blog.repository;

import cn.blog.model.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author KaelvihN
 */

@Repository
public interface CommentRepository extends BaseRepository<Comment, Integer> {
    List<Comment> findByRootIdAndStatus(Integer id, Character status, Sort sort);
    Optional<Comment> findByIdAndStatusNot(Integer id, Character status);

    List<Comment> findByRootIdAndStatusNot(Integer id, Character status, Sort sort);
}