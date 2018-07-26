package com.xgt.service.sync;

import com.alibaba.fastjson.JSONObject;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.imgtext.ImgtextDao;
import com.xgt.dao.imgtext.ImgtxtClassifyDao;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.imgtext.EduImgtxtClassify;
import com.xgt.util.FileToolUtil;
import com.xgt.util.HttpClientUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeLiu
 * @Description 同步图文Service
 * @date 2018/7/19 14:58
 */
@Service
public class SyncImgTextService extends AbstractSyncService {

    private static final Logger logger = LoggerFactory.getLogger(SyncImgTextService.class);

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private ImgtextDao imgtextDao;

    @Autowired
    private ImgtxtClassifyDao imgtxtClassifyDao;

    /**
     * @Description 根据同步开始id，同步数据, 返回结束时的id
     * @author HeLiu
     * @date 2018/7/19 15:17
     */
    @Override
    protected Integer syncData(Object data) throws IOException {
        //强转成对象
        EduImgtext eduImgtext = (EduImgtext) data;

        //创建一个集合要用于循环路径
        List<String> pathList = new ArrayList<>();
        pathList.add(eduImgtext.getImagePath());
        pathList.add(eduImgtext.getAudioPath());
        pathList.add(eduImgtext.getPdfPath());

        for (String pathStr : pathList) {
            //同步OSS资源到本地
            InputStream in = FileToolUtil.getInputStreamFromUrl(ALIYUNOSS_IMAGEADDRESS + pathStr);
            FileToolUtil.inputStream2File(in, SAFETY_EDU_LOCAL_PATH + pathStr);
        }

        //通过classifyId到服务器端数据库查询图文分类信息
        //二级
        EduImgtxtClassify classify = getClassifyByIdForServer(eduImgtext.getContentClassify());
        //一级
        EduImgtxtClassify classifyParent = getClassifyByIdForServer(classify.getParentId());
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                //同步服务器数据到数据库
                //同步一级目录信息
                Integer parentId = insertImgTextClassify(classifyParent);
                classify.setParentId(parentId);
                //同步二级目录信息
                Integer classifyId = insertImgTextClassify(classify);
                eduImgtext.setContentClassify(classifyId);
                //同步图文
                imgtextDao.addPicArticle(eduImgtext);
                //返回服务器端同步的图文id
                return eduImgtext.getId();
            }
        });
    }

    /**
     * @Description 同步图文分类信息到本地
     * 如果存在就查询已存在的编号
     * @author HeLiu
     * @date 2018/7/20 10:30
     */
    public Integer insertImgTextClassify(EduImgtxtClassify eduImgtxtClassify) {
        //和本地对比查看是否存在，存在不同步，不存在同步到本地
        Integer isExists = imgtxtClassifyDao.isExistsToLocalByClassifyName(eduImgtxtClassify.getClassifyName());
        if (isExists != null && isExists == 0) {
            //开始同步到本地
            return imgtxtClassifyDao.addClassify(eduImgtxtClassify);
        } else {
            //根据根据ClassifyName查询图文分类信息
            EduImgtxtClassify imgtxtClassify = imgtxtClassifyDao.queryClassifyByNameToLocal(eduImgtxtClassify.getClassifyName());
            return imgtxtClassify.getId();
        }
    }

    /**
     * @Description 通过classifyId到服务器端数据库查询图文分类信息
     * @author HeLiu
     * @date 2018/7/19 20:21
     */
    public EduImgtxtClassify getClassifyByIdForServer(Integer classifyId) {
        //远程服务器地址
        String url = SAFETY_EDU_REMOTE_URL + SystemConstant.RemoteServer.imgtext_queryClassifyById;

        //组装请求数据
        Map<String, Object> param = new HashMap<>();
        param.put("classifyId", classifyId);

        //发送请求
        Map result = HttpClientUtil.doPost(url, param);
        String data = MapUtils.getString(result, "data");
        //请求成功（code = 0）,返回数据不为空
        if (MapUtils.getIntValue(result, "code") == 0
                && StringUtils.isNotBlank(data)) {
            //JSON的对象格式的字符串转换成对象
            EduImgtxtClassify eduImgtxtClassify = JSONObject.parseObject(data, EduImgtxtClassify.class);
            return eduImgtxtClassify;
        } else {
            return null;
        }
    }

    /**
     * @Description 根据同步id ，查询是否有需要同步的数据,true 表示有， false 表示没有
     * @author HeLiu
     * @date 2018/7/19 15:18
     */
    @Override
    protected Pair exsitNeedSync(Integer previousId) {
        //远程服务器地址
        String url = SAFETY_EDU_REMOTE_URL + SystemConstant.RemoteServer.imgtext_queryNextImgTestById;

        //发送请求到远程服务器，查询是否有需要同步的数据
        Map<String, Object> param = new HashMap<>();
        param.put("previousId", previousId);

        //发送请求
        Map result = HttpClientUtil.doPost(url, param);
        String data = MapUtils.getString(result, "data");
        //请求成功（code = 0）,返回数据不为空
        if (MapUtils.getIntValue(result, "code") == 0
                && StringUtils.isNotBlank(data)) {
            //JSON的对象格式的字符串转换成对象
            EduImgtext eduImgtext = JSONObject.parseObject(data, EduImgtext.class);
            Pair<Boolean, Object> resultPair = Pair.of(true, eduImgtext);
            return resultPair;
        } else {
            Pair<Boolean, Object> resultPair = Pair.of(false, null);
            return resultPair;
        }
    }
}
