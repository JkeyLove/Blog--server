package com.ojc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojc.domain.entity.UserRole;
import com.ojc.mapper.UserRoleMapper;
import com.ojc.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-05-31 01:11:29
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
