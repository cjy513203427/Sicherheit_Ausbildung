package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.dto.TrainLabourerDto;
import com.xgt.entity.BuildLabourer;
import com.xgt.entity.User;
import com.xgt.entity.UserRoleRelation;
import com.xgt.entity.train.EduTraplanLabourRel;
import com.xgt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private DaoClient daoClient ;

    private static final String  name_space = "user.";

    public List<User>  queryUser(){
        String sqlId = name_space + "queryUserList";
        return daoClient.queryForList(sqlId, new HashMap<>(), User.class);
    }

    public User queryUserByUsername(String username) {
        String sqlId = name_space + "queryUserByUsername" ;
        Map param = new HashMap();
        param.put("username",username);
        return daoClient.queryForObject(sqlId,param,User.class);
    }

    /**
     *  查询用户列表
     * @author liuao
     * @date 2018/4/2 10:47
     */
    public List<User> queryUserList(User user) {
        String sqlId = name_space + "queryUserList";
        Map<String,Object> param = new HashMap<>();
        return daoClient.queryForList(sqlId, user, User.class);
    }

    /**
     *  根据条件查询 用户总数
     * @author liuao
     * @date 2018/4/2 10:47
     */
    public Long queryUserCount(User user){
        String sqlId = name_space + "queryUserCount";
        return daoClient.queryForObject(sqlId, user, Long.class);
    }

    /**
     *  保存用户信息
     * @author liuao
     * @date 2018/4/2 13:46
     */
    public int saveUser(User user){
        String sqlId = name_space + "saveUser";
        return daoClient.insertAndGetId(sqlId, user);
    }

    /*
     * @author Joanne
     * @date 2018/4/10 16:14
     * @Description 用户修改
     */
    public void modifyUser(User user) {
        String sqlId = name_space + "modifyUser";
        daoClient.excute(sqlId,user);
    }

    /*
     * @author Joanne
     * @date 2018/4/10 16:14
     * @Description 修改用户状态
     */
    public void changeUserState(String userIds,String state) {
        String sqlId = name_space + "changeUserState" ;
        Map userIdsAndState = new HashMap();
        userIdsAndState.put("userIds",userIds);
        userIdsAndState.put("state",state);
        daoClient.excute(sqlId,userIdsAndState);
    }

    /*
     * @author Joanne
     * @date 2018/4/10 16:15
     * @Description 修改密码
     */
    public void resetPassword(Map packageEcryptPass) {
        String sqlId = name_space + "resetPassword" ;
        daoClient.excute(sqlId,packageEcryptPass);
    }

    public List<UserRoleRelation> getUserRole(Integer userId) {
        String sqlId = name_space + "getUserRole";
        User user = new User();
        user.setId(userId);
        return daoClient.queryForList(sqlId,user, UserRoleRelation.class);
    }

    /**
     * @Description 删除用户角色
     * @author Joanne
     * @create 2018/4/10 20:32
     */
    public void deleteUserRole(Integer userId) {
        String sqlId = name_space + "deleteUserRole" ;
        Map map = new HashMap();
        map.put("id",userId);
        daoClient.excute(sqlId,map);
    }

    public void insertUserRole(Map userRoles) {
        String sqlId = name_space + "insertUserRole" ;
        daoClient.batchInsertAndGetId(sqlId,userRoles);
    }

    public String getUserNameById(Integer loginUserId) {
        String sqlId = name_space + "queryUserById";
        Map getUserNameById = new HashMap();
        getUserNameById.put("id",loginUserId);
        getUserNameById = daoClient.queryForMap(sqlId,getUserNameById);
        return getUserNameById.get("username").toString();
    }

    /**
     * @Description 根据岗位查询用户id集合
     * @author Joanne
     * @create 2018/6/11 17:36
     */
    public List<Integer> queryLabourIdsByPostType(String postType) {
        String sqlId = name_space + "queryLabourIdsByPostType";
        Map getUserNameById = new HashMap();
        getUserNameById.put("postType",postType);
        return  daoClient.queryForList(sqlId,getUserNameById,Integer.class);
    }

    public List<User> querySupervisorByCompanyId(Integer companyId) {
        String sqlId = name_space + "querySupervisorByCompanyId" ;
        Map map = new HashMap();
        map.put("companyId",companyId);
        return daoClient.queryForList(sqlId,map,User.class);
    }

    /**
     * @Description 根据方案计划id查询人员
     * @author Joanne
     * @create 2018/7/18 15:05
     */
    public List<EduTraplanLabourRel> queryLabourIdsByPlanId(Integer planId) {
        String sqlId = name_space + "queryLabourIdsByPlanId" ;
        Map map = new HashMap() ;
        map.put("planId",planId);
        return daoClient.queryForList(sqlId,map,EduTraplanLabourRel.class);
    }
}
