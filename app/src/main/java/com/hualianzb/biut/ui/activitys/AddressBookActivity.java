package com.hualianzb.biut.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.zxing.activity.CaptureActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.Constant;
import com.hualianzb.biut.commons.interfaces.GlobalMessageType;
import com.hualianzb.biut.models.AddressBookBean;
import com.hualianzb.biut.models.ReceiptBean;
import com.hualianzb.biut.ui.adapters.AdapterAddressBook;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.ToastUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hysd.android.platform_huanuo.base.config.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.constants.Constant.SpConstant.SWEEP;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2018/10/17
 * auther:wangtianyun
 * describe:地址簿
 */
public class AddressBookActivity extends BasicActivity {
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.tv_add_man)
    TextView tvAddMan;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_man)
    ListView lvMan;
    @BindView(R.id.rl_data)
    RelativeLayout rLData;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private List<AddressBookBean> list;
    private AdapterAddressBook adapter;
    private boolean isFromMy;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            isFromMy = bundle.getBoolean("isFromMy");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            isFromMy = savedInstanceState.getBoolean("isFromMy");
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalMessageType.MessgeCode.NOTIFYBOOKLIST:
                    getData();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        setFontStyle();
        ivBackTop.setImageDrawable(getResources().getDrawable(R.drawable.icon_close_black));
        tvTheme.setText("Address Book");
        list = new ArrayList<>();
        adapter = new AdapterAddressBook(this, handler, isFromMy);
        lvMan.setAdapter(adapter);
        tvRight.setText("Scan Code");
        if (isFromMy) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
        }

        lvMan.setOnItemClickListener((parent, view, position, id) -> {
            if (!isFromMy) {
                Intent intent = new Intent(AddressBookActivity.this, TransferActivity.class);
                intent.putExtra("address", list.get(position).getAddress());
                setResult(2, intent);
                finish();
            }
        });

    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvEmpty, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvAddMan, 1, LATO_BOLD_TTF);
    }

    private void getData() {
        list = PlatformConfig.getList(this, Constant.SpConstant.ADDRESSBOOK);
        if (null == list || list.size() <= 0) {
            llEmpty.setVisibility(View.VISIBLE);
            rLData.setVisibility(View.GONE);
        } else {
            rLData.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            list = Util.listAddressBookSort(list);
            adapter.setList(list);
        }
    }


    @OnClick({R.id.iv_back_top, R.id.tv_add_man, R.id.tv_add, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_man:
            case R.id.tv_add:
                UiHelper.startAddAddressBookActy(this);
                break;
            case R.id.iv_back_top:
                finish();
                break;
            case R.id.tv_right:
                startImageByCamera();
                break;
        }
    }

    private void startImageByCamera() {
        //拍照点击拍照无反应 20160906 wp
        if (Build.VERSION.SDK_INT >= 23) {
            //摄像头权限检测
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        5);
                return;
            } else {
                startActivityForResult(new Intent(this, CaptureActivity.class), SWEEP);
            }
        } else {
            startActivityForResult(new Intent(this, CaptureActivity.class), SWEEP);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SWEEP: //二维码扫描结果
                String result = "";
                try {
                    result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                    ReceiptBean receiptBean = JSON.parseObject(result, ReceiptBean.class);
                    String add_ss = receiptBean.getAddress();
                    if (!StringUtils.isEmpty(add_ss)) {
                        if (add_ss.length() == 40 && !add_ss.substring(0, 2).equals("0x")) {
                            add_ss = "0x" + add_ss;
                        }
                        Intent intent = new Intent(AddressBookActivity.this, TransferActivity.class);
                        intent.putExtra("address", add_ss);
                        setResult(2, intent);
                        finish();
                    } else {

                    }
                } catch (Exception e) {
                    ToastUtil.show(this, R.string.scan_error);
                }
                break;
            default:
                break;
        }
    }
}