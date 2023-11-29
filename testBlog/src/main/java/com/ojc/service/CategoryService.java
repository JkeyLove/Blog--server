package com.ojc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-05-06 19:42:53
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

