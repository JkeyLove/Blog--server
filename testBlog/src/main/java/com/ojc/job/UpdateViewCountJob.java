package com.ojc.job;

import com.ojc.domain.entity.Article;
import com.ojc.service.ArticleService;
import com.ojc.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Resource
    private RedisCache redisCache;

    //MybatisPlus中的service中有批量保存的方法
    @Resource
    private ArticleService articleService;

    //从每分钟的0秒开始,每五秒执行一次
    @Scheduled(cron = "0/5 * * * * ?")
    public void testJob(){
        //获取Redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        //注意,双列集合不能转为流对象,先转为entrySet(),再处理
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //更新到数据库中
         articleService.updateBatchById(articles);
    }

}
