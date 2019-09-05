package com.hualianzb.biut.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hualianzb.biut.commons.constants.Constant;
import com.hualianzb.biut.models.AddressBookBean;
import com.hualianzb.biut.models.BiutTransactionBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.ResultInChainBeanOrPool;
import com.hysd.android.platform_huanuo.base.config.PlatformConfig;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BLACK_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLDITALIC_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_ITALIC_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_LIGHT_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MILANOSANS_REGULAR_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MILANOSANS_REGULAR_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BLACK_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_EXTRALIGHT_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_HAIRLINE_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_LIGHT_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUMITALIC_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUM_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_REGULAR_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_SEMIBOLD_TTF;

public class Util {
    //左边第一位不为0的脚标
    public static int getIndexNoneZore(String num) {
        char[] temp = num.toCharArray();
        int index = -1;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != '0') {
                index = i;
                break;
            }
        }
        return index;
    }

    public static String get10Time(String time) {
        String timeLast = time.substring(2);//16进制去掉0x
        BigInteger d = new BigInteger(timeLast, 16);
        Long ddd = Long.parseLong(d.toString(10));//单位秒
        String date = ddd * 1000 + "";
//        TimeUtil.getTime12(ddd);
        return date;
    }

    public static List<AddressBookBean> listAddressBookSort(List<AddressBookBean> list) {
        Collections.sort(list, (o1, o2) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dt1 = format.parse(o1.getCreatTime());
                Date dt2 = format.parse(o2.getCreatTime());
                if (dt1.getTime() > dt2.getTime()) {
                    return 1;

                } else if (dt1.getTime() < dt2.getTime()) {
                    return -1;

                } else {
                    return 0;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            return 0;

        });
        return list;
    }

    public static List<RemembBIUT> ListSort(List<RemembBIUT> list) {
        Collections.sort(list, (o1, o2) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dt1 = format.parse(o1.getCreatTime());
                Date dt2 = format.parse(o2.getCreatTime());
                if (dt1.getTime() > dt2.getTime()) {
                    return 1;

                } else if (dt1.getTime() < dt2.getTime()) {
                    return -1;

                } else {
                    return 0;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            return 0;

        });
        return list;
    }

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                res.getDisplayMetrics());
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());

        return (int) scale;
    }


    /**
     * 根据uri  获取 bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Map<String, String> getTransRemarks() {
        Map<String, String> map = PlatformConfig.getMap(Constant.SpConstant.TRANSREMARKS);
        return map;
    }

    public static boolean isNetworkAvailable(Context activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void copy(Activity activity, String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("content", content);
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    //Scientific notation
    public static String getStringFromSN(int length, String num) {
        if (num.contains("e") || num.contains("E")) {
            Double dString = new Double(num);
            BigDecimal bD = new BigDecimal(dString);
            num = bD.setScale(length, BigDecimal.ROUND_HALF_UP).toPlainString();
        }
        if (num.contains(".")) {
            String changeNum = num.replace(".", "A");//点的话split无法返回String[]，所以替换
            String[] stringCut = changeNum.split("A");
            if (Double.parseDouble(stringCut[1]) > 0) {
                Double dString = new Double(num);
                BigDecimal bD = new BigDecimal(dString);
                String myString = bD.setScale(length, BigDecimal.ROUND_HALF_UP).toPlainString();
                myString = new BigDecimal(myString).stripTrailingZeros().toPlainString();
                return myString;
            } else {
                return stringCut[0];
            }
        } else {
            return num;
        }
    }

    /**
     * @param context
     * @param view
     * @param viewType view类型 1 textView 2 EditText 3 Button
     * @param fontType 字体类型
     * @param <T>
     */
    public static <T> void setFontType(Context context, T view, int viewType, int fontType) {
        Typeface typeface = null;
        switch (fontType) {
            case LATO_BLACK_PFB_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Black.pfb.ttf");
                break;
            case LATO_BOLD_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
                break;
            case LATO_BOLD_WOFF_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.woff.ttf");
                break;
            case LATO_BOLDITALIC_PFB_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Lato-BoldItalic.pfb.ttf");
                break;
            case LATO_ITALIC_PFB_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Italic.pfb.ttf");
                break;
            case LATO_LIGHT_WOFF_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Light.woff.ttf");
                break;
            case LATO_REGULAR_WOFF_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Regular.woff.ttf");
                break;
            case MILANOSANS_REGULAR_OTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "MilanoSans-Regular.otf");
                break;
            case MILANOSANS_REGULAR_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "MilanoSans-Regular.ttf");
                break;
            case MONTSERRAT_BLACK_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-Black.ttf");
                break;
            case MONTSERRAT_BOLD_OTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-Bold.otf");
                break;
            case MONTSERRAT_EXTRALIGHT_OTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-ExtraLight.otf");
                break;
            case MONTSERRAT_HAIRLINE_OTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-Hairline.otf");
                break;
            case MONTSERRAT_LIGHT_WOFF_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-Light.woff.ttf");
                break;
            case MONTSERRAT_MEDIUM_PFB_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-Medium.pfb.ttf");
                break;
            case MONTSERRAT_MEDIUMITALIC_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-MediumItalic.ttf");
                break;
            case MONTSERRAT_REGULAR_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-Regular.ttf");
                break;
            case MONTSERRAT_SEMIBOLD_TTF:
                typeface = Typeface.createFromAsset(context.getAssets(), "Montserrat-SemiBold.ttf");
                break;
        }

        switch (viewType) {
            case 1:
                ((TextView) view).setTypeface(typeface);
                break;
            case 2:
                ((EditText) view).setTypeface(typeface);
                break;
            case 3:
                ((Button) view).setTypeface(typeface);
                break;

        }
    }

}
