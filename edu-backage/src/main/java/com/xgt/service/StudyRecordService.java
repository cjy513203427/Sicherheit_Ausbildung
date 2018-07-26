package com.xgt.service;


import com.xgt.constant.SystemConstant;
import com.xgt.controller.StudyRecordController;
import com.xgt.dao.imgtext.ImgtextDao;
import com.xgt.dao.video.VideoMaterialDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class StudyRecordService {

    private static final Logger logger = LoggerFactory.getLogger(StudyRecordService.class);
    private Integer PAGE_SIZE = 20 ;

    @Autowired
    private VideoMaterialDao videoMaterialDao ;

    @Autowired
    private ImgtextDao imgtextDao;
    /**
     *  根据条件分页查询学习记录
     *  lastId ： 已展示记录中最后一个记录的id
     * @author liuao
     * @date 2018/6/11 14:52
     */
    public List queryStudyRecord(Integer lastId, Integer labourId, String collectionType, Integer pageSize) {

        pageSize = pageSize == null || PAGE_SIZE.compareTo(pageSize) > 0 ? PAGE_SIZE : pageSize ;

        List result = null;
        // 因为是倒叙分页，所以设置一个id的最大值，作为筛选条件
        lastId = null == lastId ? Integer.MAX_VALUE : lastId;
        switch (collectionType){
            // 视频
            case SystemConstant.CollectionType.VIDEO :
                result = videoMaterialDao.queryStudyReorcdBy(labourId,
                        lastId, pageSize);
                break ;
            // 图文
            case SystemConstant.CollectionType.IMGTXT :
                result = imgtextDao.queryStudyReorcdBy(labourId,
                        lastId, pageSize);
                break ;

            default:
                return null ;
        }
        return result ;
    }
}
