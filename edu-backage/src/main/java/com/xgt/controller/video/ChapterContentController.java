package com.xgt.controller.video;

import com.xgt.common.BaseController;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.EduVideoLabourRel;
import com.xgt.service.video.ChapterContentService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.PlayStatus.FINISHED;
import static com.xgt.constant.SystemConstant.PlayStatus.UNDERWAY;

/**
 * @author Joanne
 * @Description 章节视频内容
 * @create 2018-06-02 9:33
 **/
@Controller
@RequestMapping("/chapterContent")
public class ChapterContentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ChapterContentController.class);

    @Autowired
    private ChapterContentService chapterContentService ;

    /**
     * @Description pc -查询章节视频列表
     * @author Joanne
     * @create 2018/6/2 14:43
     */
    @RequestMapping(value = "queryContentList" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryContentList(ChapterContent chapterContent){
        try {
            Map<String,Object> chapterMap = chapterContentService.queryContentList(chapterContent);
            return ResultUtil.createSuccessResult(chapterMap);
        }catch (Exception e){
            logger.error("视频内容查询异常......",e);
            return ResultUtil.createFailResult("wrong"+e) ;
        }
    }

    /**
     * @Description 删除视频信息
     * @author Joanne
     * @create 2018/6/14 17:58
     */
    @RequestMapping(value = "delete" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteContent(String ids){
        try {
            chapterContentService.deleteContent(ids);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("删除异常......",e);
            return ResultUtil.createFailResult("wrong"+e) ;
        }
    }

    /**
     * @Description pc -添加章节视频内容
     * @author Joanne
     * @create 2018/6/2 14:43
     */
    @RequestMapping(value = "/addChapterContent" , produces = "application/json; charset=utf-8" )
    public Map<String,Object> addChapterContent(ChapterContent chapterContent, MultipartFile file){
        try {
            chapterContent.setCreateUserId(getLoginUserId());
            chapterContentService.addChapterContent(chapterContent,file);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("视频内容添加失败....",e);
            return ResultUtil.createFailResult("wrong:"+e);
        }
    }

    /**
     * @Description 更新章节视频信息
     * @author Joanne
     * @create 2018/6/7 11:03
     */
    @RequestMapping(value = "/updateContent")
    @ResponseBody
    public Map<String,Object> updateContent(ChapterContent chapterContent,MultipartFile file){
        try{
            chapterContentService.updateContent(chapterContent,file);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("修改视频异常", e);
            return ResultUtil.createFailResult("修改视频..........异常"+e);
        }
    }

    /**
     * @Description 移动端 标记用户观看视频时长
     * @author Joanne
     * @create 2018/6/8 14:59
     */
    @RequestMapping(value = "/markWatchVideo")
    @ResponseBody
    public Map<String,Object> markWatchVideo(EduVideoLabourRel eduVideoLabourRel) {
        try{
            eduVideoLabourRel.setLabourId(getLabourerUserId());
            eduVideoLabourRel.setPlayStatus(UNDERWAY);
            chapterContentService.markWatchVideo(eduVideoLabourRel);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("记录观看视频时长异常", e);
            return ResultUtil.createFailResult("修改视频..........异常"+e);
        }
    }


    /**
     * @Description 视频播放完毕触发事件
     * @author Joanne
     * @create 2018/6/8 20:54
     */
    @RequestMapping(value = "/markVideoPlayEndByContentId")
    @ResponseBody
    public Map<String,Object> markVideoPlayEndByContentId(EduVideoLabourRel eduVideoLabourRel) {
        try{
            eduVideoLabourRel.setLabourId(getLabourerUserId());
            eduVideoLabourRel.setPlayStatus(FINISHED);
            chapterContentService.markVideoPlayEndByContentId(eduVideoLabourRel);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("记录观看视频时长异常", e);
            return ResultUtil.createFailResult("修改视频..........异常"+e);
        }
    }

    /**
     * @Description  培训系统PC版 - 查询视频内容及试题概况
     * @author Joanne
     * @create 2018/7/18 17:03
     */
    @RequestMapping(value = "/queryContentByVideoId")
    @ResponseBody
    public Map<String,Object> queryContentByVideoId(Integer catalogId , //目录id
                                                    String videoName //视频内容名称，title
    ) {
        try{
            List<ChapterContent> contentInfo = chapterContentService.queryContentByVideoId(catalogId,videoName);
            return ResultUtil.createSuccessResult(contentInfo);
        }catch(Exception e){
            logger.error("记录观看视频时长异常", e);
            return ResultUtil.createFailResult("修改视频..........异常"+e);
        }
    }
}
