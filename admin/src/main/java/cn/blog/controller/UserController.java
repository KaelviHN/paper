package cn.blog.controller;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.group.Insert;
import cn.blog.group.Update;
import cn.blog.model.ResponseResult;
import cn.blog.model.dto.UserDto;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.UserInfoAndRoleIdsVo;
import cn.blog.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/7
 **/
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 条件分页查询用户
     * @param pageVo
     * @param user
     * @return
     */
    @GetMapping("list")
    public ResponseResult<PageVo> findUserByPage(PageVo pageVo, User user) {
        pageVo.checkParams();
        return ResponseResult.okResult(userService.findUserByPage(pageVo, user));
    }

    /**
     * 新增用户
     * @param userDto
     * @return
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody @Validated(value = {Insert.class, Default.class}) UserDto userDto) {
        userService.saveUser(userDto);
        return ResponseResult.okResult();
    }

    /**
     * 跟新用户状态
     * @param userDto
     * @return
     */
    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody @Validated(value = {Update.class}) UserDto userDto) {
        userService.changeStatus(userDto);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查用户
     * @param id
     * @return
     */
    @GetMapping("{userId}")
    public ResponseResult<UserInfoAndRoleIdsVo> findUserById(@PathVariable("userId") Integer id) {
        return ResponseResult.okResult(userService.findByUserId(id));
    }

    /**
     * 跟新用户状态
     * @param userDto
     * @return
     */
    @PutMapping
    public ResponseResult updateUser(@RequestBody @Validated(value = {Update.class, Default.class}) UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseResult.okResult();
    }

    /**
     * 根据id删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteByIds(@PathVariable("id") Integer[] ids) {
        List<Integer> idList = Arrays.stream(ids).collect(Collectors.toList());
        if (idList.contains(User.ID_DEFAULT_USER)) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "默认用户不能被删除");
        }
        userService.removeByIds(idList);
        return ResponseResult.okResult();
    }
}