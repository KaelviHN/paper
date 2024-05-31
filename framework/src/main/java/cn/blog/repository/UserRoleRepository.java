package cn.blog.repository;

import cn.blog.model.pojo.UserRole;
import cn.blog.model.pojo.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, UserRoleId> {

    List<UserRole> findById_UserId(Integer userId);

    void deleteById_UserId(Integer userId);

    void deleteById_UserIdIn(List<Integer> idList);

    void deleteById_RoleIdIn(List<Integer> ids);

    List<UserRole> findById_RoleIdIn(List<Integer> roleIds);
}
