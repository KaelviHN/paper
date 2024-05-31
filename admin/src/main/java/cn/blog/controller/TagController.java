package cn.blog.controller;

import cn.blog.model.ResponseResult;
import cn.blog.model.dto.TagDto;
import cn.blog.model.pojo.Tag;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.TagVo;
import cn.blog.service.TagService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/5
 **/

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    private TagService tagService;

    /**
     * 分页查询Tag
     * @param pageVo
     * @param tag
     * @return
     */
    @GetMapping("list")
    public ResponseResult<PageVo> findTagePage(PageVo pageVo, Tag tag) {
        pageVo.checkParams();
        return ResponseResult.okResult(tagService.findTageByPage(pageVo, tag));
    }

    /**
     * 新增Tag
     * @param tagDto
     * @return
     */
    @PostMapping
    public ResponseResult addTag(@RequestBody @Validated TagDto tagDto) {
        tagService.addTag(tagDto);
        return ResponseResult.okResult();
    }

    /**
     * 根据id删除Tag
     * @param ids
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteTag(@PathVariable(value = "id") Integer[] ids) {
        Arrays.stream(ids).forEach(id -> tagService.removeById(id));
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询标签信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult<TagVo> findTagById(@PathVariable(value = "id") Integer id) {
        return ResponseResult.okResult(tagService.findById(id));
    }

    /**
     * 跟新标签信息
     * @param tagDto
     * @return
     */
    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto) {
        tagService.updateTag(tagDto);
        return ResponseResult.okResult();
    }

    @GetMapping("listAllTag")
    public ResponseResult<List<TagVo>> listAllTag() {
        return ResponseResult.okResult(tagService.listAllTag());
    }

}