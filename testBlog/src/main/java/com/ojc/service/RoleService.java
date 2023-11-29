package com.ojc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojc.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-05-29 21:36:33
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

}

