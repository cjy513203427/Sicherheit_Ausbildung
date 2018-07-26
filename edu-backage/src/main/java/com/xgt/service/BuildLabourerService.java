package com.xgt.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.BuildAttachmentDao;
import com.xgt.dao.BuildLabourerDao;
import com.xgt.dao.BuildSiteDao;
import com.xgt.dao.DictionaryDao;
import com.xgt.dto.BuildLabourerDto;
import com.xgt.dto.ImgQrcodeDto;
import com.xgt.entity.*;
import com.xgt.entity.exam.ExamLabourerRel;
import com.xgt.enums.EnumAttachmentType;
import com.xgt.service.fingerprint.FingerPrintService;
import com.xgt.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static com.xgt.constant.SystemConstant.*;
import static com.xgt.constant.SystemConstant.TypeCode.POST_TYPE;
import static com.xgt.service.fingerprint.FingerPrintService.TEMPPIC_PATH;
import static com.xgt.util.MD5Util.MD5;

/**
 * Created by 5618 on 2018/6/1.
 */
@Service
public class BuildLabourerService {

    @Value("${aliyunOSS.accessKeyId}")
    protected String accessKeyId;

    @Value("${aliyunOSS.accessKeySecret}")
    protected String accessKeySecret;

    @Value("${aliyunOSS.uploadUrl}")
    protected String endpoint;

    @Value("${labourDetailUrl}")
    private String labourDetailUrl;

    @Value("${aliyunOSS.bucketname}")
    protected String bucketName;

    private static final Logger logger = LoggerFactory.getLogger(BuildLabourerService.class);

    String fileDirectory = "D:\\QRCodePng";
    @Autowired
    TransactionTemplate transactionTemplate ;

    @Autowired
    private BuildLabourerDao buildLabourerDao;

    @Autowired
    private BuildAttachmentDao buildAttachmentDao;

    @Autowired
    private FingerPrintService fingerPrintService ;

    @Autowired
    private BuildSiteDao buildSiteDao ;

    /**
     * 建筑工地人员信息查询
     * （根据权限查询）
     *
     * @param buildLabourer
     * @return
     */
    public Map<String, Object> queryBuildLabourer(BuildLabourer buildLabourer, User user) {
        //获取角色列表
        List<Role> roleList = user.getRoleList();
        for (Role role : roleList) {
            String roleName = role.getName();
            if (SystemConstant.ADMINISTRATOR.equals(role.getName())) {
                return getBuildLabourer(buildLabourer);
            } else if (SystemConstant.BUILDHEAD.equals(roleName)) {
                buildLabourer.setRoleCode(user.getSiteCode());
                return getBuildLabourer(buildLabourer);
            }
        }
        return null;
    }

