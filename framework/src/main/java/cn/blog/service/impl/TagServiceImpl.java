package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.dto.TagDto;
import cn.blog.model.pojo.Category;
import cn.blog.model.pojo.QTag;
import cn.blog.model.pojo.Tag;
import cn.blog.model.vo.PageVo;
import cn.blog.model.vo.TagVo;
import cn.blog.repository.ArticleTagRepository;
import cn.blog.repository.TagRepository;
import cn.blog.service.TagService;
import cn.blog.utils.BeanCopyUtils;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/5
 **/
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagRepository tagRepository;

    @Resource
    private ArticleTagRepository articleTagRepository;

    /**
     * 根据条件分页查询tag
     * @param pageVo
     * @param tag
     * @return
     */
    @Override
    public PageVo findTageByPage(PageVo pageVo, Tag tag) {
        // 分页
        Pageable pageable = PageRequest.of(pageVo.getPageNum(), pageVo.getPageSize());
        QTag qTag = QTag.tag;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // name动态查询
        if (StringUtils.isNotBlank(tag.getName())) {
            booleanBuilder.and(qTag.name.containsIgnoreCase(tag.getName()));
        }
        // 状态为正常状态
        booleanBuilder.and(qTag.status.eq(Tag.STATUS_NORMAL));
        Page<Tag> tagPage = tagRepository.findAll(booleanBuilder, pageable);
        return new PageVo(BeanCopyUtils.copyBeanList(tagPage.toList(), TagVo.class), (int) tagPage.getTotalElements());
    }

    /**
     * 新增标签
     * @param tagDto
     */
    @Override
    public void addTag(TagDto tagDto) {
        Optional<Tag> tagOptional = tagRepository.findByName(tagDto.getName());
        Tag tag = tagOptional.orElseGet(Tag::new);
        // 证明该tag已经存在
        if (Tag.STATUS_NORMAL.equals(tag.getStatus())) {
            throw new SystemException(AppHttpCodeEnum.DATA_EXIST.getCode(), "该标签已存在");
        }
        tag.setName(tagDto.getName()).setRemark(tagDto.getRemark()).setStatus(Tag.STATUS_NORMAL);
        tagRepository.save(tag);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void removeById(Integer id) {
        Optional<Tag> optional = tagRepository.findById(id);
        if (!optional.isPresent()){
            return;
        }
        Tag tag = optional.get();
        tag.setStatus(Tag.STATUS_DELETE);
        articleTagRepository.deleteById_TagId(id);
        tagRepository.save(tag);
    }

    /**
     * 根据id查询标签信息
     * @param id
     * @return
     */
    @Override
    public TagVo findById(Integer id) {
        Optional<Tag> optional = tagRepository.findByIdAndStatus(id, Tag.STATUS_NORMAL);
        Tag tag = optional.orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND));
        return BeanCopyUtils.copyBean(tag, TagVo.class);
    }

    /**
     * 跟新标签
     * @param tagDto
     */
    @Override
    public void updateTag(TagDto tagDto) {
        Optional<Tag> optional = tagRepository.findByIdAndStatus(tagDto.getId(), Tag.STATUS_NORMAL);
        Tag tag = optional.orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND));
        tag.setName(tagDto.getName()).setRemark(tagDto.getRemark());
        tagRepository.save(tag);
    }

    /**
     * 查询所有正常的tag
     * @return
     */
    @Override
    public List<TagVo> listAllTag() {
        List<Tag> tags = tagRepository.findByStatus(Tag.STATUS_NORMAL);
        return BeanCopyUtils.copyBeanList(tags, TagVo.class);
    }

    /**
     * 根据标签id查询
     * @param ids
     * @return
     */
    @Override
    public List<Tag> findByIds(List<Integer> ids) {
        Integer[] idArray =new Integer[ids.size()];
        ids.toArray(idArray);
        return tagRepository.findByIdInAndStatus(idArray,Tag.STATUS_NORMAL);
    }
}
