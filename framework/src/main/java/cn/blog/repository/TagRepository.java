package cn.blog.repository;

import cn.blog.model.pojo.Tag;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author KaelvihN
 */
@Repository
public interface TagRepository extends BaseRepository<Tag, Integer> {

    List<Tag> findByIdInAndStatus(Integer[] ids,Character status);

    Optional<Tag> findByIdAndStatus(Integer id,Character status);

    Optional<Tag> findByName(String name);

    List<Tag> findByStatus(Character status);
}