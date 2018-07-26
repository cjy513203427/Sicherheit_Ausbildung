package com.xgt.controller;

import com.aliyuncs.exceptions.ClientException;
import com.xgt.common.BaseController;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.BuildAttachment;
import com.xgt.entity.BuildLabourer;
import com.xgt.entity.User;
import com.xgt.service.BuildLabourerService;
import com.xgt.util.EncryptUtil;
import com.xgt.util.ResultUtil;
import com.xgt.util.SendMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.xgt.constant.SystemConstant.DEFAULT_PASSWORD;
import static com.xgt.constant.SystemConstant.REDIS_KEY_PREFIX;
import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;
import static com.xgt.util.EncryptUtil.HASHITERATIONS;


/**
 * Created by 5618 on 2018/6/1.
 */
@Controller
@RequestMapping("/buildLabourer")
public class BuildLabourerController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BuildLabourerController.class);

    @Autowired
    private BuildLabourerService buildLabourerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 建筑工地人员信息查询接口
     *
     * @param buildLabourer
     * @return
     */
    @RequestMapping(value = "/queryBuildLabourer", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryBuildLabourer(BuildLabourer buildLabourer) {
        Map<String, Object> result = null;
        try {
            User user = getUser();
            result = buildLabourerService.queryBuildLabourer(buildLabourer, user);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryBuildLabourer方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统 - pc端
     *
     * 新增培训计划（手动选择受训人员）查询公司及下属人员
     *
     * @author Joanne
     * @create 2018/7/13 9:31
     */
    @RequestMapping(value = "/queryLabourerAndSite", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryLabourerAndSite(Integer programId) {
        Map<String, Object> result = null;
        try {
            result = buildLabourerService.queryLabourerAndSite(getUser(),programId);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("查询手动选择受训人员信息错误：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     * @Description 培训系统 - pc端
     *
     * 新增培训计划（手动选择受训人员）下属人员
     *
     * @author Joanne
     * @create 2018/7/13 9:31
     */
    @RequestMapping(value = "/queryLabourerForSelect", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryLabourerForSelect(BuildLabourer buildLabourer,Integer programId) {
        List<BuildLabourer> buildLabourers = new ArrayList<>();
        try {
            buildLabourers = buildLabourerService.queryLabourerForSelect(buildLabourer,programId);
            return ResultUtil.createSuccessResult(buildLabourers);
        } catch (Exception e) {
            logger.error("查询手动选择受训人员信息错误：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 新增建筑工地人员信息接口
     *
     * @param buildLabourer
     * @return
     */
    @RequestMapping(value = "/createBuildLabourer", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> createBuildLabourer(BuildLabourer buildLabourer) {
        try {
            buildLabourer.setCreateUserId(getLoginUserId());
            String passwordMd5 = EncryptUtil.md5(DEFAULT_PASSWORD, buildLabourer.getPhone(), HASHITERATIONS);
            buildLabourer.setPassword(passwordMd5);
            buildLabourerService.createBuildLabourer(buildLabourer);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————createBuildLabourer方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 修改建筑工地人员信息接口
     *
     * @param buildLabourer
     * @return
     */
    @RequestMapping(value = "/updateBuildLabourer", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateBuildLabourer(BuildLabourer buildLabourer) {
        try {
            buildLabourerService.updateBuildLabourer(buildLabourer);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————updateBuildLabourer方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     * 人员启用和禁用接口
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/enOrDisBuildLabourer", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> enOrDisBuildLabourer(Integer id, String status, Integer accountNumber, String code) {
        try {
            if (SystemConstant.State.DISABLE_STATE.equals(status)) {
                Integer hasedNumber = buildLabourerService.getHasedNumber(code, SystemConstant.State.ACTIVE_STATE);
                if (hasedNumber >= accountNumber) {
                    return ResultUtil.createMyselfResult("588", "开通账户数量已达上限！");
                }
            }
            buildLabourerService.enOrDisBuildLabourer(id, status);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————enOrDisBuildLabourer方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 发送手机验证码接口
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendCode", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> senCode(String phone) throws ClientException {
        if (StringUtils.isEmpty(phone)) {
            return ResultUtil.createFailResult("手机号不能为空！");
        }
        Random ne = new Random();
        String code = String.valueOf(ne.nextInt(9999 - 1000 + 1) + 1000);
        stringRedisTemplate.opsForValue().set(REDIS_KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
        SendMessageUtil.sendNewMessage(phone, "{\"code\":\"" + code + "\"}", "SMS_131015105");
        return ResultUtil.createSuccessResult();
    }

    /**
     * 修改密码接口
     *
     * @return
     */
    @RequestMapping(value = "/updateLabourerPsw", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateLabourerPsw(String phone, String code, String newPassword, String truePassword) {
        String realcode = stringRedisTemplate.opsForValue().get(REDIS_KEY_PREFIX + phone);
        if (StringUtils.isNotEmpty(realcode)) {
            if (code.equals(realcode)) {
                if (newPassword.equals(truePassword)) {
                    BuildLabourer buildLabourer = new BuildLabourer();
                    buildLabourer.setId(getLabourerUserId());
                    //密码加密
                    String passwordMd5 = EncryptUtil.md5(truePassword, phone, HASHITERATIONS);
                    buildLabourer.setPassword(passwordMd5);
                    buildLabourerService.updateLabourerPsw(buildLabourer);
                    return ResultUtil.createSuccessResult("修改成功！");
                } else {
                    return ResultUtil.createFailResult("两次密码不相同！");
                }
            } else {
                return ResultUtil.createFailResult("验证码输入错误！");
            }
        } else {
            return ResultUtil.createFailResult("验证码失效！");
        }
    }

    /**
     * 手机端个人资料查询
     *
     * @return
     */
    @RequestMapping(value = "/queryBuildLabourerOne", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryBuildLabourerOne(BuildLabourer buildLabourer) {
        try {
            buildLabourer.setId(getLabourerUserId());
            BuildLabourer result = buildLabourerService.queryLabourerOne(buildLabourer);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryBuildLabourerOne方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 获取个人中心信息
     *
     * @param buildLabourer
     * @return
     */

    @RequestMapping(value = "/getPersonalCenter", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> getPersonalCenter(BuildLabourer buildLabourer) {
        try {
            List<BuildLabourer> result = buildLabourerService.getPersonalCenter(getLabourerUserId());
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————getPersonalCenter：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 查询学员
     * 参数buildCompanyId,buildSiteCode,idCard,startTime,endTime
     * @param buildLabourer
     * @return
     */
    @RequestMapping(value = "/queryTrainee")
    @ResponseBody
    public Map<String,Object> queryTrainee(BuildLabourer buildLabourer)
    {
        try {
            Map<String,Object> result = buildLabourerService.queryTrainee(buildLabourer);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryTrainee：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 批量转移，从子公司转移到另外一个子公司
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchTransferBuildLabour")
    @ResponseBody
    public Map<String,Object> batchTransferBuildLabour(@RequestParam("ids[]") Integer[] ids, String buildSiteCode)
    {
        try {
            Integer keyId = buildLabourerService.batchTransferBuildLabour(ids,buildSiteCode);
            return ResultUtil.createSuccessResult(keyId);
        } catch (Exception e) {
            logger.error("——————batchTransferBuildLabour：id为"+ids, e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 展示工人二维码
     * @param buildCompanyId
     * @param realnames
     * @return
     */
    @RequestMapping(value = "/showBuildLaoburerByQRCode")
    @ResponseBody
    public Map<String,Object> showBuildLaoburerByQRCode(Integer buildCompanyId,@RequestParam("realnames[]") String[] realnames)
    {
        try {
            List<Map> mapList = buildLabourerService.showBuildLaoburerByQRCode(buildCompanyId,realnames);
            return ResultUtil.createSuccessResult(mapList);
        } catch (Exception e) {
            logger.error("——————printBuildLaoburerByQRCode：realname"+realnames, e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 打印二维码
     * @return
     */
    @RequestMapping(value = "/printQRCode")
    @ResponseBody
    public Map<String,Object> printQRCode(){
        try {
            buildLabourerService.printQRCode();
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("printQRCode错误.........",e);
            return ResultUtil.createFailResult("printQRCode错误......");
        }
    }

    /**
     * @Description 批量打印人员信息二维码
     * @author Joanne
     * @create 2018/7/6 11:29
     */
    @RequestMapping(value = "/batchPrintQRInfo")
    @ResponseBody
    public Map<String,Object> batchPrintQRInfo(String ids){
        try {
            buildLabourerService.batchPrintQRInfo(ids);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("print qr info wrong.........",e);
            return ResultUtil.createFailResult("print qr info wrong......");
        }
    }

    /**
     * 删除工人，禁用
     * 离场
     * @param ids
     * @return
     */
    @RequestMapping(value = "/disableBuildLabourer")
    @ResponseBody
    public Map<String,Object> disableBuildLabourer(@RequestParam("ids[]") Integer[] ids){
        try {
            Integer effectedNum = buildLabourerService.disableBuildLabourer(ids);
            return ResultUtil.createSuccessResult(effectedNum);
        } catch (Exception e) {
            logger.error("disableBuildLabourer,id是"+ids,e);
            return ResultUtil.createFailResult("disableBuildLabourer");
        }
    }

    /**
     * 批量导入工人
     * @param jsonArr
     * @param buildSiteCode
     * @return
     */
    @RequestMapping(value = "/batchImportBuildLabourer")
    @ResponseBody
    public Map<String,Object> batchImportBuildLabourer(String jsonArr, String buildSiteCode, Integer companyId){
        try {
            buildLabourerService.batchImportBuildLabourer(jsonArr,buildSiteCode,companyId);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("batchImportBuildLabourer",e);
            return ResultUtil.createFailResult("batchImportBuildLabourer");
        }
    }

    /**
     * 新增学员基本信息
     * @param buildLabourer
     * @param buildAttachment
     * @return
     */
    @RequestMapping(value = "/addSingleBuildLabourer")
    @ResponseBody
    public Map<String,Object> addSingleBuildLabourer(String jsonArr, @RequestParam("medicalReports[]") String[] medicalReports,
                                                     @RequestParam("laborContracts[]") String[] laborContracts,
                                                     @RequestParam("liabilityForms[]") String[] liabilityForms,@RequestParam("idCardScans[]") String[] idCardScans,
                                                     BuildLabourer buildLabourer,BuildAttachment buildAttachment){
        try {
            buildLabourerService.addSingleBuildLabourer(jsonArr,medicalReports,laborContracts,liabilityForms,idCardScans
                    ,buildLabourer,buildAttachment);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("addSingleBuildLabourer,BuildLabourer是"+buildLabourer,e);
            return ResultUtil.createFailResult("addSingleBuildLabourer");
        }
    }

    /**
     * 修改学员信息
     * @param buildLabourer
     * @param buildAttachment
     * @return
     */
    @RequestMapping(value = "/updateSingleBuildLabourer")
    @ResponseBody
    public Map<String,Object> updateSingleBuildLabourer(String jsonArr, @RequestParam("medicalReports[]") String[] medicalReports,
                                                        @RequestParam("laborContracts[]") String[] laborContracts,
                                                        @RequestParam("liabilityForms[]") String[] liabilityForms,@RequestParam("idCardScans[]") String[] idCardScans,
                                                        BuildLabourer buildLabourer,BuildAttachment buildAttachment){
        try {
            buildLabourerService.updateSingleBuildLabourer( jsonArr, medicalReports,  laborContracts,
              liabilityForms,idCardScans,buildLabourer,  buildAttachment);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("updateSingleBuildLabourer,BuildLabourer是"+buildLabourer,e);
            return ResultUtil.createFailResult("updateSingleBuildLabourer");
        }
    }

    /**
     * 根据id查询工人信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryBuildLabourerById")
    @ResponseBody
    public Map<String,Object> queryBuildLabourerById(Integer id){
            try {
            Map map = buildLabourerService.queryBuildLabourerById(id);
            return ResultUtil.createSuccessResult(map);
        } catch (Exception e) {
            logger.error("queryBuildLabourerById,BuildLabourer是"+id,e);
            return ResultUtil.createFailResult("queryBuildLabourerById");
        }
    }

}
