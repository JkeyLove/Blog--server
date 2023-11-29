package com.ojc.runner;

import com.ojc.domain.entity.Article;
import com.ojc.mapper.ArticleMapper;
import com.ojc.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCount implements CommandLineRunner {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {

        //查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String,Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();   //ViewCount为Long类型,但是在redis中结尾带L(例如1L),无法自增,所以转成int
                    }));

        //存入redis
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
