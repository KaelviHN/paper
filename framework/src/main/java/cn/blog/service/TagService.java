package cn.blog.service;

import cn.blog.model.dto.TagDto;
import cn.blog.model.pojo.Tag;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.TagVo;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/5
 **/
public interface TagService {
    /**
     * 根据条件分页查询tag
     * @param pageVo
     * @param tag
     * @return
     */
    PageVo findTageByPage(PageVo pageVo, Tag tag);

    /**
     * 新增标签
     * @param tagDto
     */
    void addTag(TagDto tagDto);

    /**
     * 根据id删除
     * @param id
     */
    void removeById(Integer id);

    /**
     * 根据id查询标签信息
     * @param id
     * @return
     */
    TagVo findById(Integer id);

    /**
     * 跟新标签
     * @param tagDto
     */
    void  updateTag(TagDto tagDto);

    /**
     * 查询所有正常的tag
     * @return
     */
    List<TagVo> listAllTag();

    /**
     * 根据标签id查询
     * @param ids
     * @return
     */
    List<Tag> findByIds(List<Integer> ids);
}
