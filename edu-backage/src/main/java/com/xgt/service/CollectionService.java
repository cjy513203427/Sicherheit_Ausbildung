package com.xgt.service;

import com.xgt.common.PageQueryEntity;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.CollectionDao;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.imgtext.ImgtextDao;
import com.xgt.dao.video.VideoMaterialDao;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.EduQuestionBank;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.video.Video;
import org.apache.commons.collections.CollectionUtils;
import org.omg.CORBA.INTERNAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *  我的收藏service 层
 * @author liuao
 * @date 2018/6/14 11:51
 */
@Service
public class CollectionService {

    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private VideoMaterialDao videoMaterialDao ;

    @Autowired
    private ImgtextDao imgtextDao;

    @Autowired
    private EduQuestionDao eduQuestionDao ;

    private Integer PAGE_SIZE = 20 ;

    /**
     * 手机端添加收藏
     *
     * @param labourId
     * @param collectionType
     * @param referenceId
     */
    public int addCollection(Integer labourId, String collectionType, Integer referenceId) {
        return collectionDao.addCollection(labourId, collectionType, referenceId);
    }


    /**
     * 手机端是否已经收藏
     *
     * @param labourId
     * @param collectionType
     * @param referenceId
     */
    public boolean isExisting(Integer labourId, String collectionType, Integer referenceId) {
        return collectionDao.isExisting(labourId, collectionType, referenceId);
    }

    /**  查询 我的收藏列表
     * @author liuao
     * @date 2018/6/11 9:41
     */
    public List<Object> queryCollectionList(Integer labourId, String collectionType,
                                                  Integer lastId, Integer pageSize) {
        pageSize = pageSize == null || PAGE_SIZE.compareTo(pageSize) > 0 ? PAGE_SIZE : pageSize ;

        List result = null;
        // 因为是倒叙分页，所以设置一个id的最大值，作为筛选条件
        lastId = null == lastId ? Integer.MAX_VALUE : lastId;
        switch (collectionType){
            // 视频
            case SystemConstant.CollectionType.VIDEO :
                result = videoMaterialDao.queryCollectVideoBy(labourId,
                        lastId, pageSize, collectionType);
                break ;
            // 图文
            case SystemConstant.CollectionType.IMGTXT :
                result = imgtextDao.queryCollectImgtxtBy(labourId,
                        lastId, pageSize, collectionType);
                break ;
            // 题目
            case SystemConstant.CollectionType.QUESTION :
                // 查询题库和题目数量
                result = eduQuestionDao.queryCollectQuestionBankBy(labourId,
                        lastId, pageSize, collectionType);

                if(CollectionUtils.isNotEmpty(result)){
                    List<EduQuestionBank> eduQuestionBankList = (List<EduQuestionBank>)result;
                    for (EduQuestionBank eduQuestionBank:  eduQuestionBankList ) {
                        // 设置该题库收藏了多少题
                        Integer questionCount = eduQuestionDao.queryCollectQuestionCountBy(labourId,
                                eduQuestionBank.getId(), collectionType);
                        eduQuestionBank.setQuestionCount(questionCount);
                    }
                }

                break ;

            default:
            return null ;
        }
        return result ;
    }

    /**
     *  根据题库id 查询出该上一个或者下一题户收藏的题目
     * @author liuao
     * nextPrevFlag : 1：下一个 、 2:上一个
     * @date 2018/6/11 20:27
     */
    public EduQuestion queryNextOrPrevQuestionForCollect(Integer labourId, Integer questionBankId, Integer questionId, String nextPrevFlag) {
        EduQuestion question = eduQuestionDao.queryNextOrPrevQuestionForCollect(labourId, questionBankId,
                questionId, nextPrevFlag);
        if(null == question){
            // 查询出题目答案
            List<EduQuestionAnswer> answerList = eduQuestionDao.queryEduQuestionAnswer(question.getId());
            question.setAnswers(answerList);
        }
        return question;
    }

    /**
     * 手机端 - 删除收藏
     * 收藏类型（1:视频、2：图文、3：试题）
     *
     * @param collectionType
     * @param referenceId
     * @return
     */
    public void deleteCollection(Integer labourId, String collectionType, Integer referenceId) {
        collectionDao.deleteCollection(labourId, collectionType, referenceId);
    }
}
