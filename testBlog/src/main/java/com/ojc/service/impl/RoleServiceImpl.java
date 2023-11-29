package com.ojc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojc.domain.entity.Role;
import com.ojc.domain.entity.UserRole;
import com.ojc.mapper.RoleMapper;
import com.ojc.service.RoleService;
import com.ojc.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-05-29 21:36:34
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserRoleService userRoleService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //根据用户id在关系表中找到角色id
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleService.list(queryWrapper);
        List<Long> roleIdList = userRoleList
                .stream()
                .map(userRole -> userRole.getRoleId())
                .collect(Collectors.toList());

        //根据角色id,在角色表中找到对应的角色
        LambdaQueryWrapper<Role> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Role::getId,roleIdList.get(0));
        List<Role> roleList = list(queryWrapper1);
        List<String> roleKeyList =roleList
                .stream()
                .map(role -> role.getRoleKey().toString())
                .collect(Collectors.toList());


        return roleKeyList;
    }
}
