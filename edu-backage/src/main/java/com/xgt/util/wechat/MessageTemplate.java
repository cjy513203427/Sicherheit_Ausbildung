package com.xgt.util.wechat;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信模板消息内容组装
 * @author Joanne
 * @create 2017-10-24 17:49
 **/
public class MessageTemplate {

    /**
     * 合同推送消息模板内容
     * @Description
     * @author Joanne
     * @create 2018/4/18 9:37
     */
    public static Map contractMessage(String contractName,String expeirationDate){
        Map first = new HashMap();
        first.put("value","您好，您提交的订单合同已经生成");
        first.put("color","#173177");
        Map keynote1 = new HashMap();
        keynote1.put("value","云艺化VR");
        keynote1.put("color","#173177");
        Map keynote2 = new HashMap();
        keynote2.put("value",contractName);
        keynote2.put("color","#173177");
        Map keynote3 = new HashMap();
        keynote3.put("value",expeirationDate);
        keynote3.put("color","#173177");
        Map remark = new HashMap();
        remark.put("value","点击可以直接预览合同，合同底部点击确认按钮提交已阅回执");
        remark.put("color","#173177");
        Map data = new HashMap();
        data.put("first",first);
        data.put("keyword1",keynote1);
        data.put("keyword2",keynote2);
        data.put("keyword3",keynote3);
        data.put("remark",remark);
        return data;
    }
}
