package com.xgt.controller;
import com.xgt.entity.Resource;
import com.xgt.service.ResourceService;
import com.xgt.util.JSONUtil;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService ;

    @RequestMapping(value = "/treelist")
    @ResponseBody
    public Map<String,Object> resourceList(Integer roleId){
        try{
            List<Resource> list = resourceService.getResourcesTree(roleId);
            return ResultUtil.createSuccessResult(list);
        }catch(Exception e){
            logger.error("treelist..........异常 roleId=" + roleId, e);
            return ResultUtil.createFailResult("treelist..........异常 roleId =" + roleId);
        }
    }

    @RequestMapping(value = "/menu",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> resourceList(Resource resource){
        try{
            List<Resource> list = resourceService.getMenuResources(resource);
            return ResultUtil.createSuccessResult(JSONUtil.filterIncludeProperties(list, Resource.class,
                    "id", "text", "type", "url", "iconCls", "iconCls", "parentId", "status", "leaf", "children"));
        }catch(Exception e){
            logger.error("menu..........异常 resource=" + resource.toString(), e);
            return ResultUtil.createFailResult("menu..........异常 resource =" + resource.toString());
        }
    }

    @RequestMapping(value = "/insert",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> insert(Resource resource) {
        try{
            Integer id = resourceService.insertResource(resource);
            if(id>0){
                return ResultUtil.createSuccessResult();
            }else {
                return ResultUtil.createFailResult("资源添加失败");
            }
        }catch(Exception e){
            logger.error("insert..........异常 resource=" + resource.toString(), e);
            return ResultUtil.createFailResult("insert..........异常 resource =" + resource.toString());
        }
    }

    @RequestMapping(value = "/button",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> buttonList(Integer resourceId) {
        try{
            Assert.notNull(resourceId,"resourceId不能为空");
            List<Resource> list = resourceService.getButtonResources(resourceId);
            return ResultUtil.createSuccessResult(list);
        }catch(Exception e){
            logger.error("button..........异常 resourceId=" + resourceId, e);
            return ResultUtil.createFailResult("button..........异常 resourceId =" + resourceId);
        }
    }

    @RequestMapping(value = "/update",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> update(Resource resource) {
        try{
            resourceService.updateResource(resource);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("update..........异常 resource=" + resource.toString(), e);
            return ResultUtil.createFailResult("update..........异常 resource =" + resource.toString());
        }
    }

    @RequestMapping(value = "/delete",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> delete(Resource resource) {
        try{
            resourceService.deleteResource(resource);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("delete..........异常 resource=" + resource.toString(), e);
            return ResultUtil.createFailResult("delete..........异常 resource =" + resource.toString());
        }
    }
}
