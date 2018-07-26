package com.xgt.controller.sync;

import com.xgt.dao.video.ChapterContentDao;
import com.xgt.dao.video.VideoMaterialDao;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.Video;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 *
 *  *********** 远程服务  ***************
 *  该contoller 是 提供给一体机客户端调用的，会部署再服务器上
 *  同步数据时，需要从服务端获取数据，并保存再一体机上
 * @author liuao
 * @date 2018/7/18 10:07
 */
@Controller
@Path("/videoRemoteServer")
public class RemoteServerVideoController {
    private static final Logger logger = LoggerFactory.getLogger(RemoteServerVideoController.class);
    @Autowired
    private VideoMaterialDao videoMaterialDao;

    @Autowired
    private ChapterContentDao chapterContentDao;


    /**
     *   根据  视频章节内容Id ，查询id 大于当前该 视频章节内容Id 的下一个视频章节内容信息
     * @author liuao
     * @date 2018/7/18 10:08
     */
    @Path("/queryNextChapterContentById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> queryNextChapterContentById(@FormParam("previousId") Integer previousId) {

        try {

            ChapterContent videoChapter = chapterContentDao.queryNextChapterContentById(previousId);
            return ResultUtil.createSuccessResult(videoChapter);
        } catch (Exception e) {
            logger.error("——————queryNextChapterContentById：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     *   根据  视频章节内容Id ，查询 视频章节内容信息
     * @author liuao
     * @date 2018/7/18 10:08
     */
    @Path("/queryChapterContentById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> queryChapterContentById(@FormParam("chapterContentId") Integer chapterContentId) {

        try {
            ChapterContent videoChapter = chapterContentDao.queryContentByContentId(chapterContentId);
            return ResultUtil.createSuccessResult(videoChapter);
        } catch (Exception e) {
            logger.error("——————queryChapterContentById：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     *  根据视频集锦id ，查询视频集锦
     * @author liuao
     * @date 2018/7/18 10:08
     */
    @Path("/queryVideoById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> queryVideoById(@FormParam("videoId") Integer videoId) {

        try {

            Video video = videoMaterialDao.queryVideoById(videoId);
            return ResultUtil.createSuccessResult(video);
        } catch (Exception e) {
            logger.error("——————queryVideoById：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }






    @Path("/test")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> test( @FormParam("str") String str) {

        try {
            return ResultUtil.createSuccessResult(str);
        } catch (Exception e) {
            logger.error("——————test：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }




}
