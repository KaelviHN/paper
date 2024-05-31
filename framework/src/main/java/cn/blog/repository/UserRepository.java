package cn.blog.repository;

import cn.blog.model.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author KaelvihN
 */
@Repository
public interface UserRepository extends BaseRepository<User, Integer> {


    boolean existsByUsernameOrNickNameOrEmail(String username, String nickName, String email);

    Optional<User> findByUsername(String username);


    Optional<User> findByIdAndStatusNot(Integer id, Character status);

}