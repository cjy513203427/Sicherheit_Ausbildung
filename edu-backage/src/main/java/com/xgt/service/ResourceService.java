package com.xgt.service;

import com.xgt.dao.ResourceDao;
import com.xgt.entity.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joanne
 * @Description
 * @create 2018-03-30 19:16
 **/
@Service
public class ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    @Autowired
    private ResourceDao resourceDao ;

    /**
     *  根据用户id 和资源类型 查询可用的 权限资源
     * @author liuao
     * @date 2018/4/1 15:07
     */
    public List<Resource> queryResourceListByUserIdAndType(Integer userId, String resourcetype ) {
        return resourceDao.queryResourceListByUserIdAndType(userId, resourcetype);
    }

    /**
     * 构建资源数
     * @param list list
     * @return list
     */
    public List<Resource> buildResource(List<Resource> list) {
        List<Resource> target = new ArrayList<>();
        if (!list.isEmpty()) {
            // 根元素
            list.stream().filter(resource -> resource.getParentId() == 0).forEach(resource -> {// 根元素
                List<Resource> children = getChildren(resource, list);
                resource.setChildren(children);
                target.add(resource);
            });
        }
        return target;
    }

    private void checkResource(List<Resource> sources,List<Resource> targets) {
        for (Resource target : targets) {
            for (Resource source :sources) {
                if(source!=null){
                    if (target.getId()==source.getId()) {
                        target.setChecked(true);
                        break;
                    }
                }
            }
        }
    }

    private List<Resource> getChildren(Resource resource,List<Resource> list) {
        List<Resource> children = new ArrayList<>();
        if (!list.isEmpty()) {
            list.stream().filter(child -> resource.getId() == child.getParentId()).forEach(child -> {
                List<Resource> tmp = getChildren(child, list);
                child.setChildren(tmp);
                if (tmp.isEmpty()) {
                    child.setLeaf(true);
                }
                children.add(child);
            });
        }
        return children;
    }

    public List<Resource> getMenuResources(Resource resource) {
        List<Resource> list = resourceDao.selectMemuResource(resource);
        return  buildResource(list);
    }

    public Integer insertResource(Resource resource) {
        return resourceDao.insertResource(resource);
    }

    public List<Resource> getButtonResources(Integer resourceId) {
        return resourceDao.selectButtonResource(resourceId);
    }

    public List<Resource> getResourcesTree(Integer roleId) {
        List<Resource> list = resourceDao.selectResource();
        if (roleId != null) {
            List<Resource> resources = resourceDao.selectResourceByRole(roleId);
            checkResource(resources,list);
        }
        return  buildResource(list);
    }

    public void updateResource(Resource resource) {
        resourceDao.updateResource(resource);
    }

    public void deleteResource(Resource resource) {
        resourceDao.deleteResource(resource);
    }
}
