package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.RequestHost;
import com.hualianzb.biut.commons.interfaces.GlobalMessageType;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.UpDateBean;
import com.hualianzb.biut.ui.adapters.ViewPageAdapter;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.ui.fragments.MyFragment;
import com.hualianzb.biut.ui.fragments.PoolFragment;
import com.hualianzb.biut.ui.fragments.PropertyFragment;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.Util;
import com.hualianzb.biut.views.StillViewPager;
import com.hysd.android.platform_huanuo.utils.ActivityUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;

public class HomePageActivity extends BasicActivity implements
        MyFragment.CheckUpdateLinster {
    @BindView(R.id.ll_property)
    LinearLayout llProperty;
    @BindView(R.id.ll_my)
    LinearLayout llMy;
    @BindView(R.id.viewPager)
    StillViewPager viewPager;
    @BindView(R.id.ll_pool)
    LinearLayout llPool;
    private long exitTime = 0;
    private ViewPageAdapter viewPageAdapter;
    private List listF;
    private String address;
    private List<RemembBIUT> remembBIUTList;
    private int index;
    private boolean isNetwork = true;

    private PropertyFragment propertyFragment;

    public String getAddress() {
        return address;
    }

    UpDateBean upDateBean;
    private Dialog upDateDialog;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_homepage);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        initView();
        initData();
        checkUpdate(false);
    }

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        switch (message.what) {
            case 0x0000100:
                String urlExe = (String) message.obj;
                Uri uri = Uri.parse(urlExe);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case GlobalMessageType.MainRequest.NET_ERROR:
                propertyFragment.setDataChange(false);
                break;
            case GlobalMessageType.MainRequest.NET_OK:
                propertyFragment.setDataChange(true);
                break;
        }
    }

    private static DataChange dataChange;

    public void setNet(DataChange dataChange) {
        this.dataChange = dataChange;
    }

    public interface DataChange {
        void setDataChange(boolean isNetOk);
    }

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            index = bundle.getInt("index");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
            index = savedInstanceState.getInt("index");
        }
    }

    private void initData() {
        listF = new ArrayList<>();
        remembBIUTList = new ArrayList<>();
        remembBIUTList = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT remembBIUT : remembBIUTList) {
            if (remembBIUT.getIsNow() == true) {
                address = remembBIUT.getAddress();
                break;
            }
        }
        propertyFragment = new PropertyFragment();
        listF.add(propertyFragment);
//        listF.add(new PoolFragment());
        listF.add(new MyFragment());
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), listF);
        viewPager.setAdapter(viewPageAdapter);
        viewPager.setCurrentItem(0);
