package com.xgt.controller.imgtext;

import com.xgt.common.BaseController;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.service.UploadService;
import com.xgt.service.imgtext.ImgtextService;
import com.xgt.util.ConstantsUtil;
import com.xgt.util.DateUtil;
import com.xgt.util.MD5Util;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.LITIMG_FOLDER;
import static com.xgt.util.FileToolUtil.getFileExt;

/**
 * @author Joanne
 * @Description 图文管理
 * @create 2018-06-05 10:22
 **/
@RequestMapping("/imgtext")
@Controller
public class ImgtextController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(ImgtextController.class);
    @Autowired
    private ImgtextService imgTextService;

    @Autowired
    private UploadService uploadService;

    /**
     * @Description pc -图文列表
     * @author Joanne
     * @create 2018/6/6 14:50
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> queryImgtxtList(EduImgtext eduImgText) {
        try {
            Map imgtxtInfo = imgTextService.imgtxtInfo(eduImgText);
            return ResultUtil.createSuccessResult(imgtxtInfo);
        } catch (Exception e) {
            logger.error("查询图文信息失败", e);
            return ResultUtil.createFailResult("查询图文信息失败：" + e);
        }
    }

    /**
     * @Description 添加图文
     * @author Joanne
     * @create 2018/6/11 11:33
     */
    @RequestMapping(value = "/addPicArticle")
    @ResponseBody
    public Map<String, Object> addPicArticle(EduImgtext eduImgText) {
        try {
            eduImgText.setCreateUserId(getLoginUserId());
            imgTextService.addPicArticle(eduImgText);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("添加图文信息失败", e);
            return ResultUtil.createFailResult("添加图文信息失败：" + e);
        }
    }


    /**
     * @Description 培训系统PC版 - 新增知识库
     * @author Joanne
     * @create 2018/7/2 14:02
     */
    @RequestMapping(value = "/newSafeKnowledgeArticle" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> newSafeKnowledgeArticle(EduImgtext eduImgText,MultipartFile article,MultipartFile cover) {
        try {
            eduImgText.setCreateUserId(getLoginUserId());
//            eduImgText.setCreateUserId(1);
            imgTextService.newSafeKnowledgeArticle(eduImgText,article,cover);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("新增知识库失败", e);
            return ResultUtil.createFailResult("新增知识库失败：" + e);
        }
    }

    /**
     * @Description 修改HTML图文
     * @author Joanne
     * @create 2018/6/13 21:52
     */
    @RequestMapping(value = "/updateHTML")
    @ResponseBody
    public Map<String, Object> updatePicArticle(EduImgtext eduImgText ) {
        try {
            imgTextService.updatePicArticle(eduImgText);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("添加图文信息失败", e);
            return ResultUtil.createFailResult("添加图文信息失败：" + e);
        }
    }

    /**
     * @Description 修改PDF图文
     * @author Joanne
     * @create 2018/6/13 22:01
     */
    @RequestMapping(value = "/updatePDF")
    @ResponseBody
    public Map<String, Object> updatePDFPicArticle(EduImgtext eduImgText,MultipartFile file) {
        try {
            imgTextService.updatePDFPicArticle(eduImgText,file,getLoginUserId());
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("添加图文信息失败", e);
            return ResultUtil.createFailResult("添加图文信息失败：" + e);
        }
    }

    /**
     * @Description 添加PDF图文
     * @author Joanne
     * @create 2018/6/12 17:22
     */
    @RequestMapping(value = "/addPdfPicArticle")
    @ResponseBody
    public Map<String, Object> addPdfPicArticle(EduImgtext eduImgText, MultipartFile file) {
        try {
            imgTextService.addPdfPicArticle(eduImgText,file);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("添加图文信息失败", e);
            return ResultUtil.createFailResult("添加图文信息失败：" + e);
        }
    }

    /**
     * @Description 删除图文
     * @author Joanne
     * @create 2018/6/15 19:11
     */
    @RequestMapping(value = "delPicArticle" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> delPicArticle(String ids){
        try {
            imgTextService.delPicArticle(ids);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("删除章节异常......",e);
            return ResultUtil.createFailResult("wrong"+e) ;
        }
    }

    /**
     * @Description 移动端 - 获取图文信息集锦
     * @author Joanne
     * @create 2018/6/9 16:18
     */
    @RequestMapping(value = "/imgTextCollectionInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> imgTextCollectionInfo(EduImgtext eduImgText) {
        try {
            eduImgText.setPostType(getLoginLabourer().getPostType());
//            eduImgText.setPostType("2");
            List<EduImgtext> imgTextCollectionInfo = imgTextService.imgTextCollectionInfo(eduImgText,getCompanyId());
//            List<EduImgtext> imgTextCollectionInfo = imgTextService.imgTextCollectionInfo(eduImgText,2);
            return ResultUtil.createSuccessResult(imgTextCollectionInfo);
        } catch (Exception e) {
            logger.error("查询图文信息失败", e);
            return ResultUtil.createFailResult("查询图文信息失败：" + e);
        }
    }


    /**
     * @Description 上传缩略图
     * @author Joanne
     * @create 2018/6/13 21:31
     */
    @RequestMapping(value = "/uploadLitimg", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> uploadLitimg(Integer picArticleId, MultipartFile file) {
        try {
            imgTextService.uploadLitimg(picArticleId,file);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("查询图文信息失败", e);
            return ResultUtil.createFailResult("查询图文信息失败：" + e);
        }
    }

    /**
     * @Description 获取图文详情
     * @author Joanne
     * @create 2018/6/12 14:55
     */
    @RequestMapping(value = "/imgTextDetial", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> imgTextDetial(Integer imgTextId) {
        try {
            EduImgtext imgTextDetial = imgTextService.imgTextDetial(imgTextId,getLabourerUserId());
            //标记浏览次数
            imgTextService.markLearnTimes(imgTextId);
            return ResultUtil.createSuccessResult(imgTextDetial);
        } catch (Exception e) {
            logger.error("查询图文信息失败", e);
            return ResultUtil.createFailResult("查询图文信息失败：" + e);
        }
    }

    /**
     * @Description 上传ue图片
     * @author Joanne
     * @create 2018/6/6 14:50
     */
    @RequestMapping(value = "/ueUploadPic")
    @ResponseBody
    public Map<String, Object> ueUploadPic(@RequestParam("upfile") MultipartFile uploadFile) {
        Map<String, Object> reuslt = new HashMap<>();
        try {
            String year = DateUtil.getStringDate("yyyy");
            String month = DateUtil.getStringDate("MM");
            String extName = getFileExt(uploadFile.getOriginalFilename());
            String fileName = MD5Util.MD5(uploadFile.getOriginalFilename()+System.currentTimeMillis())+extName;
            String filePath = ConstantsUtil.Ueditor + ConstantsUtil.FILE_SEPARATOR + year + ConstantsUtil.FILE_SEPARATOR +
                    month + ConstantsUtil.FILE_SEPARATOR + fileName;
            uploadService.uploadIsToOSS(uploadFile.getInputStream(),filePath);

            reuslt.put("original", fileName);
            reuslt.put("url", filePath);
            reuslt.put("title", fileName);
            reuslt.put("state", "SUCCESS");
            reuslt.put("path",filePath);

            return reuslt;
        } catch (Exception e) {
            logger.error("UE上传失败", e);
            reuslt.put("state", "上传失败了啊");
        }
        return reuslt;
    }

}
