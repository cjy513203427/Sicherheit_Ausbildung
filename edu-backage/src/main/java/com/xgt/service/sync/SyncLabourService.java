package com.xgt.service.sync;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.BuildLabourerDao;
import com.xgt.dao.BuildSiteDao;
import com.xgt.entity.BuildLabourer;
import com.xgt.entity.BuildSite;
import com.xgt.entity.video.ChapterContent;
import com.xgt.util.HttpClientUtil;
import com.xgt.util.JSONUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.xgt.constant.SystemConstant.RemoteServer.labourer_syncBuildLabourInfo;

/**
 * @program: safety_edu
 * @description: 人员同步
 * @author: Joanne
 * @create: 2018-07-19 17:51
 **/
@Service
public class SyncLabourService extends AbstractSyncService {

    @Autowired
    private BuildLabourerDao buildLabourerDao ;

    @Autowired
    private BuildSiteDao buildSiteDao ;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    /**
     * 根据同步开始id，同步数据, 返回结束时的id, 不同模块，实现不一样，这里抽象出来
     *
     * @param data
     * @author liuao
     * @date 2018/7/18 11:32
     */
    @Override
    protected Integer syncData(Object data) {
        try {
            BuildLabourer buildLabourer = (BuildLabourer) data;
            //查询本地分公司
            BuildSite localBuildSite = buildSiteDao.queryBuildSiteByCode(buildLabourer.getBuildSiteCode());
            //同步信息
            String url = SAFETY_EDU_REMOTE_URL + labourer_syncBuildLabourInfo ;

            // 设置参数
            Map<String,Object> param = new HashMap<>();
            param.put("buildLabourer",JSON.toJSONString(buildLabourer));
            param.put("localBuildSite",JSON.toJSONString(localBuildSite));

            // 发送请求
            Map result = HttpClientUtil.doPost(url, param);

            //  返回成功，且返回数据不是空
            String resultData = MapUtils.getString(result, "data");
            if(MapUtils.getIntValue(result, "code") == 0){
                return 0 ;
            }
            return 1 ;
        }catch (Exception e){
            return 1 ;
        }
    }

    /**
     * 根据同步id ，查询是否有需要同步的数据,true 表示有， false 表示没有
     *
     * @param previousId 远程服务器数据库保存的最后一个人员id
     * @author liuao
     * @date 2018/7/18 16:21
     */
    @Override
    protected Pair<Boolean, Object> exsitNeedSync(Integer previousId) {
        //查询本地是否有需要同步的人员信息
        BuildLabourer labour = buildLabourerDao.queryNextLabourByPreviousId(previousId);
        return labour!=null?Pair.of(true,labour):Pair.of(false,labour);
    }
}
