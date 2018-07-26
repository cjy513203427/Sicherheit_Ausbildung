package com.xgt.service;

import com.xgt.dao.UserDao;
import com.xgt.entity.User;
import com.xgt.entity.UserRoleRelation;
import com.xgt.util.EncryptUtil;
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

import static com.xgt.constant.SystemConstant.DEFAULT_PASSWORD;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDao userDao ;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    public List<User> queryUser(){
        return userDao.queryUser();
    }

    public User queryUserByUsername(String username) {
        return userDao.queryUserByUsername(username);
    }

    /**
     *  查询用户列表
     * @author liuao
     * @date 2018/4/2 10:56
     */
    public Map<String, Object> queryUserList(User user) {
        Map<String, Object> data = new HashMap<>();
        Long totalCount = userDao.queryUserCount(user);
        data.put("total" ,totalCount);
        if(totalCount.compareTo(0L) <= 0) {
            return data ;
        }

        List<User> users = userDao.queryUserList(user);
        data.put("total" ,totalCount);
        data.put("list" ,users);
        return data;
    }

    /**
     *  保存用户信息
     * @author liuao
     * @date 2018/4/2 13:44
     */
    public int saveUser(User user){
        int userId = userDao.saveUser(user);
        user.setId(userId);
        // 添加到钉钉
//        addOrUpdateUserToDingDing(user);
        return userId ;
    }

    /**
     *  用户授权角色
     * @author liuao
     * @date 2018/4/2 20:53
     */
    public void grantAuthorization(String[] roleIds , Integer userId) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                //删除用户角色
                userDao.deleteUserRole(userId);
                List<Map<String,Object>> userToRoles = new ArrayList<>();
                for(int i = 0;i<roleIds.length;i++){
                    Map userToRole = new HashMap();
                    userToRole.put("id",userId);
                    userToRole.put("roleId",roleIds[i]);
                    userToRoles.add(userToRole);
                }
                //判空，即用户是否拥有角色
                if(userToRoles.size()!=0){
                    Map userRoles = new HashMap();
                    userRoles.put("userToRoles",userToRoles);
                    //插入用户角色
                    userDao.insertUserRole(userRoles);
                }
                return true ;
            }
        });
    }

    public void modifyUser(User user) {
        userDao.modifyUser(user);
    }

    public void changeUserState(String userIds,String state) {
        userDao.changeUserState(userIds,state);
    }

    public void resetPassword(String[] userIds, String[] username) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                for (int i = 0; i <username.length ; i++) {
                    Map passwordToUser = new HashMap();
                    String encryptPassword =  EncryptUtil.md5(DEFAULT_PASSWORD, username[i], 2);
                    passwordToUser.put("password",encryptPassword);
                    passwordToUser.put("id",userIds[i]);
                    userDao.resetPassword(passwordToUser);
                }
                return true;
            }
        });
    }

    public List<UserRoleRelation> getUserRole(Integer userId) {
        return userDao.getUserRole(userId);
    }

    public String getUserNameById(Integer loginUserId) {
        return userDao.getUserNameById(loginUserId);
    }

    public void modifyPasswordById(Integer loginUserId, String passwordMd5) {
        Map userIdAndPassword = new HashMap();
        userIdAndPassword.put("id", loginUserId);
        userIdAndPassword.put("password", passwordMd5);
        userDao.resetPassword(userIdAndPassword);
    }

}
