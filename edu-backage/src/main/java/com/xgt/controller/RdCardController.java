package com.xgt.controller;


import com.xgt.entity.BuildLabourer;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.service.BuildCompanyService;
import com.xgt.service.rdcard.RdCardService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *  身份证读卡器controller
 * @author liuao
 * @date 2018/7/8 9:10
 */
@Controller
@RequestMapping("/rdCard")
public class RdCardController {
    private static final Logger logger = LoggerFactory.getLogger(RdCardController.class);

    @Autowired
    private RdCardService rdCardService;

    /**
     * 放置身份证，读取身份证信息
     * @author liuao
     * @date 2018/7/9 9:11
     */
    @RequestMapping(value = "/getPersionInfo",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> getPersionInfo( ){
        try{

            File f = new File("");

            String a = f.getAbsolutePath();
            logger.info(".......getPersionInfo 路径.....:{}", a);
//            Map map = rdCardService.getPersionInfo();
//            return ResultUtil.createSuccessResult(map);

            // TODO   联调测试后需要删除
            BuildLabourer labourer = testData();
            return ResultUtil.createSuccessResult(labourer);

        }catch(Exception e){
            logger.error("getPersionInfo..........异常 getPersionInfo=", e);
            return ResultUtil.createFailResult("getPersionInfo..........异常 ");
        }
    }


    /**
     * 放置身份证，读取身份证信息，匹配数据库中的工人信息，并返回
     * @author liuao
     * @date 2018/7/9 9:11
     */
    @RequestMapping(value = "/queryLabourInfoByIdCrad",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryLabourInfoByIdCrad( ){
        try{

            File f = new File("");

            String a = f.getAbsolutePath();
            logger.info(".......queryLabourInfoByIdCrad 路径.....:{}", a);
//            BuildLabourer labourer = rdCardService.queryLabourInfoByIdCrad();

            // TODO  联调测试后需要删除
            BuildLabourer labourer = testData();

            return ResultUtil.createSuccessResult(labourer);
        }catch(Exception e){
            logger.error("queryLabourInfoByIdCrad..........异常 ", e);
            return ResultUtil.createFailResult("queryLabourInfoByIdCrad..........异常 ");
        }
    }


    // TODO 联调测试后需要删除
    public static BuildLabourer testData(){
        BuildLabourer labourer = new BuildLabourer();
        labourer.setId(11);
        labourer.setRealname("刘傲");
        labourer.setSex("男");
        labourer.setIdCard("342423199309286770");
        labourer.setWorkType("二级钳工");
        labourer.setNation("汉族");
        labourer.setAddress("安徽省霍邱县潘集乡");
        labourer.setPhone("15556091923");
        labourer.setBase64Photo("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");

        return labourer;
    }
}
