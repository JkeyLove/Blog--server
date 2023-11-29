package com.ojc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojc.constans.SystemConstants;
import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.Article;
import com.ojc.domain.entity.Category;
import com.ojc.domain.vo.CategoryVo;
import com.ojc.mapper.CategoryMapper;
import com.ojc.service.ArticleService;
import com.ojc.service.CategoryService;
import com.ojc.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-05-06 19:42:55
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表,状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id并且去重
        List<Long> categoryIds = articleList
                .stream()
                .map(article -> article.getCategoryId())
                .distinct()
                .collect(Collectors.toList());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories
                .stream()
                .filter(category -> category.getStatus().equals(SystemConstants.STATUS_NORMAL))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
