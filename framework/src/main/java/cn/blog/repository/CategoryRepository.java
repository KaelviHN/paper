package cn.blog.repository;

import cn.blog.model.pojo.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author KaelvihN
 */
public interface CategoryRepository extends BaseRepository<Category, Integer> {

    Optional<Category>  findByIdAndStatus(Integer id,Character status);


    List<Category> findByStatus(Character status);


    Optional<Category> findByIdAndStatusNot(Integer id, char status);


    Optional<Category> findByName(String name);

    List<Category> findByIdInAndStatusNot(List<Integer> ids, Character status);
}