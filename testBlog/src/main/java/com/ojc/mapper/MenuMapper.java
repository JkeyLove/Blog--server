package com.ojc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ojc.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-29 21:19:10
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    String selectPermsByUserId(Long id);
}

