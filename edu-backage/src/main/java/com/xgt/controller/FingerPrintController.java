package com.xgt.controller;

import com.xgt.entity.BuildLabourer;
import com.xgt.service.BuildLabourerService;
import com.xgt.service.fingerprint.FingerPrintService;
import com.xgt.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 *  采集指纹controller
 * @author liuao
 * @date 2018/7/11 15:49
 */
@Controller
@RequestMapping("/fingerPrint")
public class FingerPrintController {

    private static final Logger logger = LoggerFactory.getLogger(FingerPrintController.class);
    @Autowired
    private FingerPrintService fingerPrintService;


    @Autowired
    private BuildLabourerService buildLabourerService;

    /**
     *  获取指纹base64
     * @author liuao
     * @date 2018/7/11 17:02
     */
    @RequestMapping("/collectionFingerBase64")
    @ResponseBody
    public Map collectionFingerBase64(){
        try{
            String imgBase64 = fingerPrintService.collectionFingerBase64();

            //  TODO 联调测试后没删除
            imgBase64 = "iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=";


            if(StringUtils.isBlank(imgBase64)){
                return ResultUtil.createFailResult("fingerprint..........失败  ");
            }
            return ResultUtil.createSuccessResult(imgBase64);
        }catch(Exception e){
            logger.error("fingerprint..........异常 " , e);
            return ResultUtil.createFailResult("fingerprint..........异常  ");
        }
    }

    /**
     *  匹配指纹，获取对应的人员信息
     * @author liuao
     * @date 2018/7/11 17:02
     */
    @RequestMapping("/fingerMatchLabour")
    @ResponseBody
    public Map fingerMatchLabour(){
        try{
            // TODO  测试结束后，放开
//            BuildLabourer labourer = buildLabourerService.fingerMatchLabourMsg();

            // TODO  联调测试结束后， 删除
            BuildLabourer labourer = RdCardController.testData();

            return ResultUtil.createSuccessResult(labourer);
        }catch(Exception e){
            logger.error("fingerprint..........异常 " , e);
            return ResultUtil.createFailResult("fingerprint..........异常  ");
        }
    }


    /**
     *  关闭指纹识别服务
     * @author liuao
     * @date 2018/7/11 17:02
     */
    @RequestMapping("/closeFinger")
    @ResponseBody
    public Map closeFinger(){
        try{
             fingerPrintService.freeSensor();
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("closeFinger..........异常 " , e);
            return ResultUtil.createFailResult("closeFinger..........异常  ");
        }
    }

}
