package com.hualianzb.biut.commons.constants;

/**
 * Created by wty on 2018/9/27.
 */
public class RequestHost {

    public static String mainUrl = "http://scan.biut.io:";
    //    public static String testUrl = "http://18.179.198.60";
    public static String testUrl = "http://test.biut.io:";
    public static String url = "http://scan.secblock.io/publishversionapi";//服务器
    public static String webUrl = mainUrl + "///search?search=";
    public static String biut_url = mainUrl + "3002/";
    public static String biu_url = mainUrl + "3003/";

    /**
     * s
     * 访问相关
     */
    public static class AppAccessController {
        public static final String BASE = "AppAccessController/";
        public static final String getAccessList = BASE + "getAccessList";//	查询访问列表    }
    }

}