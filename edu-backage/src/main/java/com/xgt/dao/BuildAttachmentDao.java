package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.BuildAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author cjy
 * @Date 2018/6/26 14:49.
 */
@Repository
public class BuildAttachmentDao {
    private static final Logger logger = LoggerFactory.getLogger(BuildAttachmentDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "buildAttachment.";

    /**
     * 添加单位附件
     * @param buildAttachment
     */
    public void addBuildSiteAttachment(BuildAttachment buildAttachment) {
        String sqlId = name_space + "addBuildSiteAttachment";
        daoClient.insertAndGetId(sqlId, buildAttachment);
    }

    /**
     * 添加工人附件，新增学员其他信息
     * @param buildAttachment
     */
    public void addBuildLabourerAttachment(BuildAttachment buildAttachment){
        String sqlId = name_space + "addBuildLabourerAttachment";
        daoClient.insertAndGetId(sqlId,buildAttachment);
    }

    /**
     * 修改工人附件
     * @param buildAttachment
     */
    public void modifyBuildSiteAttachment(BuildAttachment buildAttachment){
        String sqlId = name_space + "modifyBuildSiteAttachment";
        daoClient.insertAndGetId(sqlId,buildAttachment);
    }
}
