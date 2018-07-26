package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.common.PageQueryEntity;
import com.xgt.entity.EduQuestion;
import com.xgt.service.CollectionService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

@Controller
@RequestMapping("/collection")
public class CollectionController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CollectionController.class);

    @Autowired
    private CollectionService collectionService;

    /**
     * 手机端添加收藏接口
     * 收藏类型（1:视频、2：图文、3：试题）
     *
     * @param collectionType
     * @param referenceId
     * @return
     */
    @RequestMapping(value = "/addCollection", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addCollection(String collectionType, Integer referenceId) {
        try {
            Integer labourId = getLabourerUserId();
            boolean isExisting = collectionService.isExisting(labourId, collectionType, referenceId);
            if (isExisting) {
                return ResultUtil.createSuccessResult("已收藏，请不要重复添加！");
            }
            int collecionId = collectionService.addCollection(labourId, collectionType, referenceId);
            return ResultUtil.createSuccessResult(collecionId);
        } catch (Exception e) {
            logger.error("——————addCollection方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 手机端 - 删除收藏
     * 收藏类型（1:视频、2：图文、3：试题）
     *
     * @param collectionType
     * @param referenceId
     * @return
     */
    @RequestMapping(value = "/deleteCollection", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteCollection(String collectionType, Integer referenceId) {
        try {
            Integer labourId = getLabourerUserId();
            boolean isExisting = collectionService.isExisting(labourId, collectionType, referenceId);
            if (!isExisting) {
                return ResultUtil.createSuccessResult("未收藏，无法删除！");
            }
            collectionService.deleteCollection(labourId, collectionType, referenceId);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————deleteCollection：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }



    /**
     *  查询我的收藏列表
     * @author liuao
     * @date 2018/6/11 9:25
     */
    @RequestMapping(value = "/queryCollectionList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryCollectionList(String collectionType, Integer pageSize, Integer lastId) {
        try {
            Integer labourId = getLabourerUserId();
//            Integer labourId = 1 ;
            List result  = collectionService.queryCollectionList(labourId, collectionType, lastId, pageSize);

            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryCollectionList：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 根据题库id 查询出该上一个或者下一题 for 收藏的题目
     * nextPrevFlag 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/11 19:57
     */
    @RequestMapping(value = "/queryNextOrPrevQuestionForCollect", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryNextOrPrevQuestionForCollect(Integer questionBankId,
                                                                 Integer questionId ,String nextPrevFlag) {
        try {

            if(null == questionBankId || null == nextPrevFlag){
                logger.info("questionBankId:{},nextPrevFlag:{}",questionBankId, nextPrevFlag);
                return ResultUtil.createFailResult("传值不正确");
            }
            Integer labourId = getLabourerUserId();
            EduQuestion result  = collectionService.queryNextOrPrevQuestionForCollect(labourId, questionBankId, questionId, nextPrevFlag);

            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryCollectionList：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
