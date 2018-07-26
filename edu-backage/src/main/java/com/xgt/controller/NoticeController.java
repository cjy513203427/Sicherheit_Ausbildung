package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.entity.EduNotice;
import com.xgt.service.NoticeService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.ReadStatus.READED;

/**
 * @author Joanne
 * @Description 通知controller
 * @create 2018-06-12 9:42
 **/
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService ;

    /**
     * @Description 根据labourId查询通知列表
     * @author Joanne
     * @create 2018/6/12 9:53
     */
    @RequestMapping(value = "/allNoticeToLabour", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> allNoticeToLabour(){
        try {
            List<EduNotice> eduNotices = noticeService.allNoticeToLabour(getLabourerUserId());
            return ResultUtil.createSuccessResult(eduNotices);
        }catch (Exception e){
            logger.error("获取通知错误:",e);
            return ResultUtil.createFailResult("获取通知错误:"+e);
        }
    }


    /**
     * @Description 通知已读
     * @author Joanne
     * @create 2018/6/14 10:17
     */
    @RequestMapping(value = "/readNoticeByNoticeId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> readNoticeByNoticeId(Integer noticeId){
        try {
            noticeService.readNoticeByNoticeId(getLabourerUserId(),noticeId);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("获取通知错误:",e);
            return ResultUtil.createFailResult("获取通知错误:"+e);
        }
    }

    /**
     * @Description 移动端 - 删除通知
     * @author Joanne
     * @create 2018/6/12 10:48
     */
    @RequestMapping(value = "/deleteNoticeByNoticeId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteNotice(Integer eduNoticeId){
        try {
            EduNotice eduNotice = new EduNotice();
            eduNotice.setId(eduNoticeId);
            eduNotice.setLabourId(getLabourerUserId());
            noticeService.deleteNotice(eduNotice);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("删除通知错误:",e);
            return ResultUtil.createFailResult("删除通知错误:"+e);
        }
    }
}
