package com.xgt.controller.sync;


import com.xgt.dao.imgtext.ImgtextDao;
import com.xgt.dao.imgtext.ImgtxtClassifyDao;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.imgtext.EduImgtxtClassify;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;


/**
 * @author HeLiu
 * @Description *********** 远程服务  ***************
 * 该contoller 是 提供给一体机客户端调用的，会部署再服务器上
 * 同步数据时，需要从服务端获取数据，并保存在一体机上
 * @date 2018/7/19 15:45
 */
@Controller
@Path("/imgTextRemoteServer")
public class ImgTextRemoteServerController {

    private static final Logger logger = LoggerFactory.getLogger(ImgTextRemoteServerController.class);

    @Autowired
    private ImgtextDao imgtextDao;

    @Autowired
    private ImgtxtClassifyDao imgtxtClassifyDao;

    /**
     * @Description 根据图文id, 查询id和大于当前该图文id的下一个图文信息
     * @author HeLiu
     * @date 2018/7/19 16:27
     */
    @Path("/queryNextImgTestById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> queryNextImgTestById(@QueryParam("previousId") Integer previousId) {
        try {
            EduImgtext result = imgtextDao.queryNextImgTestById(previousId);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **syncImgText2Local** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 根据图文分类的id查询图文分类信息
     * @author HeLiu
     * @date 2018/7/19 20:54
     */
    @Path("/queryClassifyById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> queryClassifyById(@QueryParam("classifyId") Integer classifyId) {
        try {
            EduImgtxtClassify result = imgtxtClassifyDao.queryClassifyById(classifyId);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryClassifyById** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
