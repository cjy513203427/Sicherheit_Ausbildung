package com.xgt.util;



import com.alibaba.fastjson.JSONArray;

import java.util.*;

/**
 * Author CC
 * Date: 2018年5月10日
 * Desc: 顺丰运费计算
 */
public class SFFreightUtil {
    private static int weight = 20;
    /**
     * 包河区的顺丰代码
     */
    private static String destArea = "A340111000";
    private static String CalculatingPriceURL = "http://www.sf-express.com/sf-service-owf-web/service/rate/newRates?origin=%s&dest=" + destArea + "&weight=" + weight + "&time=%s&volume=0&queryType=2&lang=sc&region=cn";
    private static String CodeURL = "http://www.sf-express.com/sf-service-owf-web/service/region/%s/subRegions/origins?originCode=%s&level=%s&lang=sc&region=cn";


    private final static Map<String,Integer> GeneralLogisticsPrices=new HashMap(){{
        put("湖南省",210);
        put("内蒙古",380);
        put("湖北省",220);
        put("黑龙江",360);
        put("吉林省",320);
        put("山西省",310);
        put("辽宁省",320);
        put("新疆省",390);
        put("浙江省",230);
        put("陕西省",290);
        put("江西省",230);
        put("江苏省",240);
        put("福建省",230);
        put("四川省",280);
        put("湖南省",260);
        put("安徽省",240);
        put("河南省",230);
        put("贵州省",260);
        put("山东省",230);
        put("河北省",260);
        put("云南省",280);
        put("北京",190);
        put("天津",190);
        put("重庆",190);
        put("上海",170);
    }};

    public static Double getFreight(String originProvince, String originCity, String originArea) {
        int level=1;
        //获取省的代号
        String provinceCode = "";
        if(originProvince.equals(originCity)){
            provinceCode="A000086000";
            level=2;
        }else{
            String provinceResult = HttpClientUtil.doGet(String.format(CodeURL, "A000086000","A000086000",level));
            List<HashMap> provinceList = JSONArray.parseArray(provinceResult, HashMap.class);
            for (Map province : provinceList) {
                if (originProvince.equals(province.get("name").toString())) {
                    provinceCode = province.get("code").toString();
                    break;
                }
            }
        }


        //获取市的代号
        String cityCode = "";
        String cityResult = HttpClientUtil.doGet(String.format(CodeURL, provinceCode,provinceCode,1));
        List<HashMap> cityList = JSONArray.parseArray(cityResult, HashMap.class);
        for (Map city : cityList) {
            if (originCity.equals(city.get("name").toString())) {
                cityCode = city.get("code").toString();
                break;
            }
        }

        //获取区的代号
        String areaCode = "";
        String areaResult = HttpClientUtil.doGet(String.format(CodeURL, cityCode,cityCode,level));
        List<HashMap> areaList = JSONArray.parseArray(areaResult, HashMap.class);
        for (Map area : areaList) {
            if (originArea.equals(area.get("name").toString())) {
                areaCode = area.get("code").toString();
                break;
            }
        }
        if(areaCode==""){
            areaCode=areaList.get(0).toString();
        }

        //获取最终结果
        String nowTime = DateUtil.getStringDate("yyyy-MM-dd")+"T17%3A30%3A00%2B08%3A00";
        String result = HttpClientUtil.doGet(String.format(CalculatingPriceURL, areaCode, nowTime));
        List<HashMap> resultList = JSONArray.parseArray(result, HashMap.class);
        for (Map priceResult : resultList) {
            if (priceResult.get("limitTypeCode").equals("T4")) {
                return Double.parseDouble(priceResult.get("totalFreight").toString());
            }
        }
        return null;
    }

    /**
     * 获取普通物流的费用
     * @param originProvince
     * @return
     */
    public static Double getGeneralLogisticsPrice(Integer haveLogistics,String originProvince){
        return 6.5*GeneralLogisticsPrices.get(originProvince)+600+500;
    }
}
