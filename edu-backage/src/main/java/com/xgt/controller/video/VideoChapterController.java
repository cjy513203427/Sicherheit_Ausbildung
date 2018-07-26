package com.xgt.controller.video;

import com.xgt.common.BaseController;
import com.xgt.entity.video.VideoChapter;
import com.xgt.service.video.VideoChapterService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-02 10:03
 **/
@Controller
@RequestMapping("/videoChapter")
public class VideoChapterController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(VideoChapterController.class);

    @Autowired
    private VideoChapterService videoChapterService ;

    /**
     * @Description pc - 查询章节列表
     * @author Joanne
     * @create 2018/6/2 14:44
     */
    @RequestMapping(value = "queryChapterList" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryChapterList(VideoChapter videoChapter){
        try {
            Map<String,Object> chapterMap = videoChapterService.queryChapterList(videoChapter);
            return ResultUtil.createSuccessResult(chapterMap);
        }catch (Exception e){
            logger.error("视频章节查询异常......",e);
            return ResultUtil.createFailResult("wrong"+e) ;
        }
    }

    /**
     * @Description pc -添加章节
     * @author Joanne
     * @create 2018/6/2 14:44
     */
    @RequestMapping(value = "/addChapter" ,produces = "application/json; charset=utf-8" )
    @ResponseBody
    public Map<String,Object> addChapter(VideoChapter videoChapter){
        try {
            videoChapter.setCreateUserId(getLoginUserId());
            videoChapterService.addChapter(videoChapter);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("章节添加失败....",e);
            return ResultUtil.createFailResult("wrong:"+e);
        }
    }

    /**
     * @Description PC-更新章节信息
     * @author Joanne
     * @create 2018/6/7 10:59
     */
    @RequestMapping(value = "/updateChapter")
    @ResponseBody
    public Map<String,Object> updateVideoInfo(VideoChapter videoChapter){
        try{
            videoChapterService.updateChapter(videoChapter);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("修改章节异常", e);
            return ResultUtil.createFailResult("修改章节..........异常"+e);
        }
    }

    /**
     * @Description 删除视频章节，并删除该章节所拥有的视频
     * @author Joanne
     * @create 2018/6/15 10:02
     */
    @RequestMapping(value = "deleteChapter" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteChapter(String ids){
        try {
            videoChapterService.deleteChapter(ids);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("删除章节异常......",e);
            return ResultUtil.createFailResult("wrong"+e) ;
        }
    }
}
