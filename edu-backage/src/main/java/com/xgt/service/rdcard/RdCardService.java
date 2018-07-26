package com.xgt.service.rdcard;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.xgt.dao.BuildLabourerDao;
import com.xgt.entity.BuildLabourer;
import com.xgt.util.FileToolUtil;
import com.xgt.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *  读卡器 service
 * Created by 5618 on 2018/6/1.
 */
@Service
public class RdCardService {



    private static final Logger logger = LoggerFactory.getLogger(RdCardService.class);

    @Autowired
    private BuildLabourerDao buildLabourerDao ;

    /**
     *  读取身份证中的人员信息
     * @author liuao
     * @date 2018/7/16 15:28
     */
    public Map getPersionInfo() throws IOException {

        String aa;
        aa = String.format("%c", 0x41);

        IntByReference bb = new IntByReference();
        bb.setValue(0);
        IntByReference cc = new IntByReference();
        cc.setValue(8811);


        byte[] dd = { 0x02, 0x27, 0x00, 0x00 };    //9986

        logger.info("打开端口：..........:{}",IRdcardDll.bell.UCommand1(aa, bb, cc, dd));

        aa = String.format("%c", 0x43);
        logger.info("验证卡：..........:{}",IRdcardDll.bell.UCommand1(aa, bb, cc, dd));

        aa = String.format("%c", 0x49);

        //modified by jiadl 20170623
        //字符串后面必须加“0”
        String strPara2 = "D:/temp/";
        FileToolUtil.createNewDirectory(strPara2);
        // 删除原有文件
        FileToolUtil.delAllFile(strPara2);
        logger.info("编码格式...:{}",System.getProperty("file.encoding"));
        // 设置临时文件目录
        String tempPath = strPara2+"_";
        byte[] bytePara2 = tempPath.getBytes("GBK");

        int flag = IRdcardDll.bell.UCommand1(aa, bb, cc, bytePara2);

        logger.info("读取信息：..........:{}",flag);

        Map<String,Object> result = new HashMap<>();

        // 62171 读取身份证信息正确（无指纹） 62172 读取身份证信息正确（带指纹）
        if((flag==62171) || (flag==62172)){

            // 获取姓名
            byte[] name1 = new byte[20];
            IRdcardDll.bell.GetName(name1);
            String name = new String(name1, "GBK");
            System.out.println(name);
            name = reEncoding(name, "UTF-8");
            result.put("realname", name);

            // 获取性别
            byte[] sex1 = new byte[20];
            IRdcardDll.bell.GetSexGB(sex1);
            String sex = new String(sex1, "GBK");
            sex = reEncoding(sex, "UTF-8");
            result.put("sex", sex);

            // 民族
            byte[] min1 = new byte[20];
            IRdcardDll.bell.GetFolkGB(min1);
            String nation = new String(min1, "GBK");
            nation = reEncoding(nation, "UTF-8");
            result.put("nation", nation);

            // 获取地址
            byte[] addr = new byte[50];
            IRdcardDll.bell.GetAddr(addr);
            String add = new String(addr, "GBK");
            add = reEncoding(add, "UTF-8");
            result.put("address", add);

            // 获取身份证号
            byte[] idNum = new byte[50];
            IRdcardDll.bell.GetIDNum(idNum);
            String idNumber = new String(idNum, "GBK");
            idNumber = reEncoding(idNumber, "UTF-8");
            result.put("idCard", idNumber);

            // 签发机关
            byte[] dep1 = new byte[50];
            IRdcardDll.bell.GetDep(dep1);
            String idIssued = new String(dep1, "GBK");
            idIssued = reEncoding(idIssued, "UTF-8");
            result.put("idIssued", idIssued);

            // 生日
            byte[] bir1 = new byte[20];
            IRdcardDll.bell.GetBirth(bir1);
            String birthday = new String(bir1, "GBK");
            birthday = reEncoding(birthday, "UTF-8");
            result.put("birthday", birthday);

            // 有效开始时间
            byte[] begin = new byte[20];
            IRdcardDll.bell.GetBegin(begin);
            String issuedDate = new String(begin, "GBK");
            issuedDate = reEncoding(issuedDate, "UTF-8");
            result.put("issuedDate", issuedDate);

            // 有效期截至日期
            byte[] end1 = new byte[20];
            IRdcardDll.bell.GetEnd(end1);
            String validDate = new String(end1, "GBK");
            validDate = reEncoding(validDate, "UTF-8");
            result.put("validDate", validDate);

            // 头像base64
            byte[] path1 = new byte[100];
            IRdcardDll.bell.GetBmpPath(path1);
            String path = new String(path1, "GBK");
            logger.info("............头像base64.path..........{}",path);
            path = reEncoding(path,"UTF-8");
            logger.info("............头像base64.path..utf-8........{}",path);
            FileInputStream inputStream = new FileInputStream(path);
            String picBase64 = new String(Base64.encodeBase64(
                    FileToolUtil.inputStreamToByte(inputStream)));
            result.put("base64Photo", picBase64);
            FileToolUtil.delAllFile(strPara2);

        }

        //  62173 读取外国人永久居留身份证正确
        if(flag==62173){

            // 姓名
            byte[] name1 = new byte[120];
            IRdcardDll.bell.FID_GetEnName(name1);
            String name = new String(name1, "GBK");
            name = reEncoding(name, "UTF-8");
            result.put("realname", name);

            // 性别
            byte[] sex1 = new byte[20];
            IRdcardDll.bell.FID_GetSexGB(sex1);
            String sex = new String(sex1, "GBK");
            sex = reEncoding(sex, "UTF-8");
            result.put("sex", sex);

            // 籍贯地址
            byte[] addr = new byte[50];
            IRdcardDll.bell.FID_GetChName(addr);
            String add = new String(addr, "GBK");
            add = reEncoding(add, "UTF-8");
            result.put("address", add);

            // 身份证号码
            byte[] idNum = new byte[50];
            IRdcardDll.bell.FID_GetIDNum(idNum);
            String id = new String(idNum, "GBK");
            id = reEncoding(id, "UTF-8");
            result.put("idCard", id);

            // 签发机关
            byte[] dep1 = new byte[20];
            IRdcardDll.bell.FID_GetNationality(dep1);
            String idIssued = new String(dep1, "GBK");
            idIssued = reEncoding(idIssued, "UTF-8");
            result.put("idIssued", idIssued);

            // 生日
            byte[] bir1 = new byte[20];
            IRdcardDll.bell.FID_GetBirth(bir1);
            String bir = new String(bir1, "GBK");
            bir = reEncoding(bir, "UTF-8");
            result.put("birthday", bir);

            // 有效开始日期
            byte[] begin = new byte[20];
            IRdcardDll.bell.FID_GetBegin(begin);
            String begi = new String(begin, "GBK");
            begi = reEncoding(begi, "UTF-8");
            result.put("issuedDate", begi);

            // 有效截至日期
            byte[] end1 = new byte[20];
            IRdcardDll.bell.FID_GetEnd(end1);
            String end = new String(end1, "GBK");
            end = reEncoding(end, "UTF-8");
            result.put("validDate", end);

            //
            byte[] path1 = new byte[50];
            IRdcardDll.bell.GetBmpPath(path1);
            String path = new String(path1, "GBK");
            path = reEncoding(path,"UTF-8");
            FileInputStream inputStream = new FileInputStream(path);
            String picBase64 = new String(Base64.encodeBase64(
                    FileToolUtil.inputStreamToByte(inputStream)));
            result.put("base64Photo", picBase64);
        }
        logger.info("身份证信息：{}",result);
        // 删除零食文件
        FileToolUtil.delAllFile(strPara2);
        return result;

    }

    /**
     *  编码格式转换
     * @author liuao
     * @date 2018/7/8 11:08
     */
    public String reEncoding(String text, String newEncoding) {
        text =  text.trim();
        return StringUtil.getUTF8StringFromGBKString(text);
    }

    /**
     *
     * @author liuao
     * @date 2018/7/16 15:26
     */
    public BuildLabourer queryLabourInfoByIdCrad() throws IOException {
        Map map = getPersionInfo();
        String idCard = MapUtils.getString(map, "idCard");
        if(StringUtils.isBlank(idCard)) {
            return null;
        }
        return buildLabourerDao.queryLabourByIdCard(idCard);

    }
}
