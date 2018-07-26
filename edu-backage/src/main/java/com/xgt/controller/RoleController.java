package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.entity.Role;
import com.xgt.entity.User;
import com.xgt.enums.EnumPcsServiceError;
import com.xgt.service.RoleService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService ;

    /**
     * @Description 获取角色列表
     * @author Joanne
     * @create 2018/4/10 16:31
     */
    @RequestMapping(value = "/list" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> getRoleList(Role role){
        try{
            Map<String, Object> map = roleService.selectAllRole(role);
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("list..........异常 role=" + role.toString(), e);
            return ResultUtil.createFailResult("list..........异常 role =" + role.toString());
        }
    }

    /**
     * @Description 查询角色用户
     * @author Joanne
     * @create 2018/5/29 18:19
     */
    @RequestMapping(value = "/queryUsersByRoleId" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryUsersByRoleId(Integer roleId){
        try{
            List<User> users = roleService.queryUsersByRoleId(roleId);
            List<Integer> userIds = new ArrayList<>();
            for(User user:users){
                userIds.add(user.getId());
            }
            Map roleUser = new HashMap();
            roleUser.put("users",users);
            roleUser.put("userIds",userIds);
            return ResultUtil.createSuccessResult(roleUser);
        }catch(Exception e){
            logger.error("list..........查询角色用户异常......", e);
            return ResultUtil.createFailResult("list..........查询角色用户异常.....");
        }
    }

    /**
     * @Description 更新角色用户
     * @author Joanne
     * @create 2018/5/29 19:13
     */
    @RequestMapping(value = "/updateRoleUser" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateRoleUser(String userIds, Integer roleId){
        try{
            roleService.updateRoleUser(userIds,roleId);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("list..........更新角色用户异常......", e);
            return ResultUtil.createFailResult("list..........更新角色用户异常.....");
        }
    }

    /**
     * @Description 更新角色
     */
    @RequestMapping(value = "/update" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateRole(Role role) {
        try {
            Assert.notNull(role.getId(), "用户ID不能为空");
            if (role.getName() != null && (!role.getName().equals(""))) {
                Integer isExistRole = roleService.isExistRole(role);
                if (isExistRole != null && isExistRole > 0) {
                    return ResultUtil.createFailResult(EnumPcsServiceError.BUSINESS_ROLE_EXISTED + "");
                }
            }
            roleService.updateRole(role);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("update..........异常 role=" + role.toString(), e);
            return ResultUtil.createFailResult("update..........异常 role =" + role.toString());
        }
    }

    /**
     * @Description 创建角色
     * @author Joanne
     * @create 2018/4/10 16:33
     */
    @RequestMapping(value = "/create" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> createRole(Role role) {
        try {
            if (role.getName() != null && (!"".equals(role.getName()))) {
                Integer isExistRole = roleService.isExistRole(role);
                if (isExistRole != null && isExistRole > 0) {
                    return ResultUtil.createFailResult(EnumPcsServiceError.BUSINESS_ROLE_EXISTED + "");
                }
            }
            roleService.createRole(role);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("create..........创建角色异常 role=" + role.toString(), e);
            return ResultUtil.createFailResult("create..........创建角色异常 role =" + role.toString());
        }
    }

    /**
     * @Description 删除角色（使角色无效）
     * @author Joanne
     * @create 2018/4/10 16:33
     */
    @RequestMapping(value = "/delete" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteRole(String roleIds) {
        try {
            Assert.notNull(roleIds, "角色ID不能为空");
            roleService.deleteRole(roleIds);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("delete..........异常 role=" + roleIds, e);
            return ResultUtil.createFailResult("delete..........异常 role =" + roleIds);
        }
    }
}