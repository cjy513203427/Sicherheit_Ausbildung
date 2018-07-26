package com.xgt.service;

import com.xgt.dao.NoticeDao;
import com.xgt.entity.EduNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.ContentTemplate.NEW_PIC_ARTICLE;
import static com.xgt.constant.SystemConstant.ContentTemplate.NEW_VIDEO;
import static com.xgt.constant.SystemConstant.NoticeType.PIC_ARTICLE_COURSE_NOTICE;
import static com.xgt.constant.SystemConstant.NoticeType.VIDEO_COURSE_NOTICE;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-11 20:21
 **/
@Service
public class NoticeService {

    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
    @Autowired
    private NoticeDao noticeDao ;

    /**
     * @Description 新增通知
     * @author Joanne
     * @create 2018/6/11 20:51
     */
    public void addNotice(Map eduNoticeTemplate) {
        if(((List<Integer>)eduNoticeTemplate.get("labourIds")).size()>0){
            logger.info("存在用户并开始发送通知.....");
            List<EduNotice> eduNotices = new ArrayList<>();
            //组装通知
            for (Integer labourId:(List<Integer>)eduNoticeTemplate.get("labourIds") ){
                EduNotice eduNotice = new EduNotice();
                eduNotice.setLabourId(labourId);
                eduNotice.setNoticeContent(eduNoticeTemplate.get("content").toString());
                eduNotice.setNoticeType(eduNoticeTemplate.get("noticeType").toString());
                eduNotice.setReferenceId((int)eduNoticeTemplate.get("id"));
                eduNotices.add(eduNotice);
            }
            Map map = new HashMap();
            map.put("eduNotices",eduNotices);
            //新增通知
            noticeDao.addNotice(map);
        }
    }

    /**
     * @Description 组装新增视频通知（外键id 为集锦id）
     * @author Joanne
     * @create 2018/6/11 20:55
     */
    public void assambleVideoNotice(List<Integer> labourIds, Integer id, String title){
        //新增通知
        Map<String,Object> map = new HashMap();
        map.put("content",String.format(NEW_VIDEO,title));
        map.put("noticeType",VIDEO_COURSE_NOTICE);
        map.put("id",id);
        map.put("labourIds",labourIds);
        addNotice(map);
    }

    /**
     * @Description 组装图文通知
     * @author Joanne
     * @create 2018/6/12 11:56
     */
    public void assamblePicArticleNotice(List<Integer> labourIds, Integer id, String title){
        //新增通知
        Map<String,Object> map = new HashMap();
        map.put("content",String.format(NEW_PIC_ARTICLE,title));
        map.put("noticeType",PIC_ARTICLE_COURSE_NOTICE);
        map.put("id",id);
        map.put("labourIds",labourIds);
        addNotice(map);
    }

    /**
     * @Description 根据labourId查询通知列表
     * @author Joanne
     * @create 2018/6/12 9:54
     */
    public List<EduNotice> allNoticeToLabour(Integer labourerUserId) {
        return noticeDao.allNoticeToLabour(labourerUserId);
    }

    /**
     * @Description 删除通知
     * @author Joanne
     * @create 2018/6/12 10:48
     */
    public void deleteNotice(EduNotice eduNotice) {
        noticeDao.deleteNotice(eduNotice);
    }

    /**
     * @Description 通知已读
     * @author Joanne
     * @create 2018/6/14 10:37
     */
    public void readNoticeByNoticeId(Integer labourerUserId,Integer noticeId) {
        noticeDao.readNoticeByNoticeId(labourerUserId,noticeId);
    }
}
