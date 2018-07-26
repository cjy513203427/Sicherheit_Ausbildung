package com.xgt.controller.sync;

import com.xgt.enums.EnumSyncType;
import com.xgt.service.sync.SyncImgTextService;
import com.xgt.service.sync.SyncLabourService;
import com.xgt.service.sync.SyncVideoQuestionService;
import com.xgt.service.sync.SyncVideoService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 *  一体机客户端同步远程数据controller 层
 * @author liuao
 * @date 2018/7/18 10:07
 */
@Controller
@RequestMapping("/syncRemoteClient")
public class SyncRemoteClientController {
    private static final Logger logger = LoggerFactory.getLogger(SyncRemoteClientController.class);
    @Autowired
    private SyncVideoService syncVideoService;

    @Autowired
    private SyncImgTextService syncImgTextService;


    @Autowired
    private SyncLabourService syncLabourService ;

    @Autowired
    private SyncVideoQuestionService syncVideoQuestionService;



    /**
     * 同步服务端的 视频数据到本地
     *
     * @author liuao
     * @date 2018/7/18 10:08
     */
    @RequestMapping("/syncVideo2Local")

    public Map<String, Object> syncVideo2Local() {

        try {
            // 同步视频
            syncVideoService.excuteSync(EnumSyncType.VIDEO.code);

            // 同步视频题目
            syncVideoQuestionService.excuteSync(EnumSyncType.VIDEO_QUESTION.code);

            // 同步学员信息
            syncLabourService.excuteSync(EnumSyncType.Labour.code);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————syncVideo2Local：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 同步服务端的图文数据到本地
     * @author HeLiu
     * @date 2018/7/19 14:54
     */
    public Map<String, Object> syncImgText2Local() {
        try {
            //同步图文
            syncImgTextService.excuteSync(EnumSyncType.IMGTXT.code);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("Mistakes are happening for **syncImgText2Local** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
