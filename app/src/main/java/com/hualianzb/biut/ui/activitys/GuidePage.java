package com.hualianzb.biut.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.utils.UiHelper;

import java.util.List;

public class GuidePage extends Activity {
    public final int MSG_FINISH_LAUNCHERACTIVITY = 500;
    private List<RemembBIUT> list;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISH_LAUNCHERACTIVITY:
                    //跳转到MainActivity，并结束当前的LauncherActivity
                    initData();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        list = BIUTApplication.query_remeb.list();
        // 停留3秒后发送消息，跳转到MainActivity
        mHandler.sendEmptyMessageDelayed(MSG_FINISH_LAUNCHERACTIVITY, 2000);
    }

    private void initData() {
        if (list == null || list.size() == 0) {
            UiHelper.startActyCreateInsertWallet(this);
        } else {
            for (RemembBIUT remembBIUT : list) {
                if (remembBIUT.getIsNow()) {//查找选定的钱包
                    UiHelper.startHomaPageAc(this, remembBIUT.getAddress());
                    break;
                }
            }
        }
    }
}
