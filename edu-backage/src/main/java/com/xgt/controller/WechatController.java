package com.xgt.controller;

import com.alibaba.druid.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.xgt.util.HttpClientUtil;
import com.xgt.util.ResultUtil;
import com.xgt.util.wechat.Element;
import com.xgt.util.wechat.SignUtil;
import com.xgt.util.wechat.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Joanne
 * @Description
 * @create 2018-04-02 16:12
 **/
@Controller
@RequestMapping("/wechat")
public class WechatController {
    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate ;


    /**
     * 微信授权的CODE
     * @param
     * @throws IOException
     */
    @RequestMapping("/getYyhWxCode")
    @ResponseBody
    public String getYyhWxCode(String signature,String timestamp,
                             String nonce,String echostr) {
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                return echostr;
            }
            return "false";
        }catch(Exception e){
            logger.error("update..........异常 signature=" + signature+" timestamp="+timestamp+" nonce="+nonce+" echostr="+echostr, e);
            return  "update..........异常 signature=" + signature+" timestamp="+timestamp+" nonce="+nonce+" echostr="+echostr;
        }

    }

    /**
     * 微信授权必要的文件
     * @param
     * @throws IOException
     */
    @RequestMapping("/MP_verify_C5wgdkL0oHYgugJH.txt")
    @ResponseBody
    public void downLoad(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("Content-Type:text/plain");
        byte[] bytes = Utils.readByteArrayFromResource("MP_verify_C5wgdkL0oHYgugJH.txt");
        if (bytes != null) {
            httpServletResponse.getOutputStream().write(bytes);
            httpServletResponse.flushBuffer();
        }
    }

    /**
     * 获取code，并跳转页面
     * @param httpServletResponse
     * @param code
     * @throws IOException
     */
    @RequestMapping("/response")
    @ResponseBody
    public void response(HttpServletResponse httpServletResponse,String code,String state) throws IOException {
        String responseUrl= decryptBASE64(state);
        if(responseUrl.indexOf("?")>-1){
            httpServletResponse.sendRedirect(responseUrl+"&code="+code);
        }else{
            httpServletResponse.sendRedirect(responseUrl+"?code="+code);
        }
    }

    /**
     * 前台ajax传code获取微信用户个人信息
     * @param code
     * @return
     */
    @RequestMapping("/getWeUser")
    @ResponseBody
    public Map<String,Object> getWeUser(String code) {
        logger.info("微信个人信息换取=====code:" + code);
        try {
            String httpOrgCreateTest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Element.appid + "&secret=" + Element.appSecret + "&code=" + code + "&grant_type=authorization_code";
            String accessToken = HttpClientUtil.doGet(httpOrgCreateTest);
            Map getUserInfo = JSONArray.parseObject(accessToken, HashMap.class);
            Map user = new HashMap();
            try {
                logger.info("微信个人信息换取=====openid:" + getUserInfo.get("openid"));
                if (getUserInfo.get("access_token") != null) {
                    httpOrgCreateTest = "https://api.weixin.qq.com/sns/userinfo?access_token=" + getUserInfo.get("access_token") + "&openid=" + getUserInfo.get("openid") + "&lang=zh_CN";
                    String userInfo = HttpClientUtil.doGet(httpOrgCreateTest);
                    logger.info("微信个人信息换取=====userinfo:" + userInfo);
                    user = JSONArray.parseObject(userInfo, HashMap.class);
                    return ResultUtil.createSuccessResult(user);
                }
            } catch (Exception e) {
                logger.info("微信个人信息换取失败=====:" + e.getMessage());
                return ResultUtil.createSuccessResult(user);
            }
            return ResultUtil.createSuccessResult(user);
        }catch(Exception e){
            logger.error("getWeUser..........异常 code=" + code, e);
            return ResultUtil.createFailResult("getWeUser..........异常 code =" + code);
        }
    }



    /**
     * 获取公众号用户信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String,Object> getUserInfo(String code) throws Exception {
        //使用code换取accesstoken及openid
        String ACCESS_TOKEN = "accessToken";
        Map<String,String> accessToken= WechatUtil.getWeChatAccessToken(code);
        System.out.println("accessToken:"+accessToken.toString());
        if(accessToken.get("access_token")==null){
            return ResultUtil.createFailResult("用户信息获取失败！");
        }

        //获取微信公众号用户信息
        Map<String,Object> userInfo = WechatUtil.getSubscribeUser(WechatUtil.getWeChatToken(stringRedisTemplate)
                .get(ACCESS_TOKEN),accessToken.get("openid"));
        System.out.println("===============公众号获取的信息"+userInfo);
        //通过收授权获取的accesstoken拉取用户信息,为保证获取到昵称和头像地址
        Map<String,Object> userInfoMore = WechatUtil.getWeChatUserInfo(accessToken);
        if(userInfo.get("nicknme")==null){
            System.out.println("===============未关注但授权了"+userInfoMore);
            userInfo.put("nickname",userInfoMore.get("nickname").toString().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""));
            userInfo.put("headimgurl",userInfoMore.get("headimgurl").toString());
        }

        Integer t = (Integer) userInfo.get("subscribe");
        if(t==0){
            userInfo.put("message","未关注");
            //完善微信用户信息表信息
            // wechatlotteryService.updateUserInfo(userInfo);
        }
        return ResultUtil.createSuccessResult(userInfo);
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key) {
        byte[] bt;
        try {
            bt = (new BASE64Decoder()).decodeBuffer(key);
            return new String(bt);//如果出现乱码可以改成： String(bt, "utf-8")或 gbk
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
