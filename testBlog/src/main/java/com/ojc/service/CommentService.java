package com.ojc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-05-11 22:28:08
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

