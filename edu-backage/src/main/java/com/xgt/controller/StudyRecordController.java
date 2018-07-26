package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.service.StudyRecordService;
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

/**
 *  学习记录 controller
 * @author liuao
 * @date 2018/6/11 14:30
 */
@Controller
@RequestMapping("/studyRecord")
public class StudyRecordController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(StudyRecordController.class);


    @Autowired
    private StudyRecordService studyRecordService ;


    /**
     *  查询学习记录
     * @author liuao
     * @date 2018/6/11 15:41
     */
    @RequestMapping(value = "/queryStudyRecord", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryStudyRecord(Integer lastId, String collectionType, Integer pageSize) {
        try {

            Integer labourId = getLabourerUserId();
            List result = studyRecordService.queryStudyRecord(lastId, labourId, collectionType, pageSize);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("...............queryStudyRecord 方法异常：..", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
