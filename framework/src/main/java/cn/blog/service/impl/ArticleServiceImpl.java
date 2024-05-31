package cn.blog.service.impl;

import cn.blog.constants.SystemConstants;
import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.model.dto.ArticleDto;
import cn.blog.model.pojo.Article;
import cn.blog.model.pojo.Category;
import cn.blog.model.pojo.QArticle;
import cn.blog.model.pojo.Tag;
import cn.blog.model.vo.*;
import cn.blog.repository.ArticleRepository;
import cn.blog.repository.ArticleTagRepository;
import cn.blog.repository.CategoryRepository;
import cn.blog.service.ArticleService;
import cn.blog.service.ArticleTagService;
import cn.blog.service.TagService;
import cn.blog.utils.BeanCopyUtils;
import cn.blog.utils.RedisCache;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/1
 **/

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ArticleTagRepository articleTagRepository;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private TagService tagService;

    @PostConstruct
    public void loadViewCount() {
        //查询博客信息  id  viewCount
        Page<Article> articles = articleRepository.findByStatus(Article.STATUS_PUBLIC, Pageable.unpaged());
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()
                ));
        //存储到redis中
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEW_COUNT, viewCountMap);
    }


    /**
     * 查询热门文章
     * @return
     */
    @Override
    public List<HotArticleVo> hotArticleList() {
        // 查询浏览量前十的文章
        Sort sort = Sort.by(Sort.Direction.DESC, Article.VIEW_COUNT);
        Pageable pageable = PageRequest.of(PageVo.DEFAULT_PAGE_NUM, PageVo.DEFAULT_PAGE_SIZE, sort);
        // 文章状态为已发布
        List<Article> articleList = articleRepository.findByStatus(Article.STATUS_PUBLIC, pageable).toList();
        return BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
    }

    /**
     * 根据分类分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public PageVo articleList(Integer pageNum, Integer pageSize, Integer categoryId) {
        Sort sort = Sort.by(Sort.Direction.DESC, Article.TOP, Article.CREATE_TIME);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        // 文章状态为已发布,如果categoryId = 0,则查询全部分类
        Page<Article> articlePage = categoryId == 0 ?
                articleRepository.findByStatus(Article.STATUS_PUBLIC, pageable) :
                articleRepository.findByStatusAndCategoryId(Article.STATUS_PUBLIC, categoryId, pageable);
        articlePage.forEach(System.out::println);
        // 根据id查询分类名称
        List<ArticleListVo> articleListVoList = articlePage.stream()
                .map(article -> {
                    ArticleListVo articleListVo = BeanCopyUtils.copyBean(article, ArticleListVo.class);
                    Optional<Category> optional =
                            categoryRepository.findByIdAndStatus(article.getCategoryId(), Category.STATUS_NORMAL);
                    Category category = optional.orElseGet(Category::new);
                    return articleListVo.setCategoryName(category.getName());
                })
                .collect(Collectors.toList());
        return new PageVo(articleListVoList, (int) articlePage.getTotalElements());
    }

    /**
     * 根系文章浏览量
     * @param id
     * @return
     */
    @Override
    public void updateViewCount(Integer id) {
        if (redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id.toString()) == null) {
            redisCache.deleteObject(SystemConstants.ARTICLE_VIEW_COUNT);
            loadViewCount();
        }
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id.toString(), 1);
    }

    /**
     * 根据id 获取具体文章
     * @param id
     * @return
     */
    @Override
    public ArticleDetailVo getArticleDetail(Integer id) {
        //根据id查询文章
        Optional<Article> optional = articleRepository.findByIdAndStatus(id, Article.STATUS_PUBLIC);
        Article article = optional.orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND));
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.intValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Integer categoryId = articleDetailVo.getCategoryId();
        Optional<Category> optionalCategory = categoryRepository.findByIdAndStatus(categoryId, Category.STATUS_NORMAL);
        Category category = optionalCategory.orElseGet(Category::new);
        articleDetailVo.setCategoryName(category.getName());
        // 根据id查询标签名
        List<Integer> tagIds = articleTagService.findByArticleId(id)
                .stream()
                .map(articleTag -> articleTag.getId().getTagId())
                .collect(Collectors.toList());
        List<String> tags = tagService.findByIds(tagIds)
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        articleDetailVo.setTags(tags);
        return articleDetailVo;
    }

    /**
     * 分页查询文章
     * @param pageVo
     * @param article
     * @return
     */
    @Override
    public PageVo findArticleByPage(PageVo pageVo, Article article) {
        Sort sort = Sort.by(Sort.Direction.DESC, Article.CREATE_TIME);
        PageRequest pageRequest = PageRequest.of(pageVo.getPageNum(), pageVo.getPageSize(), sort);
        QArticle qArticle = QArticle.article;
        BooleanBuilder builder = new BooleanBuilder();
        // 根据title动态查询
        if (StringUtils.isNotBlank(article.getTitle())) {
            builder.and(qArticle.title.containsIgnoreCase(article.getTitle()));
        }
        // 根据摘要模糊查询
        if (StringUtils.isNotBlank(article.getSummary())) {
            builder.and(qArticle.title.containsIgnoreCase(article.getSummary()));
        }
        // 文章状态不能是已删除
        builder.and(qArticle.status.ne(Article.STATUS_DELETE));
        Page<Article> articlePage = articleRepository.findAll(builder, pageRequest);
        return new PageVo(BeanCopyUtils.copyBeanList(articlePage.toList(), ArticleVo.class), (int) articlePage.getTotalElements());
    }

    /**
     * 根据id查询文章信息
     * @param id
     * @return
     */
    @Override
    public ArticleVo getArticleById(Integer id) {
        // 根据id获取未删除的文章
        Article article = articleRepository.findByIdAndStatusNot(id, Article.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "文章不存在"));
        // 获取文章id对应的tag id
        List<Integer> tagIds = articleTagRepository.findById_ArticleId(id)
                .stream()
                .map(articleTag -> articleTag.getId().getTagId())
                .collect(Collectors.toList());
        // 拷贝
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        return articleVo.setTags(tagIds);
    }

    /**
     * 根据分类更新文章信息
     * @param id
     * @return
     */
    @Override
    public void deleteArticleCategory(Integer id) {
        // 根据categoryId获取文章
        List<Article> articles = articleRepository.findByCategoryId(id);
        // 修改文章的CategoryId
        articles = articles.stream()
                .map(article -> article.setCategoryId(Category.DEFAULT_CATEGORY))
                .collect(Collectors.toList());
        articleRepository.saveAll(articles);
    }

    /**
     * 跟新文章
     * @param articleDto
     */
    @Override
    public void updateArticle(ArticleDto articleDto) {
        // 根据id获取未删除的文章
        Article article = articleRepository.findByIdAndStatusNot(articleDto.getId(), Article.STATUS_DELETE)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.NOT_FOUND.getCode(), "文章不存在"));
        // 更新Article-Tag
        articleTagService.updateArticleTag(articleDto);
        article.setTitle(articleDto.getTitle())
                .setCategoryId(articleDto.getCategoryId())
                .setContent(articleDto.getContent())
                .setSummary(articleDto.getSummary())
                .setThumbnail(articleDto.getThumbnail())
                .setTop(articleDto.getTop())
                .setCanComment(articleDto.getCanComment())
                .setStatus(articleDto.getStatus());
        articleRepository.save(article);
    }

    /**
     * 新增文章
     * @param articleDto
     */
    @Override
    public void addArticle(ArticleDto articleDto) {
        // 拷贝
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        article.setStatus(articleDto.getStatus())
                .setTop(articleDto.getTop())
                .setCanComment(articleDto.getCanComment())
                .setViewCount(0);
        // 插入Article
        article = articleRepository.save(article);
        // 插入Article-Tag
        articleTagService.saveByArticleDto(articleDto.setId(article.getId()));
    }

    /**
     * 根据id删除文章
     * @param ids
     */
    @Override
    public void deleteArticleByIds(List<Integer> ids) {
        // 根据id获取未删除的文章
        List<Article> articles = articleRepository.findByIdInAndStatusNot(ids, Article.STATUS_DELETE)
                .stream()
                .map(article -> article.setStatus(Article.STATUS_DELETE))
                .collect(Collectors.toList());
        articleRepository.saveAll(articles);
    }
}
