package com.ojc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-05-08 20:46:13
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

