package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.dto.LinkDto;
import cn.blog.model.pojo.Link;
import cn.blog.model.pojo.QLink;
import cn.blog.model.vo.LinkVo;
import cn.blog.model.vo.PageVo;
import cn.blog.repository.LinkRepository;
import cn.blog.service.LinkService;
import cn.blog.utils.BeanCopyUtils;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
@Service
public class LinkServiceImpl implements LinkService {
    @Resource
    private LinkRepository linkRepository;

    /**
     * 获取所有已审核的友链
     * @return
     */
    @Override
    public List<LinkVo> getAllLink() {
        List<Link> linkList = linkRepository.findByStatus(Link.STATUS_REVIEW_PASS);
        return BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
    }

    /**
     * 友链分页查询
     * @param pageVo
     * @param link
     * @return
     */
    @Override
    public PageVo getLinksByPage(PageVo pageVo, Link link) {
        Sort sort = Sort.by(Sort.Direction.DESC, Link.CREATE_TIME);
        PageRequest pageRequest = PageRequest.of(pageVo.getPageNum(), pageVo.getPageSize(), sort);
        QLink qLink = QLink.link;
        BooleanBuilder builder = new BooleanBuilder();
        // link名称的动态查询
        if (StringUtils.isNotBlank(link.getName())) {
            builder.and(qLink.name.containsIgnoreCase(link.getName()));
        }
        // link状态的动态查询
        if (link.getStatus() != null) {
            builder.and(qLink.status.eq(link.getStatus()));
        } else {
            builder.and(qLink.status.ne(Link.STATUS_DELETE));
        }
        Page<Link> linkPage = linkRepository.findAll(builder, pageRequest);
        return new PageVo(linkPage.toList(), (int) linkPage.getTotalElements());
    }

    /**
     * 查询对应id的友链
     * @param id
     * @return
     */
    @Override
    public LinkVo findById(Integer id) {
        Link link = linkRepository.findByIdAndStatusNot(id, Link.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND));
        return BeanCopyUtils.copyBean(link, LinkVo.class);
    }

    /**
     * 更新友链
     * @param linkDto
     */
    @Override
    public void updateLink(LinkDto linkDto) {
        Link link = linkRepository.findByIdAndStatusNot(linkDto.getId(), Link.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND));
        link.setName(linkDto.getName())
                .setLogo(linkDto.getLogo())
                .setAddress(linkDto.getAddress())
                .setDescription(linkDto.getDescription())
                .setStatus(linkDto.getStatus());
        linkRepository.save(link);
    }

    /**
     * 根据id删除友链
     * @param id
     */
    @Override
    public void removeById(Integer id) {
        Link link = linkRepository.findByIdAndStatusNot(id, Link.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND));
        link.setStatus(Link.STATUS_DELETE);
        linkRepository.save(link);
    }

    /**
     * 新增友链
     * @param linkDto
     */
    @Override
    public void addLink(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        link.setStatus(linkDto.getStatus());
        linkRepository.save(link);
    }

    /**
     * 根据ids删除
     * @param idList
     */
    @Override
    public void removeByIds(List<Integer> idList) {
        List<Link> links = linkRepository.findByIdInAndStatusNot(idList, Link.STATUS_DELETE)
                .stream()
                .map(link -> link.setStatus(Link.STATUS_DELETE))
                .collect(Collectors.toList());
        linkRepository.saveAll(links);
    }


}