//        if (index == 1000) {
//            viewPager.setCurrentItem(1);
//        }
    }

    @OnClick({R.id.ll_pool, R.id.ll_property, R.id.ll_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_property:
                viewPager.setCurrentItem(0);
                tabSelected(llProperty);
                break;
            case R.id.ll_pool:
                viewPager.setCurrentItem(1);
                tabSelected(llPool);
                break;
            case R.id.ll_my:
                viewPager.setCurrentItem(2);
                tabSelected(llMy);
                break;
            default:
                break;
        }
    }

    private void tabSelected(LinearLayout linearLayout) {
        llPool.setSelected(false);
        llProperty.setSelected(false);
        llMy.setSelected(false);
        linearLayout.setSelected(true);
        ImmersionBar.with(this).statusBarColor(R.color.gray_background).init();
        if (viewPager.getCurrentItem() == 2) {
            ImmersionBar.with(this).statusBarColor(R.color.gray_background_home).init();
        }
    }

    private void initView() {
        llPool.setVisibility(View.GONE);
        tabSelected(llProperty);
        upDateDialog = DialogUtil.upDateDialog(this);
        dataChange = isNetOk -> dataChange.setDataChange(isNetOk);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            moveTaskToBack(true);
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(HomePageActivity.this, "Press the exit procedure again ", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                BIUTApplication.getInstance().exit();
            }
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("address", address);
//        savedInstanceState.putBoolean("isBackMy", isBackMy);
        super.onSaveInstanceState(savedInstanceState);
    }

    //检查更新
    private void checkUpdate(boolean isFromMy) {
        RequestParams params = new RequestParams(RequestHost.url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtils.isEmpty(result)) {
                    upDateBean = JSON.parseObject(result, UpDateBean.class);
                    UpDateBean.AndroidBean androidBean = upDateBean.getAndroid();
                    String discrib = androidBean.getDescrib();
                    String urlExe = androidBean.getLink();
                    String version = androidBean.getVersion();
                    String updateStatus = androidBean.getStatus();

                    TextView tup = findViewById(R.id.tv_update);
                    if (Integer.parseInt(updateStatus) > 1) {
                        tup.setVisibility(View.VISIBLE);
                    } else {
                        tup.setVisibility(View.GONE);
                    }
                    //初始化布局控件
                    TextView tv_version = upDateDialog.findViewById(R.id.tv_version);
                    TextView tv_describ = upDateDialog.findViewById(R.id.tv_describ);
                    TextView tv_cancel = upDateDialog.findViewById(R.id.tv_cancel);
                    TextView tv_sure = upDateDialog.findViewById(R.id.tv_sure);

                    Util.setFontType(HomePageActivity.this, tv_sure, 1, LATO_BOLD_TTF);
                    Util.setFontType(HomePageActivity.this, tv_cancel, 1, LATO_BOLD_TTF);
                    Util.setFontType(HomePageActivity.this, tv_version, 1, LATO_REGULAR_WOFF_TTF);
                    Util.setFontType(HomePageActivity.this, tv_describ, 1, LATO_REGULAR_WOFF_TTF);

                    tv_describ.setText(discrib);
                    tv_sure.setVisibility(View.VISIBLE);
                    tv_cancel.setOnClickListener(v -> upDateDialog.dismiss());
                    tv_version.setText(version);

                    switch (updateStatus) {
                        case "1"://no upgrade
                            tv_describ.setText("no upgrade");
                            tv_cancel.setVisibility(View.GONE);
                            tv_sure.setText("Go It");
                            if (isFromMy == true) {
                                upDateDialog.show();
                            }
                            break;
                        case "2":// upgrade not compulsively
                            tv_cancel.setVisibility(View.VISIBLE);
                            tv_describ.setText(discrib);
                            tv_sure.setText("Update");
                            upDateDialog.show();
                            break;
                        case "3":// upgrade compulsively
                            if (!version.equals(ActivityUtil.getVersionName(HomePageActivity.this))) {
                                tv_cancel.setVisibility(View.GONE);
                                tv_sure.setText("Update");
                                upDateDialog.show();
                            } else {
                                tv_describ.setText("no upgrade");
                                tv_cancel.setVisibility(View.GONE);
                                tv_sure.setText("Go It");
                                if (isFromMy == true) {
                                    upDateDialog.show();
                                }
                            }
                            break;
                    }
                    tv_sure.setOnClickListener(v -> {
                        switch (updateStatus) {
                            case "1"://  no update
                                upDateDialog.dismiss();
                                break;
                            case "2":// upgrade not compulsively
                                sendMessage(0x0000100, urlExe);
                                break;
                            case "3":// upgrade compulsively
                                if (!version.equals(ActivityUtil.getVersionName(HomePageActivity.this))) {
                                    sendMessage(0x0000100, urlExe);
                                } else {
                                    upDateDialog.dismiss();
                                }
                                break;
                        }

                    });
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("web3", ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
//                Log.e("web3", "onFinished");
            }
        });
    }

    @Override
    public void check() {
        checkUpdate(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            index = bundle.getInt("index");
            if (index == 1000) {
                viewPager.setCurrentItem(0);
                tabSelected(llProperty);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            viewPager.setCurrentItem(1);
            tabSelected(llPool);
        }
    }
}

