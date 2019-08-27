package com.hualianzb.biut.application;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bulong.rudeness.RudenessScreenHelper;
import com.hualianzb.biut.BuildConfig;
import com.hualianzb.biut.biutUtil.SECBlockJavascriptAPI;
import com.hualianzb.biut.commons.constants.Constant;
import com.hualianzb.biut.commons.constants.RequestHost;
import com.hualianzb.biut.models.DaoMaster;
import com.hualianzb.biut.models.DaoSession;
import com.hualianzb.biut.models.PropertyBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.RemembBIUTDao;
import com.hualianzb.biut.models.ResultInChainBeanOrPool;
import com.hualianzb.biut.models.ResultInChainBeanOrPoolDao;
import com.hualianzb.biut.models.TokenBean;
import com.hualianzb.biut.models.TokenBeanDao;
import com.hysd.android.platform_huanuo.base.config.PlatformConfig;
import com.hysd.android.platform_huanuo.base.manager.BaseApplication;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @auther:Created by wangtianyun on 2018/4/2.
 * des:
 */

public class BIUTApplication extends BaseApplication {
    private static BIUTApplication instance = null;
    public static List<Activity> activityList = new LinkedList<>();
    private static Context mContext;
    public List<PropertyBean> listToken = new ArrayList<>();
    public static DaoSession daoSession;
    public static DaoMaster.OpenHelper helperRemb;
    public static Database database_helperRemeb;

    //助记词相关
    public static RemembBIUTDao dao_remeb;
    public static Query<RemembBIUT> query_remeb;
    //余额相关
    public static TokenBeanDao tokenBeanDao;
    public static Query<TokenBean> queryToken;
    //交易记录相关
    public static ResultInChainBeanOrPoolDao recordResulttDao;
    public static Query<ResultInChainBeanOrPool> queryRecord;

    //设计图标注的宽度
    public static float designWidth = 720;

    public static synchronized Context getContext() {
        return mContext;
    }

    public BIUTApplication() {
    }

    @Override
    public void onCreate() {
        try {
            mContext = getApplicationContext();
            AutoLayoutConifg.getInstance().useDeviceSize();
            CrashReport.initCrashReport(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        super.onCreate();
        initScreen();
        initKind();
//        对xUtils进行初始化
        x.Ext.init(this);
        //是否是开发、调试模式
        x.Ext.setDebug(BuildConfig.DEBUG);//是否输出debug日志，开启debug会影响性能
        //初始化数据库
        initDataDao();
    }

    private void initScreen() {
        new RudenessScreenHelper(this, designWidth).activate();
    }

    public void initDataDao() {
        //初始化数据库信息
        helperRemb = new DaoMaster.DevOpenHelper(this, "remeb-db");
        database_helperRemeb = helperRemb.getWritableDb();
        daoSession = new DaoMaster(database_helperRemeb).newSession();


        //初始化数据信息--钱包
        dao_remeb = daoSession.getRemembBIUTDao();
        query_remeb = dao_remeb.queryBuilder().build();

        //初始化数据信息--余额
        tokenBeanDao = daoSession.getTokenBeanDao();
        queryToken = tokenBeanDao.queryBuilder().build();

        //初始化数据信息--交易记录
        recordResulttDao = daoSession.getResultInChainBeanOrPoolDao();
        queryRecord = recordResulttDao.queryBuilder().build();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public void initKind() {
        boolean isFirstLogin = PlatformConfig.getBoolean(Constant.SpConstant.FIRST);
        if (isFirstLogin == false) {
            PropertyBean bean = new PropertyBean();
            bean.setName(Constant.SpConstant.BIUT);
            bean.setAllName("Social Ecommerce Chain");
            bean.setTokenAddress(RequestHost.biut_url);
            bean.setChecked(true);
            listToken.add(bean);
            PlatformConfig.putList(Constant.SpConstant.KINDTOKEN, listToken);
        }
    }

    public static BIUTApplication getInstance() {
        if (instance == null) {
            synchronized (BIUTApplication.class) {
                if (instance == null) {
                    instance = new BIUTApplication();
                }
            }
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */

    public void finishActivity(Activity activity) {

        if (activity != null) {
            this.activityList.remove(activity);
            activity.finish();
        }
    }

    /**
     * 应用退出，清理所有活动
     */
    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}