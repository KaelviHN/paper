package cn.blog.repository;

import cn.blog.model.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Repository
public interface RoleRepository extends BaseRepository<Role, Integer> {
    List<Role> findByIdInAndStatus(List<Integer> roleIds, Character status);

    List<Role> findByStatusNot(Character status);

    Optional<Role> findByIdAndStatusNot(Integer id, Character status);

}
