package cn.blog.repository;

import cn.blog.model.pojo.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Repository
public interface MenuRepository extends BaseRepository<Menu, Integer> {

    List<Menu> findDistinctByIdInAndMenuTypeInAndStatusOrderByParentIdAscOrderNumAsc(Collection<Integer> menuIds, Collection<Character> menuType, Character status);

    List<Menu> findByStatusNot(Character status);

    Optional<Menu> findByIdAndStatusNot(Integer menuId, Character status);

    boolean existsByParentIdAndStatusNot(Integer parentId, Character status);

    List<Menu> findByIdInAndStatusNot(List<Integer> ids, Character status);
}
