package com.xgt.controller.sync;

import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.video.ChapterContentDao;
import com.xgt.dao.video.VideoMaterialDao;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.EduVideoQuestionRel;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.Video;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
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
@Path("/remoteServerQuestionRel")
public class RemoteServerQuestionRelController {
    private static final Logger logger = LoggerFactory.getLogger(RemoteServerQuestionRelController.class);

    @Autowired
    private ChapterContentDao chapterContentDao;


    @Autowired
    private EduQuestionDao eduQuestionDao ;


    /**
     *   根据  视频题目关系Id ，查询id 大于当前该 “视频题目关系Id”的下一个视频题目关系
     * @author liuao
     * @date 2018/7/18 10:08
     */
    @Path("/queryNextQuestionRelById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> queryNextQuestionRelById(@FormParam("previousId") Integer previousId) {

        try {

            EduVideoQuestionRel questionRel = chapterContentDao.queryNextQuestionRelById(previousId);
            return ResultUtil.createSuccessResult(questionRel);
        } catch (Exception e) {
            logger.error("——————queryNextQuestionRelById：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     *   根据题目Id ，查询题目和题目选项
     * @author liuao
     * @date 2018/7/18 10:08
     */
    @Path("/queryQuestionAndOptionById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> queryQuestionAndOptionById(@FormParam("questionId") Integer questionId) {

        try {

            EduQuestion question = eduQuestionDao.queryQuestionById(questionId);

            List<EduQuestionAnswer> questionOptionList = eduQuestionDao.queryEduQuestionAnswer(questionId);

            Map<String, Object> result = new HashMap<>();
            result.put("question", question);
            result.put("questionOptionList", questionOptionList);

            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryQuestionAndOptionById：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


}
