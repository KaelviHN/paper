package cn.blog.job;

import cn.blog.constants.SystemConstants;
import cn.blog.model.pojo.Article;
import cn.blog.repository.ArticleRepository;
import cn.blog.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author KaelvihN
 */
@Component
public class UpdateViewCountJob {

    @Resource
    private RedisCache redisCache;

    @Resource
    private ArticleRepository articleRepository;

    @Scheduled(cron = "0 */5 * ? * *")
    public void updateViewCount() {
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT);
        //更新到数据库中
        viewCountMap.entrySet().stream()
                .forEach(entry -> {
                    articleRepository.updateViewCountById(Integer.valueOf(entry.getKey()), entry.getValue().intValue());
                });

    }
}
