package com.xgt.service;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.BuildAttachmentDao;
import com.xgt.dao.BuildCompanyDao;
import com.xgt.dao.BuildSiteDao;
import com.xgt.dto.BuildSiteDto;
import com.xgt.entity.BuildAttachment;
import com.xgt.entity.BuildCompany;
import com.xgt.entity.BuildLabourer;
import com.xgt.entity.BuildSite;
import com.xgt.util.JSONUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 5618 on 2018/6/1.
 */
@Service
public class BuildSiteService {

    @Value("${aliyunOSS.accessKeyId}")
    protected String accessKeyId;

    @Value("${aliyunOSS.accessKeySecret}")
    protected String accessKeySecret;

    @Value("${aliyunOSS.uploadUrl}")
    protected String endpoint;

    @Value("${aliyunOSS.bucketname}")
    protected String bucketName;

    private static final Logger logger = LoggerFactory.getLogger(BuildSiteService.class);

    @Autowired
    private BuildSiteDao buildSiteDao;

    @Autowired
    private BuildCompanyDao buildCompanyDao;

    @Autowired
    private BuildAttachmentDao buildAttachmentDao;
    @Autowired
    TransactionTemplate transactionTemplate ;
    /**
     * 建筑工地信息查询（分页）
     *
     * @param buildSite
     * @return
     */
    public Map<String, Object> queryBuildSiteList(BuildSite buildSite) {
        Integer total = buildSiteDao.queryBuildSiteCount(buildSite);
        List<BuildSite> list = null;
        if (total != null && total > 0) {
            list = buildSiteDao.queryBuildSiteList(buildSite);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, BuildSite.class));
        map.put("total", total);
        return map;
    }

    public Map<String,Object> queryBuildSiteById(Integer labourerId){
        List<BuildSiteDto> buildSiteDtoList = buildSiteDao.queryBuildSiteById(labourerId);
        for (BuildSiteDto buildSiteDto:buildSiteDtoList){
            List<BuildSiteDto> attachmentList = buildSiteDao.queryBuildSiteAttachment(labourerId);
            buildSiteDto.setAttachmentList(attachmentList);
        }
        Map map = new HashMap();
        map.put("buildSiteDtoList",buildSiteDtoList);
        return map;
    }

    /**
     * 工地启用和禁用
     *
     * @param id
     * @param status
     */
    public void enOrDisBuildSite(Integer id, String status, String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (SystemConstant.State.ACTIVE_STATE.equals(status)) {
            //禁用的时候将对应的工地账号全部禁用
            buildSiteDao.disBuildLabourers(code, SystemConstant.State.DISABLE_STATE);
            map.put("status", SystemConstant.State.DISABLE_STATE);
        } else {
            map.put("status", SystemConstant.State.ACTIVE_STATE);
        }
        buildSiteDao.enOrDisBuildSite(map);
    }

    /**
     * 新增建筑工地
     *
     * @param buildSite
     */
    public void createBuildSite(BuildSite buildSite) {
        //获得自动生成的编号
        Map<String, Object> map = getCode();
        buildSite.setId(MapUtils.getInteger(map, "id"));
        buildSite.setCode(MapUtils.getString(map, "code"));
        buildSiteDao.createBuildSite(buildSite);
    }

    /**
     * 自动生成工地编号
     * （先插入一条数据获取id，根据id生成工地编号）
     * 防止重复
     * 返回id和工地编号
     *
     * @return
     */
    public Map<String, Object> getCode() {
        Map<String, Object> map = new HashMap<>();
        Integer id = buildSiteDao.insertNullRecord();
        String code = String.format("%06d", id);
        map.put("id", id);
        map.put("code", code);
        return map;
    }

    /**
     * 修改建筑工地
     *
     * @param buildSite
     */
    public void updateBuildSite(BuildSite buildSite) {
        buildSiteDao.updateBuildSite(buildSite);
    }

    /**
     * 生成公司-工地树
     * @return
     */
    public List<BuildCompany> generateTree(Integer userId){
        List<BuildCompany> buildCompanies = buildCompanyDao.queryParentNode(userId);
        List<BuildSite> buildSites = buildSiteDao.queryChildNode();
        for(BuildCompany buildCompany:buildCompanies){
            for(BuildSite buildSite:buildSites){
                if (buildCompany.getId()==buildSite.getCompanyId()) {
                    buildCompany.setLeaf(true);
                    List<BuildSite> children = buildSiteDao.queryChildNodeByCompanyId(buildSite.getCompanyId());
                    buildCompany.setChildren(children);
                    //使用break防止重复setChildren而影响效率
                    break;
                }
            }
        }
        return buildCompanies;
    }

    /**
     * 添加单位信息/新增机构
     * @param files
     * @param buildSite
     * @param buildAttachment
     * @param fileNames
     */
    public void addBuildSite(String[] files,String[] fileNames, BuildSite buildSite, BuildAttachment buildAttachment) throws FileNotFoundException {
            Integer id = buildSiteDao.addBuildSite(buildSite);
            buildAttachment.setForeignId(id);
            transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    /**
                     * 多个Base64文件保存到数据库
                     */
                    if (files.length != 0) {
                        for (int i = 0; i < files.length; i++) {
                            String base64File = files[i];
                            String fileName = fileNames[i];
                            buildAttachment.setAttachmentName(fileName);
                            uploadBuildSiteAttachment(buildAttachment, fileName, base64File);
                        }
                        String code = String.format("%06d", id);
                        buildSiteDao.updateBuildSiteCode(id, code);
                    }
                    return true;
                }
            });
    }

    /**
     * 修改工地信息
     * @param files
     * @param fileNames
     * @param buildSite
     * @param buildAttachment
     * @throws FileNotFoundException
     */
    public void modifyBuildSite(String[] files,String[] fileNames, BuildSite buildSite, BuildAttachment buildAttachment) throws FileNotFoundException {
        Integer id = buildSiteDao.modifyBuildSite(buildSite);
        buildAttachment.setForeignId(id);
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                /**
                 * 多个Base64文件保存到数据库
                 */
                if(files.length!=0) {
                for(int i = 0;i<files.length;i++){
                        String base64File = files[i];
                        String fileName = fileNames[i];
                        buildAttachment.setAttachmentName(fileName);
                        uploadBuildSiteAttachment(buildAttachment,fileName,base64File);
                    }
                }
                return true;
            }
        });
    }

    /**
     * Base64保存到数据库
     * @param buildAttachment
     * @throws FileNotFoundException
     */
    public void uploadBuildSiteAttachment(BuildAttachment buildAttachment,String fileName,String base64File)   {
        buildAttachment.setAttachmentName(fileName);
        buildAttachment.setAttachmentPath(base64File);
        buildAttachmentDao.addBuildSiteAttachment(buildAttachment);
    }

    /**
     * 删除分公司/工地/单位
     * @param id
     */
    public Integer deleteBuildSite(Integer id){
        return buildSiteDao.deleteBuildSite(id);
    }
}
