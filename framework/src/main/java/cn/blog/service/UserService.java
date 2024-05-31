package cn.blog.service;

import cn.blog.model.dto.UserDto;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.UserInfoAndRoleIdsVo;
import cn.blog.model.vo.UserInfoVo;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
public interface UserService {
    /**
     * 获取用户信息
     * @return
     */
    UserInfoVo userInfo();

    /**
     * 更新用户信息
     * @param user
     */
    void updateUserInfo(User user);

    /**
     * 注册用户
     * @param user
     */
    void register(User user);

    /**
     * 条件分页查询用户
     * @param pageVo
     * @param user
     * @return
     */
    PageVo findUserByPage(PageVo pageVo, User user);

    /**
     * 新增用户
     * @param userDto
     */
    void saveUser(UserDto userDto);

    /**
     * 更新用户状态
     * @param userDto
     */
    void changeStatus(UserDto userDto);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    UserInfoAndRoleIdsVo findByUserId(Integer userId);

    /**
     * 更新用户
     * @param userDto
     */
    void updateUser(UserDto userDto);

    /**
     * 根据idList删除
     * @param idList
     */
    void removeByIds(List<Integer> idList);

}
