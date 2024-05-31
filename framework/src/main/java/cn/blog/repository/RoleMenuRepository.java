package cn.blog.repository;

import cn.blog.model.pojo.RoleMenu;
import cn.blog.model.pojo.RoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/4
 **/
@Repository
public interface RoleMenuRepository extends BaseRepository<RoleMenu, RoleMenuId> {

    List<RoleMenu> findById_RoleIdIn(Collection<Integer> roleIds);

    void deleteById_RoleId(Integer roleId);

    void deleteById_MenuIdIn(List<Integer> menuIds);
}
