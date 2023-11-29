package com.ojc.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojc.domain.entity.Tag;
import com.ojc.mapper.TagMapper;
import com.ojc.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-28 19:34:13
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

