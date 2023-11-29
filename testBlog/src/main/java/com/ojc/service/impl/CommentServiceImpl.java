package com.ojc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojc.constans.SystemConstants;
import com.ojc.domain.ResponseResult;
import com.ojc.domain.entity.Comment;
import com.ojc.domain.vo.CommentVo;
import com.ojc.domain.vo.PageVo;
import com.ojc.enums.AppHttpCodeEnum;
import com.ojc.exception.SystemException;
import com.ojc.mapper.CommentMapper;
import com.ojc.service.CommentService;
import com.ojc.service.UserService;
import com.ojc.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-05-11 22:28:10
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        //当commentType=0,即评论类型为友链类型,才进行articleId匹配.友链的articleId为null,使用会空指针异常
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);

        //根评论RootId==-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_STATUS_ROOT);

        //对应评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        //将Comment转为CommentVo,并且将在CommentVo中存在但Comment没有的字段赋值
        //返回所有根评论
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论,赋值给children
        commentVoList.stream()
                .forEach(commentVo ->
                        commentVo.setChildren(getChildren(commentVo)));
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {

        //评论不能为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new  SystemException(AppHttpCodeEnum.CONTEXT_NOT_NULL);
        }
        //设置了MP字段填充
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(CommentVo commentVo){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,commentVo.getId());
        queryWrapper.orderByAsc(Comment::getCreateTime);
        //commentChildrenList为对应的子评论
        List<Comment> childrenList = list(queryWrapper);
        //将对应子评论转转为Vo
        List<CommentVo> ChildrenVoList = toCommentVoList(childrenList);
        return ChildrenVoList;
    }

    //将Comment转为CommentVo,并且将在CommentVo中存在但Comment没有的字段赋值
    private List<CommentVo> toCommentVoList(List<Comment> commentList){
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList,CommentVo.class);
        //遍历commentVoList集合

        //方法一:
        /*for (CommentVo commentVo
                :commentVoList) {
            //通过createBy查询username,并且赋值到CommentVo
            commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
            //通过toCommentUserId查询toCommentUserName,并且赋值到CommentVo
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != SystemConstants.TO_COMMENT_STATUS_ROOT){
                commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
            }
        }*/

        //方法二:
        commentVoList.stream()
                .forEach(commentVo -> {
                    commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
                    if (commentVo.getToCommentUserId() != SystemConstants.TO_COMMENT_STATUS_ROOT) {
                        commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
                    }
                });

        return commentVoList;
    }
}