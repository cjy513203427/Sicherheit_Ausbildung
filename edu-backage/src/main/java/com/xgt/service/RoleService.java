package com.xgt.service;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.RoleDao;
import com.xgt.entity.Role;
import com.xgt.entity.User;
import com.xgt.util.JSONUtil;
import com.xgt.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joanne
 * @Description
 * @create 2018-03-30 19:16
 **/
@Service
public class RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleDao roleDao ;

    @Autowired
    private TransactionTemplate transactionTemplate ;


    /**
     *  根据userid 查询 该用户的所有角色
     * @author liuao
     * @date 2018/4/1 16:01
     */
    public List<Role> queryRoleListByUserId(Integer userId){
        return roleDao.queryRoleListByUserId(userId);
    }

    public Map<String,Object> selectAllRole(Role role) {
        Integer total ;
        List<Role> list = null;
        Map<String, Object> map = new HashMap<>();
        // 偏移量
        int pageOffset = role.getPageSize() * (role.getPageIndex() - 1);
        role.setPageOffset(pageOffset);
        // 第一页时,查询总记录数，翻页时不查询，由客户端保存总数
        total = roleDao.countRole(role);
        if (total!=null && total>0) {
            list =roleDao.selectAllRole(role);
        }
        map.put("list", JSONUtil.filterDateProperties(list, User.class));
        map.put("total", total);
        return map;
    }

    public Integer isExistRole(Role role) {
        return  roleDao.selectSingleRole(role);
    }

    /*
     * @author Joanne
     * @date 2018/4/6 13:39
     * @Description 更新角色权限
     */
    public void updateRole(Role role) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                //更新角色
                roleDao.updateRole(role);
                String[] permissions= StringUtil.stringAnalytical(role.getPermissions(),",");
                //删除角色已有权限
                roleDao.deleteRoleResource(role);
                if(permissions.length>0){
                    Map packagePersionAndRole = packageArrayToMap(permissions,role);
                    //插入角色权限
                    roleDao.insertRoleResource(packagePersionAndRole);
                }
                return true;
            }
        });
    }

    public void createRole(Role role) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                //创建角色
                Integer roleId = roleDao.createRole(role);
                role.setId(roleId);
                if(role.getPermissions()!=null&&(!"".equals(role.getPermissions()))){
                    String[] permissions= StringUtil.stringAnalytical(role.getPermissions(),",");
                    Map packagePersionAndRole = packageArrayToMap(permissions,role);
                    //为新角色分配权限
                    roleDao.insertRoleResource(packagePersionAndRole);
                }
                return true;
            }
        });
    }

    /*
    * @author Joanne
    * @date 2018/4/6 13:42
    * @Description 将权限与角色进行组装，便于进行sql插入操作
    */
    public Map packageArrayToMap(String[] string,Role role){
        List<Map<String,Object>> roleResourceList = new ArrayList<>();
        for(int i = 0 ;i<string.length;i++){
            Map permission = new HashMap();
            permission.put("roleId",role.getId());
            permission.put("resourceId",string[i]);
            roleResourceList.add(permission);
        }
        Map map = new HashMap();
        map.put("roleResourceList",roleResourceList);
        return map ;
    }

    public void deleteRole(String roleIds) {
        roleDao.updateRoleStatus(SystemConstant.State.DISABLE_STATE,roleIds);
    }

    public List<User> queryUsersByRoleId(Integer roleId) {
        return roleDao.queryUsersByRoleId(roleId);
    }

    /**
     * @Description 删除角色用户
     * @author Joanne
     * @create 2018/5/29 19:44
     */
    public void updateRoleUser(String userIds, Integer roleId) {
        List<User> users = roleDao.queryUsersByRoleId(roleId);
        String userId = "";
        for (User user:users) {
            if(!userIds.contains(user.getId().toString())){
                userId = userId + user.getId()+",";
            }
        }
        if(StringUtils.isNotBlank(userId)){
            userId = userId.substring(0,userId.length()-1);
            roleDao.deleteRoleUserByUseId(userId,roleId);
        }
    }
}
