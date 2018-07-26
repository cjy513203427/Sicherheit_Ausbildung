package com.xgt.controller;

import com.xgt.entity.Dictionary;
import com.xgt.service.DictionaryService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.TypeCode.PROGRAM_TYPE;
import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 * @author
 * @Description
 * @create 2018-03-30 19:05
 **/

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * @author Joanne
     * @date 2018/4/5 17:32
     * @Description 查询字典列表(或有条件)
     */

    
    @RequestMapping(value = "/list", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> getDictionaryList(Dictionary dictionary) {
        try{
            Map<String, Object> result = dictionaryService.selectDictionaryList(dictionary);
            return ResultUtil.createSuccessResult(result);
        }catch(Exception e){
            logger.error("list..........异常 dictionary=" + dictionary.toString(), e);
            return ResultUtil.createFailResult("list..........异常 dictionary =" + dictionary.toString());
        }
    }

    /**
     * @Description 添加字典
     * @author Joanne
     * @create 2018/4/11 10:17
     */
    @RequestMapping(value = "/create", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addDictionary(Dictionary dictionary) {
        try{
            dictionaryService.addDictionary(dictionary);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("create..........异常 dictionary=" + dictionary.toString(), e);
            return ResultUtil.createFailResult("create..........异常 dictionary =" + dictionary.toString());
        }
    }

    /**
     * @Description 修改字典
     * @author Joanne
     * @create 2018/4/11 11:58
     */
    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> modifyDictionary(Dictionary dictionary) {
        try{
            dictionaryService.modifyDictionary(dictionary);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("update..........异常 dictionary=" + dictionary.toString(), e);
            return ResultUtil.createFailResult("update..........异常 dictionary =" + dictionary.toString());
        }
    }



    /**
     * @Description 获取省份的数据源
     * @author CC
     * @create 2018/5/23 15：32
     */
    @RequestMapping(value = "/provinceList",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> getProvinceList() {
        try {
            List<Dictionary> provinceList = dictionaryService.getProvinceList();
            return ResultUtil.createSuccessResult(provinceList);
        } catch (Exception e) {
            logger.error("........provinceList error", e);
        }
        return ResultUtil.createFailResult(ERROR_SERVER.desc);
    }

    /**
     * @Description 获取城市的数据源
     * @author CC
     * @create 2018/5/23 15：32
     */
    @RequestMapping(value = "/cityList",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> getCityList(String provinceText) {
        try {
            if(provinceText==null||provinceText.length()==0){
                return ResultUtil.createSuccessResult(new ArrayList<Dictionary>());
            }
            List<Dictionary> cityList = dictionaryService.getCityList(provinceText);
            return ResultUtil.createSuccessResult(cityList);
        } catch (Exception e) {
            logger.error("........cityList error", e);
        }
        return ResultUtil.createFailResult(ERROR_SERVER.desc);
    }

    /**
     * @Description 培训系统PC版 - 字典下拉列表
     * @author Joanne
     * @create 2018/6/27 15:43
     */
    @RequestMapping(value = "/queryDic",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryDic(String typeCode) {
        try {
            List<Dictionary> programTypeList = dictionaryService.queryDicInfo(typeCode);
            return ResultUtil.createSuccessResult(programTypeList);
        } catch (Exception e) {
            logger.error("........获取方案类型失败..........", e);
        }
        return ResultUtil.createFailResult(ERROR_SERVER.desc);
    }

}
