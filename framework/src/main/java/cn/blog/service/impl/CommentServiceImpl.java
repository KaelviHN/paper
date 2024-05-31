package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.pojo.Article;
import cn.blog.model.pojo.Comment;
import cn.blog.model.pojo.QComment;
import cn.blog.model.pojo.User;
import cn.blog.model.vo.CommentVo;
import cn.blog.model.vo.PageVo;
import cn.blog.repository.ArticleRepository;
import cn.blog.repository.CommentRepository;
import cn.blog.repository.UserRepository;
import cn.blog.service.CommentService;
import cn.blog.utils.BeanCopyUtils;
import cn.blog.utils.SecurityUtils;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/2
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ArticleRepository articleRepository;


    /**
     * 查询评论列表
     * @param commentType
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageVo commentList(Character commentType, Integer articleId, Integer pageNum, Integer pageSize) {
        // 获取10条根评论(rootId = -1)
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        QComment qComment = QComment.comment;
        BooleanBuilder builder = new BooleanBuilder();
        // 如果是文章类型，则根据文章id查找
        if (Comment.ARTICLE_COMMENT.equals(commentType)) {
            builder.and(qComment.articleId.eq(articleId));
        }
        // 根据文章类型,根评论id,状态
        builder.and(qComment.type.eq(commentType))
                .and(qComment.rootId.eq(Comment.ROOT_COMMENT))
                .and(qComment.status.eq(Comment.STATUS_NORMAL));
        Page<Comment> commentPage = commentRepository.findAll(builder, pageable);
        // 封装根评论
        List<CommentVo> commentVoList = toCommentVoList(commentPage.toList());
        // 处理子评论
        commentVoList.stream()
                .forEach(commentVo -> {
                    List<CommentVo> children = getChildren(commentVo.getId());
                    commentVo.setChildren(children);
                });
        return new PageVo(commentVoList, commentPage.getTotalPages());
    }

    /**
     * 新增评论
     * @param comment
     */
    @Override
    public void addComment(Comment comment) {
        // 根据id获取用户信息
        Integer userId = SecurityUtils.getUserId();
        comment.setStatus(Comment.STATUS_CHECK)
                .setToCommentId(userId);
        comment.setType(comment.getArticleId() == null ? Comment.LINK_COMMENT : Comment.ARTICLE_COMMENT);
        commentRepository.saveAndFlush(comment);
    }

    /**
     * 分页查询评论
     * @param pageVo
     * @param status
     * @return
     */
    @Override
    public PageVo pageComment(PageVo pageVo, Character status) {
        Sort sort = Sort.by(Sort.Direction.DESC, Comment.CREATE_TIME);
        PageRequest pageRequest = PageRequest.of(pageVo.getPageNum(), pageVo.getPageSize(), sort);
        QComment qComment = QComment.comment;
        BooleanBuilder builder = new BooleanBuilder();
        // 根据状态动态查询
        if (status == null) {
            builder.and(qComment.status.ne(Comment.STATUS_DELETE));
        } else {
            builder.and(qComment.status.eq(status));
        }
        Page<Comment> commentPage = commentRepository.findAll(builder, pageRequest);
        List<CommentVo> commentVos = commentPage.map(comment -> {
            //拷贝
            CommentVo commentVo = BeanCopyUtils.copyBean(comment, CommentVo.class);
            // 查询并设置文章名
            String name = Comment.ARTICLE_COMMENT.equals(comment.getType())
                    ? articleRepository.findByIdAndStatusNot(comment.getArticleId(), Article.STATUS_DELETE)
                    .orElseGet(Article::new).getTitle() : Comment.LINK_COMMENT_DEFAULT_VALUE;
            commentVo.setName(name);
            // 设置用户昵称
            Integer toCommentId = comment.getToCommentId();
            Optional<User> user = userRepository.findById(toCommentId);
            String nickName = user.orElseGet(User::new).getNickName();
            commentVo.setUsername(nickName);
            return commentVo;
        }).toList();
        return new PageVo(commentVos, (int) commentPage.getTotalElements());
    }

    /**
     * 更新状态
     * @param commentVo
     */
    @Override
    public void changeStatus(CommentVo commentVo) {
        Comment comment
                = commentRepository.findByIdAndStatusNot(commentVo.getId(), Comment.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "该评论不存在"));
        comment.setStatus(commentVo.getStatus());
        commentRepository.save(comment);
    }

    /**
     * 根据id删除
     * @param ids
     */
    @Override
    public void removeByIds(Integer[] ids) {
        List<Comment> comments = commentRepository.findAllById(Arrays.stream(ids).collect(Collectors.toList()));
        int size = comments.size();
        for (int i = 0; i < size; i++) {
            // 获取子评论
            List<Comment> childComment =
                    commentRepository.findByRootIdAndStatusNot(comments.get(i).getId(), Comment.STATUS_DELETE, Sort.by(Sort.Direction.DESC, Comment.CREATE_TIME));
            comments.addAll(childComment);
        }
        // 修改状态
        comments = comments.stream()
                .distinct()
                .map(comment -> comment.setStatus(Comment.STATUS_DELETE))
                .collect(Collectors.toList());
        commentRepository.saveAll(comments);
    }

    /**
     * 封装评论
     * @param commentList
     * @return
     */
    public List<CommentVo> toCommentVoList(List<Comment> commentList) {
        // 属性拷贝
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        return commentVos.stream()
                .map(commentVo -> {
                    // 获取评论人昵称
                    Optional<User> optional = userRepository.findById(commentVo.getCreateBy());
                    User user = optional.orElseGet(User::new);
                    commentVo.setUsername(user.getNickName());
                    // 获取被回复人昵称
                    String toCommentUserName = Comment.ROOT_COMMENT.equals(commentVo.getToCommentUserId()) ?
                            null : userRepository.findById(commentVo.getToCommentUserId()).orElseGet(User::new).getNickName();
                    return commentVo.setToCommentUserName(toCommentUserName);
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取子评论
     * @param id
     * @return
     */
    public List<CommentVo> getChildren(Integer id) {
        // 获取子评论
        List<Comment> childComment =
                commentRepository.findByRootIdAndStatus(id, Comment.STATUS_NORMAL, Sort.by(Sort.Direction.DESC, Comment.CREATE_TIME));
        return toCommentVoList(childComment);
    }
}
