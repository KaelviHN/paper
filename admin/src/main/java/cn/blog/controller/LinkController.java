package cn.blog.controller;

import cn.blog.group.Update;
import cn.blog.model.ResponseResult;
import cn.blog.model.dto.LinkDto;
import cn.blog.model.pojo.Link;
import cn.blog.model.vo.LinkVo;
import cn.blog.model.vo.PageVo;
import cn.blog.service.LinkService;
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
 * @Date : 2024/3/6
 **/
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Resource
    private LinkService linkService;

    /**
     * 分页查询
     * @param pageVo
     * @param link
     * @return
     */
    @GetMapping("list")
    public ResponseResult<PageVo> findLinkPage(PageVo pageVo, Link link) {
        pageVo.checkParams();
        return ResponseResult.okResult(linkService.getLinksByPage(pageVo, link));
    }

    /**
     * 查询对应id的友链信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult<LinkVo> findLinkById(@PathVariable(value = "id") Integer id) {
        return ResponseResult.okResult(linkService.findById(id));
    }

    /**
     * 跟新友链信息
     */
    @PutMapping
    public ResponseResult updateLink(@RequestBody @Validated(value = {Default.class, Update.class}) LinkDto linkDto) {
        linkService.updateLink(linkDto);
        return ResponseResult.okResult();
    }

    /**
     * 删除友链
     * @param ids
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteLinkById(@PathVariable("id") Integer[] ids) {
        List<Integer> idList = Arrays.stream(ids).collect(Collectors.toList());
        linkService.removeByIds(idList);
        return ResponseResult.okResult();
    }

    /**
     * 添加友链
     * @param linkDto
     * @return
     */
    @PostMapping
    public ResponseResult addLink(@RequestBody @Validated LinkDto linkDto) {
        linkService.addLink(linkDto);
        return ResponseResult.okResult();
    }


}