    /**
     * 建筑工地人员信息列表查询（分页）
     *
     * @param buildLabourer
     * @return
     */
    private Map<String, Object> getBuildLabourer(BuildLabourer buildLabourer) {
        Integer total = buildLabourerDao.queryBuildLabourerCount(buildLabourer);
        List<BuildLabourer> list = null;
        if (total != null && total > 0) {
            list = buildLabourerDao.queryBuildLabourer(buildLabourer);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, BuildLabourer.class));
        map.put("total", total);
        return map;
    }

    /**
     * 新增建筑工地人员信息
     *
     * @param buildLabourer
     */
    public void createBuildLabourer(BuildLabourer buildLabourer) {
        buildLabourerDao.createBuildLabourer(buildLabourer);
    }

    /**
     * 人员启用和禁用
     *
     * @param id
     * @param status
     */
    public void enOrDisBuildLabourer(Integer id, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (SystemConstant.State.ACTIVE_STATE.equals(status)) {
            map.put("status", SystemConstant.State.DISABLE_STATE);
        } else {
            map.put("status", SystemConstant.State.ACTIVE_STATE);
        }
        buildLabourerDao.enOrDisBuildLabourer(map);
    }

    /**
     * 修改建筑工地人员信息
     *
     * @param buildLabourer
     */
    public void updateBuildLabourer(BuildLabourer buildLabourer) {
        buildLabourerDao.updateBuildLabourer(buildLabourer);
    }

    /**
     * 修改密码
     *
     * @param buildLabourer
     */
    public void updateLabourerPsw(BuildLabourer buildLabourer) {
        buildLabourerDao.updateLabourerPsw(buildLabourer);
    }

    /**
     * 手机端个人资料查询
     *
     * @param buildLabourer
     * @return
     */
    public BuildLabourer queryLabourerOne(BuildLabourer buildLabourer) {
        return buildLabourerDao.queryLabourerOne(buildLabourer);
    }

    /**
     * 已经开通账号数量
     *
     * @param code
     */
    public Integer getHasedNumber(String code, String status) {
        return buildLabourerDao.getHasedNumber(code, status);
    }

    /**
     * 获取个人中心信息
     *
     * @return
     */
    public List<BuildLabourer> getPersonalCenter(Integer labourerId) {
        return buildLabourerDao.getPersonalCenter(labourerId);
    }

    /**
     * 查询学员
     * 参数buildCompanyId,buildSiteCode,idCard,startTime,endTime
     * @param buildLabourer
     * @return
     */
    public Map<String,Object> queryTrainee(BuildLabourer buildLabourer){
        Integer total = buildLabourerDao.countQueryTrainee(buildLabourer);
        List<BuildLabourer> buildLabourerList = null;
        if(total>0){
            buildLabourerList = buildLabourerDao.queryTrainee(buildLabourer);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("buildLabourerList",buildLabourerList);
        return map;
    }

    /**
     * 通用附件上传工具
     * @param buildAttachment
     * @throws FileNotFoundException
     */
    public void uploadBuildSiteAttachment(BuildAttachment buildAttachment) throws FileNotFoundException {
        if(buildAttachment.getAttachmentByte().length>0){
            // 上传到图片服务器
            OssUtil oss=new OssUtil(accessKeyId, accessKeySecret, endpoint,bucketName);
            //图片
            String ATTACHFileName = buildAttachment.getAttachmentName().substring(0,
                    buildAttachment.getAttachmentName().lastIndexOf(".")).toLowerCase();
            String ATTACHSuffixName = buildAttachment.getAttachmentName().substring(
                    buildAttachment.getAttachmentName().lastIndexOf("."), buildAttachment.getAttachmentName().length())
                    .toLowerCase();
            oss.putObject(ConstantsUtil.FOLDER_ATTACHMENT_BUILDSITE+ConstantsUtil.FILE_SEPARATOR
                    +ATTACHFileName+ATTACHSuffixName, buildAttachment.getAttachmentByte());
            buildAttachment.setAttachmentPath(ConstantsUtil.FOLDER_ATTACHMENT_BUILDSITE+ConstantsUtil.FILE_SEPARATOR
                    +ATTACHFileName+ATTACHSuffixName);
            buildAttachment.setAttachmentName(ATTACHFileName);
            buildAttachmentDao.addBuildLabourerAttachment(buildAttachment);
        }
    }

    /**
     * 批量转移，从子公司转移到另外一个子公司
     * @param ids
     * @return
     */
    public Integer batchTransferBuildLabour(Integer[] ids,String buildSiteCode)
    {
        return buildLabourerDao.batchTransferBuildLabour(ids,buildSiteCode);
    }

    /**
     * 生成多个、单个Base64的二维码
     * @param buildCompanyId
     * @param realnames
     * @return
     */
    public List<Map> showBuildLaoburerByQRCode(Integer buildCompanyId,String[] realnames){
        List<Map> mapList = new ArrayList<>();
        String Base64QRCode;
        String fileName = null;
        try {
            for (String realname:realnames) {
                Map<String,Object> map = new HashMap<>();
                fileName = String.valueOf(realname);
                String url = "http://127.0.0.1/rest/buildLabourer/queryTrainee?buildCompanyId=" + buildCompanyId +"&realname=" +realname;
                Base64QRCode = QRCodeUtil.createQRCode(url, fileDirectory, fileName);
                map.put("realname",realname);
                map.put("Base64QRCode",Base64QRCode);
                mapList.add(map);
            }
            ImgUtil.ImageTset(fileDirectory,realnames);

        } catch (IOException e) {
            logger.error("IO异常"+fileName+e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }


    /**
     * 打印二维码
     */
    public void printQRCode(){
        PrintUtil.drawImage(fileDirectory + "\\合成图.png", 1);
        DeleteFileUtil.delete(fileDirectory);
    }
    /**
     * @Description 批量打印二维码
     * @author Joanne
     * @create 2018/7/6 11:36
     */
    public void batchPrintQRInfo(String ids) throws Exception {

        List<String> idList = Arrays.asList(ids.split(COMMA));
        List<ImgQrcodeDto> imgList = new ArrayList<>();
        //取整
        int objNUmber = idList.size()/6;
        //取余
        int remainder = idList.size()%6;
        int counter = 0 ;
        for(int i = 0;i<(remainder>0?objNUmber+1:objNUmber);i++){
            ImgQrcodeDto imgQrcodeDto = new ImgQrcodeDto();
            Map imgMap = new HashMap() ;
            for(int j=1 ; j<=6 ;j++){
                BuildLabourer buildLabourer = null;
                if(counter>=idList.size()){
                    break ;
                }
                buildLabourer = buildLabourerDao.queryLabourerById(Integer.parseInt(idList.get(counter)));
                //获取base64加密的二维码内容
                String targetInput = FontImageUtil.printQrInfo(buildLabourer,idList.get(counter), labourDetailUrl);
                imgMap.put("img"+j,targetInput);
                counter++;
            }
            imgQrcodeDto = (ImgQrcodeDto) JSONUtil.jsonToObject(JSONUtils.toJSONString(imgMap),ImgQrcodeDto.class);
            imgList.add(imgQrcodeDto);
        }
        String qrCodeWordPath = String.format(QR_CODE_WORD_PATH, DateUtil.getStringDate(DateUtil.YYYYMMDDHHMMSSS));
        FontImageUtil.wordPicPosition(imgList, qrCodeWordPath);
        // 打印机打印word
        logger.info("打印二维码word 文件开始..............");
//        printByWord(qrCodeWordPath);
        logger.info("打印二维码word 文件结束..............");
    }

    /**
     * 删除工人，禁用
     * 离场
     * @param ids
     * @return
     */
    public Integer disableBuildLabourer(Integer[] ids) {
        return buildLabourerDao.disableBuildLabourer(ids);
    }

    /**
     * 批量导入工人
     */
    public void batchImportBuildLabourer(String jsonArr,String buildSiteCode,Integer companyId){
        List<BuildLabourer> buildLabourerList = JSONArray.parseArray(jsonArr, BuildLabourer.class);
        if(CollectionUtils.isEmpty(buildLabourerList)){
            return ;
        }
        for (BuildLabourer buildLabourer : buildLabourerList) {
            // 设置工地编号
            buildLabourer.setBuildSiteCode(buildSiteCode);
            // 设置公司id
            buildLabourer.setBuildCompanyId(companyId);
        }
        buildLabourerDao.batchImportBuildLabourer(buildLabourerList);
    }

    /**
     *  根据指纹匹配人员信息
     * @author liuao
     * @date 2018/7/12 10:43
     */
    public BuildLabourer fingerMatchLabourMsg() throws IOException {

        // 开启 指纹识别仪， 并获取指纹的路径
        String fingerPicPath = fingerPrintService.collectionFingerPicPath();

        if(StringUtils.isNotBlank(fingerPicPath)){
            Iterator<Map.Entry<String, BuildLabourer>> it =
                    BuildLabourerDao.labourerMap.entrySet().iterator();
            while (it.hasNext()) {
                logger.info(".........开始遍历.内存中的指纹......");
                Map.Entry<String, BuildLabourer> entry = it.next();
                BuildLabourer labourer = entry.getValue();
                // 人员指纹信息，数据库会保存3份指纹
                String finger1Base64 = labourer.getFinger1Base64();
                String finger2Base64 = labourer.getFinger2Base64();
                String finger3Base64 = labourer.getFinger3Base64();
                String [] fingerBase64Arr = new String[]{finger1Base64, finger2Base64, finger3Base64};
                for (String fingerBase64 : fingerBase64Arr) {
                    if(StringUtils.isBlank(fingerBase64)){
                        logger.info(".....本次指纹为空：工人id:{}......", entry.getKey());
                        continue;
                    }
                    // base64 转成 图片，返回图片路径
                    String tempFingerPicPath = String.format(TEMPPIC_PATH, DateUtil.getDateTime());
                    tempFingerPicPath = Base64Util.base64ToFile(fingerBase64, tempFingerPicPath);

                    //  比对指纹,获取相似度分数
                    int score = fingerPrintService.getMatchScore(fingerPicPath, tempFingerPicPath);
                    logger.info(".....工人id:{}...本次匹配分数score：{}.....", entry.getKey(), score);
                    // 删除base64 生成的临时文件
                    FileToolUtil.delFile(tempFingerPicPath);

                    if(score > FINGER_MATCH_STANDARD_SORCE){
                        // 比对成功后， 删除采集的临时文件
                        FileToolUtil.delFile(fingerPicPath);
                        // 返回的数据把指纹信息去除掉
                        labourer.setFinger1Base64(null);
                        labourer.setFinger2Base64(null);
                        labourer.setFinger3Base64(null);
                        fingerPrintService.freeSensor();
                        return labourer;
                    }
                }
            }
            // 比对成功后， 删除采集的临时文件
            FileToolUtil.delFile(fingerPicPath);
        }

        return null ;
    }

    /**
     * @Description 查询人员及公司（手动选择受训人员）
     * @author Joanne
     * @create 2018/7/13 9:38
     */
    public Map<String,Object> queryLabourerAndSite(User user,Integer programId) {
        BuildLabourer buildLabourer = new BuildLabourer();
        //查询集团下属公司
        List<BuildSite> buildSites = buildLabourerDao.queryBuildSiteByCompanyId(user.getCompanyId());
        buildLabourer.setBuildSiteCode(buildSites.get(0).getCode());
        //查询集团第一个下属分公司所属员工
        List<BuildLabourer> buildLabourers = buildLabourerDao.queryLabourerForSelect(buildLabourer);
        //根据方案id查询参与考试人员
        List<ExamLabourerRel> examLabourerRels = buildLabourerDao.queryExamLabourRel(programId);
        buildLabourers = selectLabourWithoutExam(buildLabourers,examLabourerRels);
        Map<String,Object> map = new HashMap<>();
        map.put("buildSites",buildSites);
        map.put("buildLabourers",buildLabourers);
        map.put("companyId",user.getCompanyId());
        map.put("companyName",user.getCompanyName());
        return map ;
    }

    /**
     * @Description 查询以供选择的公司员工（分公司）
     * @author Joanne
     * @create 2018/7/13 16:47
     */
    public List<BuildLabourer> queryLabourerForSelect(BuildLabourer buildLabourer,Integer programId) {
        List<BuildLabourer> buildLabourers = buildLabourerDao.queryLabourerForSelect(buildLabourer);
        //根据方案id查询参与考试人员
        List<ExamLabourerRel> examLabourerRels = buildLabourerDao.queryExamLabourRel(programId);
        return selectLabourWithoutExam(buildLabourers,examLabourerRels);
    }

    /**
     * @Description 筛选方案未参加考试的人员
     * @author Joanne
     * @create 2018/7/17 16:08
     */
    public List<BuildLabourer> selectLabourWithoutExam(List<BuildLabourer> buildLabourers,
                                                       List<ExamLabourerRel> examLabourerRels){
        //如果人员已经参加考试，则移除
        for (BuildLabourer b:buildLabourers){
            for (ExamLabourerRel e:examLabourerRels){
                if(b.getId()== e.getLabourerId()){
                    buildLabourers.remove(b);
                    break;
                }
            }
            b.setPostTypeTxt(DictionaryDao.getDictionaryText(POST_TYPE + b.getPostType()));
        }
        return buildLabourers ;
    }

    /**
     * 新增学员基本信息
     * @param buildLabourer
     * 添加工人附件，新增学员其他信息
     * 体检报告
     * @param medicalReports
     * 劳务合同
     * @param laborContracts
     * 安全责任状
     * @param liabilityForms
     * 身份证扫描件
     * @param idCardScans
     * 工人
     * @param buildLabourer
     * 附件
     * @param buildAttachment
     */
    public void addSingleBuildLabourer(String jsonArr,String[] medicalReports, String[] laborContracts,
                                       String[] liabilityForms, String[] idCardScans,
                                       BuildLabourer buildLabourer, BuildAttachment buildAttachment) {
        Integer id = buildLabourerDao.addSingleBuildLabourer(buildLabourer);
        buildAttachment.setForeignId(id);
        List<BuildAttachment> BuildAttachmentList = JSONArray.parseArray(jsonArr, BuildAttachment.class);
        for (BuildAttachment buildAttachment1:BuildAttachmentList) {
            for(String attachmentPath:buildAttachment1.getAttachmentPaths()) {
                buildAttachment.setAttachmentName(buildAttachment1.getAttachmentName());
                buildAttachment.setAttachmentPath(attachmentPath);
                buildAttachment.setAttachmentType(EnumAttachmentType.CERTIFICATE.code);
                buildAttachment.setAttachmentCode(buildAttachment1.getAttachmentCode());
                buildAttachment.setForeignId(buildAttachment1.getForeignId());
                buildAttachmentDao.addBuildLabourerAttachment(buildAttachment);
            }
        }
        if(medicalReports.length != 0) {
            autoFor(medicalReports, EnumAttachmentType.MEDICAL_REPORT.code, buildAttachment);
        }else if(laborContracts.length != 0) {
            autoFor(laborContracts, EnumAttachmentType.LABOUR_CONTRACT.code, buildAttachment);
        }else if(liabilityForms.length != 0) {
            autoFor(liabilityForms, EnumAttachmentType.LIABILITY_FORMS.code, buildAttachment);
        }else if(idCardScans.length != 0) {
            autoFor(idCardScans, EnumAttachmentType.IDCARD_SCAN.code, buildAttachment);
        }

    }

    /**
     * 修改学员信息
     * 添加工人附件，新增学员其他信息
     * 体检报告
     * @param medicalReports
     * 劳务合同
     * @param laborContracts
     * 安全责任状
     * @param liabilityForms
     * 身份证扫描件
     * @param idCardScans
     * 工人
     * @param buildLabourer
     * 附件
     * @param buildAttachment
     */
    public void updateSingleBuildLabourer(String jsonArr,String[] medicalReports, String[] laborContracts,
                                          String[] liabilityForms, String[] idCardScans,
                                          BuildLabourer buildLabourer, BuildAttachment buildAttachment){
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                buildLabourerDao.updateSingleBuildLabourer(buildLabourer);
                List<BuildAttachment> BuildAttachmentList = JSONArray.parseArray(jsonArr, BuildAttachment.class);
                for (BuildAttachment buildAttachment1:BuildAttachmentList) {
                    for(String attachmentPath:buildAttachment1.getAttachmentPaths()) {
                        buildAttachment.setAttachmentName(buildAttachment1.getAttachmentName());
                        buildAttachment.setAttachmentPath(attachmentPath);
                        buildAttachment.setAttachmentType(EnumAttachmentType.CERTIFICATE.code);
                        buildAttachment.setAttachmentCode(buildAttachment1.getAttachmentCode());
                        buildAttachment.setForeignId(buildAttachment1.getForeignId());
                        buildAttachmentDao.addBuildLabourerAttachment(buildAttachment);
                    }
                }
                if(medicalReports.length != 0) {
                    autoFor(medicalReports, EnumAttachmentType.MEDICAL_REPORT.code, buildAttachment);
                }else if(laborContracts.length != 0) {
                    autoFor(laborContracts, EnumAttachmentType.LABOUR_CONTRACT.code, buildAttachment);
                }else if(liabilityForms.length != 0) {
                    autoFor(liabilityForms, EnumAttachmentType.LIABILITY_FORMS.code, buildAttachment);
                }else if(idCardScans.length != 0) {
                    autoFor(idCardScans, EnumAttachmentType.IDCARD_SCAN.code, buildAttachment);
                }
                return true;
            }
        });
    }

    /**
     * 添加修改学员循环工具
     * @param attachmentBase64s
     * @param attachmentTypes
     * @param buildAttachment
     */
    public void autoFor(String[] attachmentBase64s,String attachmentTypes,BuildAttachment buildAttachment){
        for (int i = 0; i < attachmentBase64s.length;i++) {
            String attachmentBase64 = attachmentBase64s[i];
            buildAttachment.setAttachmentPath(attachmentBase64);
            buildAttachment.setAttachmentType(attachmentTypes);
            buildAttachmentDao.addBuildLabourerAttachment(buildAttachment);
        }
    }
    /**
     * 根据id查询工人信息
     * @param id
     * @return
     */
    public Map queryBuildLabourerById(Integer id){
        BuildLabourerDto buildLabourer = buildLabourerDao.queryBuildLabourerById(id);
        List<BuildLabourerDto> attachmentList = buildLabourerDao.queryBuildLabourerAttachmentById(buildLabourer.getId());
        buildLabourer.setAttachmentList(attachmentList);
        Map map = new HashMap();
        map.put("buildLabourer",buildLabourer);
        return map;
    }


    /**
     * @Description 同步分公司人员信息
     * @author Joanne
     * @create 2018/7/20 11:10
     */
    public void syncBuildLabourInfo(BuildSite localBuildSite, BuildLabourer buildLabourer) {
        //根据buildSiteCode查询服务器是否存在该分公司
        BuildSite buildSite = buildSiteDao.queryBuildSiteByCode(buildLabourer.getBuildSiteCode());
        if(StringUtils.isNotBlank(buildLabourer.getPhone())){
            buildLabourer.setPassword(EncryptUtil.md5(DEFAULT_PASSWORD, buildLabourer.getPhone(), EncryptUtil.HASHITERATIONS));
        }
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //如果不存在则新增
                if ( buildSite==null ){
                    buildSiteDao.addBuildSite(localBuildSite);
                }
                buildLabourerDao.addSingleBuildLabourer(buildLabourer);
                return true;
            }
        });
    }

}
