package com.xgt.util.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgt.constant.SystemConstant;
import com.xgt.util.HttpClientUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xgt.constant.SystemConstant.REDIS_KEY_PREFIX;

/**
 * Created by CC on 2017/8/2.
 */
public class WechatUtil {
    public static final String WECHAT_CONTRACT_PUBMESSAGE_ID = "StKsOMG-VS4j4ii297UQtSS7RN2rVOoKY0vrMVIz5fA";

    public static final  String JUMP_SERVER_URL="http://yun.xgtvr.cn/";
    /**
     * 获取签名信息
     * @return 返回签名等
     */
    public static Map<String,String>  getWeChatToken(StringRedisTemplate stringRedisTemplate)  {
        String appid = Element.appid;
        String appSecret = Element.appSecret;
        String url_Template_GetAccessToken ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        String url_Template_GetAccessTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

        String keyWechatAccessToken = REDIS_KEY_PREFIX + "wechatAccessToken";
        String keyWechatAccessTicket = REDIS_KEY_PREFIX + "wechatAccessTicket";

        String accessToken = stringRedisTemplate.opsForValue().get(keyWechatAccessToken);
        if(accessToken == null){
            //获取token
            String url_GetAccessToken = String.format(url_Template_GetAccessToken, appid,appSecret);
            String result = HttpClientUtil.doGet(url_GetAccessToken);
            JSONObject accessTokenMap= JSONArray.parseObject(result);
            accessToken = accessTokenMap.getString("access_token");
            stringRedisTemplate.opsForValue().set(keyWechatAccessToken, accessToken,7200, TimeUnit.SECONDS );
        }

        String accessTicket = stringRedisTemplate.opsForValue().get(keyWechatAccessTicket);
        if(accessTicket == null){
            //获取ticket
            String url_GetAccessTicket = String.format(url_Template_GetAccessTicket, accessToken);
            String result = HttpClientUtil.doGet(url_GetAccessTicket);
            JSONObject url_GetAccessTicketMap= JSONArray.parseObject(result);
            accessTicket = url_GetAccessTicketMap.getString("ticket");
            stringRedisTemplate.opsForValue().set(keyWechatAccessTicket, accessTicket,7200, TimeUnit.SECONDS );
        }

        Map<String,String> result = new HashMap<String,String>();
        result.put("accessToken", accessToken);
        result.put("accessTicket", accessTicket);
        return result;
    }

    /**
     * CC
     * @param code 用户唯一码
     * @return 用户凭据和OpenId
     */
    public static Map<String,String> getWeChatAccessToken(String code){
        String httpOrgCreateTest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Element.appid + "&secret=" + Element.appSecret + "&code=" + code + "&grant_type=authorization_code";
        String accessToken = HttpClientUtil.doGet(httpOrgCreateTest);
        Map<String,String> result = JSONArray.parseObject(accessToken, HashMap.class);
        return result;
    }

    /**
     * CC
     * @param accessToken
     * @return
     */
    public static Map<String,Object> getWeChatUserInfo(Map<String,String> accessToken){
        String httpOrgCreateTest = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken.get("access_token") + "&openid=" + accessToken.get("openid") + "&lang=zh_CN";
        String userInfo = HttpClientUtil.doGet(httpOrgCreateTest);
        Map<String,Object> result = JSONArray.parseObject(userInfo, HashMap.class);
        System.out.println(result);
        return result;
    }

    /**
     * 获取微信公众号用户信息
     * @author Joanne
     * @date 2017/12/5 11:16
     * @param accessToken
     * @return java.util.Map
     * @excption
     */
    public static Map getSubscribeUser(String accessToken,String openid ){
        String httpGetAddress = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                + accessToken+"&openid=" + openid+"&lang=zh_CN";
        System.out.println("url:"+httpGetAddress);
        String userInfo = HttpClientUtil.doGet(httpGetAddress);
        //    String userInfo = "{\"subscribe\": 1, \"openid\": \"o6_bmjrPTlm6_2sgVt7hMZOPfL2M\", \"nickname\": \"Band\", \"sex\": 1, \"language\": \"zh_CN\", \"city\": \"广州\", \"province\": \"广东\", \"country\": \"中国\", \"headimgurl\":\"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0\",\"subscribe_time\": 1382694957,\"unionid\": \" o6_bmasdasdsad6_2sgVt7hMZOPfL\",\"remark\": \"\",\"groupid\": 0,\"tagid_list\":[128,2]}" ;
        Map<String,Object> result = JSONArray.parseObject(userInfo, HashMap.class);
        System.out.println(result);
        return result;
    }

    /**
     * 发送微信消息的方法
     * @param accessToken  微信全局唯一凭据
     * @param messageData  消息模版Map集合
     * @param openId     用户唯一ID
     * @param jumpUrl    跳转地址
     * @param templateId  模版ID
     */
    public static void sendMessage(String accessToken,Map messageData,String openId,String jumpUrl,String templateId){
        Map handOutMessage = new HashMap();
        String messageUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        handOutMessage.put("url",JUMP_SERVER_URL+jumpUrl);
        handOutMessage.put("touser",openId);
        handOutMessage.put("template_id", templateId);
        handOutMessage.put("data",messageData);
        HttpClientUtil.doPost(messageUrl, handOutMessage, "UTF-8");
    }

}