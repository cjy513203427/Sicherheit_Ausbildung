package com.xgt.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgt.dao.BuildLabourerDao;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;

public class WordUtil {

    private Configuration configuration = null;

    @Autowired
    private BuildLabourerDao buildLabourerDao;
    public WordUtil(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) {
        WordUtil test = new WordUtil();
        //test.createWord();
    }

    public void createWord(Map<String,Object> dataMap) throws IOException {
        //Map<String,Object> dataMap=new HashMap<String,Object>();
        getData(dataMap);
        configuration.setClassForTemplateLoading(this.getClass(), "/template");  //FTL文件所存在的位置
        Template t=null;
        try {
            t = configuration.getTemplate("buildLabourerExportNormal.ftl"); //文件名
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outFile = new File("D:\\py交易\\导出的文档Normal.doc");  //导出文档的存放位置
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    private void getData(Map<String, Object> dataMap) {
        /*dataMap.put("realname", "华宝宝");
        dataMap.put("sex", "男");
        dataMap.put("age","18");
        dataMap.put("address","安徽省安庆市");
        dataMap.put("postType","一品总经理");
        dataMap.put("workType","木工");
        dataMap.put("attachmentName","结婚证");
        dataMap.put("attachmentCode","PY9865214");
        dataMap.put("siteName","一品华宝宝有限责任公司");
        dataMap.put("idCard","3402515555666644");
        dataMap.put("emergencyContact","谷阿莫");
        dataMap.put("emergencyContactPhone","18895358110");
        dataMap.put("programType","班组安全培训");
        dataMap.put("createTime","培训日期");
        dataMap.put("companyId",1);
        dataMap.put("passStatus","1");
        dataMap.put("programName","新入场工人安全教育试卷");
        dataMap.put("totalScore",80);
        dataMap.put("singleChooseCount",31);

        List<Map<String,Object>> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("title", "入场基本要求");
            map.put("content", "要求"+i);
            list1.add(map);
        }
        dataMap.put("trainInfoList", list1);

        List<Map<String,Object>> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("eduTitle", (i+1));
            map.put("eduAnswer", "选项A:好玩的");
            list2.add(map);
        }
        dataMap.put("eduList", list2);

        List<Map<String,Object>> list3 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("answerTitle", (i+1));
            map.put("answerInfo", "B");
            list3.add(map);
        }
        dataMap.put("answerList", list3);

        List<Map<String,Object>> list4 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("singleEduTitle", (i+1)+".生活区严禁私接、乱拉电线。");
            map.put("singleEduInfo", "A，是    B，否 ");
            list4.add(map);
        }
        dataMap.put("singleEduList", list4);

        List<Map<String,Object>> list5 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("multiEduTitle", (i+1)+".以下属于员工进入施工现场，必须禁止的行为的是（ ）。");
            map.put("multiEduInfo", "A，带小孩进入施工现场    B，穿拖鞋进入施工现场    C，赤膊进入施工现场    ");
            list5.add(map);
        }
        dataMap.put("multiEduList", list5);

        dataMap.put("iconPath","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");
        dataMap.put("QRCode","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");*/
    }
}
