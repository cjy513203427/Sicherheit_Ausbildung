package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.Role;
import com.xgt.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @Description
 * @create 2018-03-30 19:05
 **/
@Repository
public class RoleDao {
    private static final Logger logger = LoggerFactory.getLogger(RoleDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "role.";

    /**
     *  根据userid 查询 该用户的所有角色
     * @author liuao
     * @date 2018/4/1 16:01
     */
    public List<Role> queryRoleListByUserId(Integer userId){
        String sqlId = NAMESPACE + "queryRoleListByUserId";
        Map<String,Object> param = new HashMap<>();
        param.put("userId", userId);
        return daoClient.queryForList(sqlId, param, Role.class);
    }

    /**
     * @Description 计算角色数量
     * @author
     * @create
     */
    public Integer countRole(Role role) {
        String sqlId = NAMESPACE + "countRole";
        Long total =  daoClient.queryForObject(sqlId,role,Long.class);
        return total.intValue();
    }

    public List<Role> selectAllRole(Role role) {
        String sqlId = NAMESPACE + "selectAllRole" ;
        return daoClient.queryForList(sqlId,role,Role.class);
    }

    public Integer selectSingleRole(Role role) {
        String sqlId = NAMESPACE + "selectSingleRole" ;
        Long value = daoClient.queryForObject(sqlId,role,Long.class);
        return value.intValue();
    }

    /**
     * @Description 修改角色
     * @author Joanne
     * @create 2018/4/10 18:23
     */
    public void updateRole(Role role) {
        String sqlId = NAMESPACE + "updateRole" ;
        daoClient.excute(sqlId,role);
    }

    /**
     * @Description 删除角色权限
     * @author Joanne
     * @create 2018/4/10 18:22
     */
    public void deleteRoleResource(Role role) {
        String sqlId = NAMESPACE + "deleteRoleResource";
        daoClient.excute(sqlId,role);
    }

    /**
     * @Description 分配角色权限
     * @author Joanne
     * @create 2018/4/10 16:57
     */
    public void insertRoleResource(Map map) {
        String sqlId = NAMESPACE + "insertRoleResource";
        daoClient.batchInsertAndGetId(sqlId,map);
    }

    /**
     * @Description 创建角色
     * @author Joanne
     * @create 2018/4/10 16:48
     */
    public Integer createRole(Role role) {
        String sqlId = NAMESPACE + "createRole" ;
        return daoClient.insertAndGetId(sqlId,role);
    }

    /**
     * @Description 更改角色状态
     * @author Joanne
     * @create 2018/4/10 18:23
     */
    public void updateRoleStatus(String state,String roleIds) {
        String sqlId = NAMESPACE + "updateRoleStatus" ;
        Map roleIdsAndState = new HashMap();
        roleIdsAndState.put("state",state);
        roleIdsAndState.put("roleIds",roleIds);
        daoClient.excute(sqlId,roleIdsAndState);
    }

    /**
     * @Description 查询角色用户
     * @author Joanne
     * @create 2018/5/29 18:22
     */
    public List<User> queryUsersByRoleId(Integer roleId) {
        String sqlId = NAMESPACE + "queryUsersByRoleId" ;
        Map params = new HashMap();
        params.put("roleId",roleId);
        return daoClient.queryForList(sqlId,params,User.class);
    }

    public void deleteRoleUserByUseId(String userId,Integer roleId) {
        String sqlId = NAMESPACE + "deleteRoleUserByUseId";
        Map params = new HashMap();
        params.put("userId",userId);
        params.put("roleId",roleId);
        daoClient.excute(sqlId,params);
    }
}
