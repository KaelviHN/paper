package cn.blog.service;

import cn.blog.model.dto.LinkDto;
import cn.blog.model.pojo.Link;
import cn.blog.model.vo.LinkVo;
import cn.blog.model.vo.PageVo;

import java.util.List;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
public interface LinkService {
    /**
     * 获取所有已审核的友链
     * @return
     */
    List<LinkVo> getAllLink();

    /**
     * 友链分页查询
     * @param pageVo
     * @param link
     * @return
     */
    PageVo getLinksByPage(PageVo pageVo, Link link);

    /**
     * 查询对应id的友链
     * @param id
     * @return
     */
    LinkVo findById(Integer id);

    /**
     * 更新友链
     * @param linkDto
     */
    void updateLink(LinkDto linkDto);

    /**
     * 根据id删除友链
     * @param id
     */
    void removeById(Integer id);

    /**
     * 新增友链
     * @param linkDto
     */
    void addLink(LinkDto linkDto);

    /**
     * 根据ids删除
     * @param idList
     */
    void removeByIds(List<Integer> idList);
}
