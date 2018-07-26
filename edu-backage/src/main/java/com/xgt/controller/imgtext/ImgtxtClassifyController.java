package com.xgt.controller.imgtext;

import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.imgtext.EduImgtxtClassify;
import com.xgt.service.imgtext.ImgtxtClassifyService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Joanne
 * @Description 图文分类
 * @create 2018-06-05 11:15
 **/
@RequestMapping("/imgtxtClassify")
@Controller
public class ImgtxtClassifyController {

    private final static Logger logger = LoggerFactory.getLogger(ImgtxtClassifyController.class);

    @Autowired
    private ImgtxtClassifyService imgtxtClassifyService ;

    /**
     * @Description pc -查询图文树
     * @author Joanne
     * @create 2018/6/5 14:04
     */
    @RequestMapping(value = "/treelist", produces = "application/json ;charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryTreeList(EduImgtxtClassify eduImgText){
        try{
            List<EduImgtxtClassify> eduImgTxtTreeList = imgtxtClassifyService.queryTreeList();
            return ResultUtil.createSuccessResult(eduImgTxtTreeList);
        }catch (Exception e){
            logger.error("查询图文树失败",e);
            return ResultUtil.createFailResult("查询图文树失败："+e);
        }
    }

    /**
     * @Description 移动端 -查询图文分类树
     * @author Joanne
     * @create 2018/6/9 10:11
     */
    @RequestMapping(value = "/classifyTree", produces = "application/json ;charset=utf-8")
    @ResponseBody
    public Map<String,Object> classifyTree(){
        try{
            return ResultUtil.createSuccessResult(imgtxtClassifyService.queryTreeList());
        }catch (Exception e){
            logger.error("查询图文树失败",e);
            return ResultUtil.createFailResult("查询图文树失败："+e);
        }
    }

    /**
     * @Description pc-添加分类树
     * @author Joanne
     * @create 2018/6/5 17:07
     */
    @RequestMapping(value = "/addClassify", produces = "application/json ;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addClassify(EduImgtxtClassify eduImgTxt){
        try{
            Integer id = imgtxtClassifyService.addClassify(eduImgTxt);
            if(id>0){
                return ResultUtil.createSuccessResult(id);
            }
            return ResultUtil.createFailResult("添加分类失败");
        }catch (Exception e){
            logger.error("添加分类失败",e);
            return ResultUtil.createFailResult("添加分类失败："+e);
        }
    }

    /**
     * @Description pc-修改分类树
     * @author Joanne
     * @create 2018/6/5 17:07
     */
    @RequestMapping(value = "/updateClassify", produces = "application/json ;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateClassify(EduImgtxtClassify eduImgTxt){
        try{
            imgtxtClassifyService.updateClassify(eduImgTxt);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("修改分类失败",e);
            return ResultUtil.createFailResult("修改分类失败："+e);
        }
    }

    /**
     * @Description pc -根据类id删除类
     * @author Joanne
     * @create 2018/6/5 17:34
     */
    @RequestMapping(value = "/deleteClassify", produces = "application/json ;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteClassify(Integer classifyId){
        try{
            imgtxtClassifyService.deleteClassify(classifyId);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("修改分类失败",e);
            return ResultUtil.createFailResult("修改分类失败："+e);
        }
    }

    /**
     * @Description 查询热门分类
     * @author Joanne
     * @create 2018/6/12 14:16
     */
    @RequestMapping(value = "/queryHotClassify", produces = "application/json ;charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryHotClassify(){
        try{
            List<EduImgtext> eduImgtextList = imgtxtClassifyService.queryHotClassify();
            return ResultUtil.createSuccessResult(eduImgtextList);
        }catch (Exception e){
            logger.error("获取热门分类失败：",e);
            return ResultUtil.createFailResult("获取热门分类失败："+e);
        }
    }
}
