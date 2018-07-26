package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-11 20:21
 **/
@Repository
public class NoticeDao {
    private static final Logger logger = LoggerFactory.getLogger(NoticeDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "notice.";

    /**
     * @Description 新增通知
     * @author Joanne
     * @create 2018/6/11 19:42
     */
    public void addNotice(Map eduNotices) {
        String sqlId = NAMESPACE + "addNotice" ;
        daoClient.batchInsertAndGetId(sqlId,eduNotices);
    }

    /**
     * @Description 根据labourId查询通知
     * @author Joanne
     * @create 2018/6/12 9:57
     */
    public List<EduNotice> allNoticeToLabour(Integer labourerUserId) {
        String sqlId = NAMESPACE + "allNoticeToLabour" ;
        Map param = new HashMap();
        param.put("labourerId",labourerUserId);
        return daoClient.queryForList(sqlId,param,EduNotice.class);
    }

    /**
     * @Description 根据id和人员身份删除通知
     * @author Joanne
     * @create 2018/6/12 10:37
     */
    public void deleteNotice(EduNotice eduNotice) {
        String sqlId = NAMESPACE + "deleteNotice" ;
        daoClient.excute(sqlId,eduNotice);
    }

    /**
     * @Description 根据用户id和通知id更新通知已读状态
     * @author Joanne
     * @create 2018/6/14 10:19
     */
    public void readNoticeByNoticeId(Integer labourerUserId, Integer noticeId) {
        String sqlId = NAMESPACE + "readNoticeByNoticeId";
        Map params = new HashMap();
        params.put("labourId",labourerUserId);
        params.put("id",noticeId);
        daoClient.excute(sqlId,params);
    }

    /**
     * @Description 根据视频集锦删除通知
     * @author Joanne
     * @create 2018/6/15 15:02
     */
    public void deleteNoticeByReferenceIdAndType(String ids,String noticeType,Integer limit) {
        String sqlId = NAMESPACE + "deleteNoticeByMaterialId";
        Map map = new HashMap();
        map.put("ids",ids);
        map.put("noticeType",noticeType);
        map.put("limit",limit);
        daoClient.excute(sqlId,map);
    }
}
