package com.hualianzb.biut.commons.constants;

/**
 * Created by wty on 2017/8/22.
 */

public class Constant {

    /**
     * 请求超时时间
     */
    public static final long TIME_OUT_INT = 5 * 1000;
    public static final int BLANK = 5 * 1000;

    //登录
    public static final int LOGIN = 0x10000;

    public static final String PREFIX_16 = "0x";

    /**
     * SQL用
     */
    public static class SpConstant {
        /**
         * 钱包的键
         */
        public static final String FIRST = "FIRST";

        public static final int SWEEP = 12345;

        public static final String TRANSREMARKS = "TRANSREMARKS";//交易记备注
        public static final String KINDTOKEN = "KINDTOKEN";
        public static final String ADDRESSBOOK = "ADDRESSBOOK";//地址簿

        public static final String BIUT = "BIUT";

        /**
         * 当前用户的token
         */
        public static final String USER_TOKEN = "token";
        /**
         * 当前用户的手机号
         */

        public static class RequestCode {
//        int BASE = 0x10000000;
        }
    }
}