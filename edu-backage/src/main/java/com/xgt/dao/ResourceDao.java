package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.Resource;
import com.xgt.entity.Role;
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
public class ResourceDao {
    private static final Logger logger = LoggerFactory.getLogger(ResourceDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "resource.";

    /**
     *  根据用户id 查询改用的 权限资源
     * @author liuao
     * @date 2018/4/1 15:07
     */
    public List<Resource> queryResourceListByUserIdAndType(Integer userId, String resourcetype ) {
        String sqlId = NAMESPACE + "queryResourceListByUserIdAndType";
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("type", resourcetype);
        return daoClient.queryForList(sqlId, param, Resource.class);
    }

    public List<Resource> selectMemuResource(Resource resource) {
        String sqlId = NAMESPACE + "selectMenuResource" ;
        return daoClient.queryForList(sqlId,resource,Resource.class);
    }

    public Integer insertResource(Resource resource) {
        String sqlId = NAMESPACE + "insertResource" ;
        return daoClient.insertAndGetId(sqlId,resource);
    }

    public List<Resource> selectButtonResource(Integer resourceId) {
        String sqlId = NAMESPACE + "selectButtonResource" ;
        Resource resource = new Resource();
        resource.setId(resourceId);
        return daoClient.queryForList(sqlId,resource,Resource.class);
    }

    public List<Resource> selectResource() {
        String sqlId = NAMESPACE + "selectResource" ;
        Resource resource = new Resource();
        return daoClient.queryForList(sqlId,resource,Resource.class);
    }

    public List<Resource> selectResourceByRole(Integer roleId) {
        String sqlId = NAMESPACE + "selectResourceByRole" ;
        Role role = new Role();
        role.setId(roleId);
        return daoClient.queryForList(sqlId,role,Resource.class);
    }

    public void updateResource(Resource resource) {
        String sqlId = NAMESPACE + "updateResource" ;
        daoClient.excute(sqlId,resource);
    }

    public void deleteResource(Resource resource) {
        String sqlId = NAMESPACE + "deleteResource" ;
        daoClient.excute(sqlId,resource);
    }
}
