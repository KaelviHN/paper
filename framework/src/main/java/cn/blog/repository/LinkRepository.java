package cn.blog.repository;

import cn.blog.model.pojo.Link;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
@Repository
public interface LinkRepository extends BaseRepository<Link, Integer> {
    List<Link> findByStatus(Character status);

    Optional<Link> findByIdAndStatusNot(Integer id, Character status);

    List<Link> findByIdInAndStatusNot(List<Integer> ids, char status);
}
