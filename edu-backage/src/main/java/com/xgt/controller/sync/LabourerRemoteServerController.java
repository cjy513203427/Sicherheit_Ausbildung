package com.xgt.controller.sync;

import com.alibaba.fastjson.JSON;
import com.xgt.entity.BuildLabourer;
import com.xgt.entity.BuildSite;
import com.xgt.service.BuildLabourerService;
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
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 * @program: safety_edu
 * @description:
 * @author: Joanne
 * @create: 2018-07-20 10:21
 **/
@Controller
@Path("/labourRemoteServer")
public class LabourerRemoteServerController {

    private static final Logger logger = LoggerFactory.getLogger(LabourerRemoteServerController.class);

    @Autowired
    private BuildLabourerService buildLabourerService;

    /**
     *   根据  视频章节内容Id ，查询id 大于当前该 视频章节内容Id 的下一个视频章节内容信息
     * @author liuao
     * @date 2018/7/18 10:08
     */
    @Path("/syncBuildLabourInfo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> syncBuildLabourInfo(@FormParam("localBuildSite") String localBuildSiteString,
                                                  @FormParam("buildLabourer") String buildLabourerString) {
        try {
            BuildSite localBuildSite = JSON.parseObject(localBuildSiteString,BuildSite.class);
            BuildLabourer localBuildLabourer = JSON.parseObject(buildLabourerString,BuildLabourer.class);
            buildLabourerService.syncBuildLabourInfo(localBuildSite,localBuildLabourer);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("......同步人员信息失败：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
