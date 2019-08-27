package com.hualianzb.biut.commons.interfaces;

public interface GlobalMessageType {

    /**
     * 通用模块消息类型
     */
    interface CommonMessageType {
        int BASE = 0x10000000;
        /**
         * 用户Token过期
         */
        int USER_TOKEN_EXPIRE = BASE + 1;
    }

    interface MainRequest {
        int BASE = 0x11000000;
        int GETTOKEN_LAST = BASE + 1;//token值
        int BIUT = BASE + 2;//
        int BIUT_LAST = BASE + 3;//
        int BIU = BASE + 4;//
        int BIU_LAST = BASE + 5;//
        int GETTOKEN_ERROR = BASE + 6;//token值--失败
        int NET_ERROR = BASE + 7;//断网
        int NET_OK = BASE + 8;//断网
    }

    interface RequestCode {
        int BASE = 1000;
        int FromCreate = BASE + 1;
        int FromMnFragment = BASE + 2;
        int FromOfficialFragment = BASE + 3;
        int FromPrivatKey = BASE + 4;
    }

    interface MessgeCode {
        int BASE = 0x12000000;
        int NOTIFYBOOKLIST = BASE + 1;//地址簿改变后通知列表刷新
        int NONET = BASE + 2;//没网
        int CANCELORERROR = BASE + 3;//取消或者出错
        int ContainMessage = BASE + 4;//携带信息
    }

    interface TextFontType {
        int BASE = 0x13000000;
        int LATO_BLACK_PFB_TTF = BASE + 1;
        int LATO_BOLD_TTF = BASE + 2;
        int LATO_BOLD_WOFF_TTF = BASE + 3;
        int LATO_BOLDITALIC_PFB_TTF = BASE + 4;
        int LATO_ITALIC_PFB_TTF = BASE + 5;
        int LATO_LIGHT_WOFF_TTF = BASE + 6;
        int LATO_REGULAR_WOFF_TTF = BASE + 7;
        int MILANOSANS_REGULAR_OTF = BASE + 8;
        int MILANOSANS_REGULAR_TTF = BASE + 9;
        int MONTSERRAT_BLACK_TTF = BASE + 10;
        int MONTSERRAT_BOLD_OTF = BASE + 11;
        int MONTSERRAT_EXTRALIGHT_OTF = BASE + 12;
        int MONTSERRAT_HAIRLINE_OTF = BASE + 13;
        int MONTSERRAT_LIGHT_WOFF_TTF = BASE + 14;
        int MONTSERRAT_MEDIUM_PFB_TTF = BASE + 15;
        int MONTSERRAT_MEDIUMITALIC_TTF = BASE + 16;
        int MONTSERRAT_REGULAR_TTF = BASE + 17;
        int MONTSERRAT_SEMIBOLD_TTF = BASE + 18;
    }

}