package com.ojc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojc.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-05-29 21:19:15
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);
}

