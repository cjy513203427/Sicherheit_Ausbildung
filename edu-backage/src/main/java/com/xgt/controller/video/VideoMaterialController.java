package com.xgt.controller.video;

import com.xgt.common.BaseController;
import com.xgt.common.PageQueryEntity;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.Video;
import com.xgt.entity.video.VideoComment;
import com.xgt.service.video.VideoMaterialService;
import com.xgt.util.DeleteFileUtil;
import com.xgt.util.FileToolUtil;
import com.xgt.util.ResultUtil;
import com.xgt.util.VideoEncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.COMMA;

/**
 * @author Joanne
 * @Description 视频材料管理（视频集锦）
 * @create 2018-06-01 16:05
 **/
@Controller
@RequestMapping("/video")
public class VideoMaterialController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(VideoMaterialController.class);

    @Autowired
    private VideoMaterialService videoMaterialService ;

    /**
     * @Description pc-查询视频
     * @author Joanne
     * @create 2018/6/1 17:11
     */
    @RequestMapping(value = "/videoInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> videoInfo(Video video){
        try{
            Map<String,Object> videoInfo = videoMaterialService.videoInfo(video);
            return ResultUtil.createSuccessResult(videoInfo);
        }catch(Exception e){
            logger.error("videotree..........异常 ", e);
            return ResultUtil.createFailResult("videotree..........异常");
        }
    }

    /**
     * @Description pc-视频信息添加
     * @author Joanne
     * @create 2018/6/1 17:13
     */
    @RequestMapping(value = "/addVideoInfo")
    @ResponseBody
    public Map<String,Object> addVideoInfo(Video video){
        try{
            video.setCreateUserId(getLoginUserId());
            videoMaterialService.addVideoInfo(video);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("添加视频信息异常", e);
            return ResultUtil.createFailResult("添加视频..........异常");
        }
    }

    /**
     * @Description pc -修改视频集锦
     * @author Joanne
     * @create 2018/6/2 15:32
     */
    @RequestMapping(value = "/updateVideoInfo")
    @ResponseBody
    public Map<String,Object> updateVideoInfo(Video video,MultipartFile file){
        try{
            videoMaterialService.updateVideoInfo(video);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("修改视频信息异常", e);
            return ResultUtil.createFailResult("修改视频..........异常"+e);
        }
    }

    /**
     * @Description 删除视频集锦，并删除所有章节及视频
     * @author Joanne
     * @create 2018/6/15 10:51
     */
    @RequestMapping(value = "deleteVideoCollection" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteVideoCollection(String ids){
        try {
            videoMaterialService.deleteVideoCollection(ids);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("删除章节异常......",e);
            return ResultUtil.createFailResult("wrong"+e) ;
        }
    }

    /**
     * @Description 移动端 -获取视频集锦列表 搜寻条件为按时间（createTime默认）或按热度（点击次数clickNumber）
     * @author Joanne
     * @create 2018/6/7 14:51
     */
    @RequestMapping(value = "/videoCollectionInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> videoCollectionInfo(Video video){
        try{
            video.setPostType(getLoginLabourer().getPostType());
//            video.setPostType("1");
            List<Video> videoInfo = videoMaterialService.videoCollectionInfo(video);
            return ResultUtil.createSuccessResult(videoInfo);
        }catch(Exception e){
            logger.error("videotree..........异常 ", e);
            return ResultUtil.createFailResult("videotree..........异常");
        }
    }


    /**
     * @Description 移动端 -根据集锦ID获取集锦的全部信息（包括视频，章节，简介等）
     * @author Joanne
     * @create 2018/6/7 15:10
     */
    @RequestMapping(value = "/queryDetialByCollectionId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryDetialByCollectionId(Integer collectionId){
        try{
                Video videoInfo = videoMaterialService.queryDetialByCollectionId(collectionId,getLabourerUserId());
//            Video videoInfo = videoMaterialService.queryDetialByCollectionId(collectionId,9);
            return ResultUtil.createSuccessResult(videoInfo);
        }catch(Exception e){
            logger.error("videotree..........异常 ", e);
            return ResultUtil.createFailResult("videotree..........异常");
        }
    }

    /**
     * @Description 培训系统PC版 获取课程树及节点名称
     * @author Joanne
     * @create 2018/6/26 14:15
     */
    @RequestMapping(value = "/queryAllCourses", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryAllCourses(){
        try{
            List<Video> allCourses = videoMaterialService.queryAllCourses();
            return ResultUtil.createSuccessResult(allCourses);
        }catch(Exception e){
            logger.error("获取课程失败", e);
            return ResultUtil.createFailResult("coursetree..........异常");
        }
    }

    /**
     * @Description 培训系统PC版 - 查询视频集锦
     * @author Joanne
     * @create 2018/7/18 16:45
     */
    @RequestMapping(value = "/queryCatelog", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryCatelog(){
        try{
            List<Video> catalogs = videoMaterialService.queryCatelog();
            return ResultUtil.createSuccessResult(catalogs);
        }catch(Exception e){
            logger.error("获取课程失败", e);
            return ResultUtil.createFailResult("coursetree..........异常");
        }
    }


    /**
     * @Description
     * 培训系统PC端 - 查询课程树（树包含视频地址）
     * @author Joanne
     * @create 2018/7/16 15:44
     */
    @RequestMapping(value = "/queryCoursesWithVideo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryCoursesWithVideo(Integer programId){
        try{
            Map map = videoMaterialService.queryCoursesWithVideo(programId);
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("获取课程失败", e);
            return ResultUtil.createFailResult("coursetree..........异常"+e);
        }
    }


    /**
     * @Description 移动端- 用户增加评论
     * @author Joanne
     * @create 2018/6/7 20:33
     */
    @RequestMapping(value = "/addComments", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> addComments(VideoComment videoComment){
        try{
            videoComment.setCreateId(getLabourerUserId());
            videoComment.setCreateUserName(getLoginLabourer().getRealname());
//          videoComment.setCreateId(1);
            //插入评论获取评论id
            Integer id = videoMaterialService.addComments(videoComment);
            //根据评论id查询评论信息
            videoComment = videoMaterialService.queryCommentsByCommentId(id);
            return ResultUtil.createSuccessResult(videoComment);
        }catch(Exception e){
            logger.error("videotree..........异常 ", e);
            return ResultUtil.createFailResult("videotree..........异常");
        }
    }

    /**
     * @Description 移动端 - 根据集锦id查询评论，需分页查询
     * @author Joanne
     * @create 2018/6/7 19:36
     */
    @RequestMapping(value = "/queryCommentsByCollectionId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryCommentsByCollectionId(VideoComment videoComment){
        try{
            List<VideoComment> videoCommentList = videoMaterialService.queryCommentsByCollectionId(videoComment);
            return ResultUtil.createSuccessResult(videoCommentList);
        }catch(Exception e){
            logger.error("videotree..........异常 ", e);
            return ResultUtil.createFailResult("videotree..........异常");
        }
    }

    /**
     * @Description 根据视频内容ID查询视频详细信息
     * @author Joanne
     * @create 2018/7/11 15:44
     */
    @RequestMapping(value = "/queryContentByCourseIds", produces = "application/json; charset=utf-8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> queryContentByCourseIds(HttpServletRequest req,PageQueryEntity pageQueryEntity){
        try{
            String[] array = req.getParameterValues("ids[]");
            String ids = "";
            for (String string : array) {
                ids += string+COMMA;
            }
            Map<String,Object> courseList = videoMaterialService.queryContentByCourseIds(ids.substring(0,ids.lastIndexOf(COMMA)),pageQueryEntity);
            return ResultUtil.createSuccessResult(courseList);
        }catch(Exception e){
            logger.error("查询课程详情..........异常 ", e);
            return ResultUtil.createFailResult("查询课程详情..........异常");
        }
    }

//    @RequestMapping(value = "/encryptVideo", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public Map<String,Object> encryptVideo(String fileName){
//        try{
//            videoMaterialService.encryptVideo(fileName);
//            return ResultUtil.createSuccessResult();
//        }catch(Exception e){
//            logger.error("encryptVideo..........异常,文件名是 "+fileName, e);
//            return ResultUtil.createFailResult("encryptVideo..........异常");
//        }
//    }

    /**
     *  解密视频文件
     * @author liuao
     * @date 2018/7/20 10:33
     */
    @RequestMapping(value = "/decryptVideo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> decryptVideo(String fileName){
        try{
            videoMaterialService.decryptVideo(fileName);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("decryptVideo..........异常,文件名是 "+fileName, e);
            return ResultUtil.createFailResult("decryptVideo..........异常");
        }
    }


    /**
     * 播放加密MP4
     * @param response
     * @throws IOException
     */
    @RequestMapping("/playMp4")
    @ResponseBody
    public void playMp4(HttpServletResponse response,String fileName) throws Exception {

        // 解密过后的临时文件路径
        String tempFilePath = SystemConstant.VIDEO_TEMP_PATH + fileName;

        FileInputStream inputStream = new FileInputStream(tempFilePath);
        byte[] data = FileToolUtil.inputStreamToByte(inputStream);
        String diskfilename = "final.mp4";
        response.setContentType("video/mp4");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"" );
        System.out.println("data.length " + data.length);
        response.setContentLength(data.length);
        response.setHeader("Content-Range", "" + Integer.valueOf(data.length-1));
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Etag", "W/\"9767057-1323779115364\"");
        byte[] content = new byte[1024];
        BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
        OutputStream os = response.getOutputStream();
        while (is.read(content) != -1) {
            os.write(content);
        }
        //先声明的流先关掉！

        os.close();
        is.close();
        inputStream.close();
        DeleteFileUtil.delete(tempFilePath);
    }
}
