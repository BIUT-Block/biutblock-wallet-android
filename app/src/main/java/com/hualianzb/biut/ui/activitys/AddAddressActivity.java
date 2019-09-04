package com.hualianzb.biut.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.zxing.activity.CaptureActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.Constant;
import com.hualianzb.biut.models.AddressBookBean;
import com.hualianzb.biut.models.ReceiptBean;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.TimeUtil;
import com.hualianzb.biut.utils.ToastUtil;
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
 * describe:新增地址
 */
public class AddAddressActivity extends BasicActivity {
    @BindView(R.id.iv_close_top)
    ImageView ivClose;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_remark)
    EditText edRemark;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name_error)
    TextView tvNameError;
    @BindView(R.id.tv_address_error)
    TextView tvAddressError;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    private List<AddressBookBean> list;
    private String address, name;
    private boolean isAddressOk, isNameOk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        ivBackTop.setVisibility(View.GONE);
        ivClose.setVisibility(View.VISIBLE);
        setFontStyle();
        tvTheme.setText("Address Contact");
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        list = BIUTApplication.addressBookBeanDao.loadAll();
//        list = PlatformConfig.getList(this, Constant.SpConstant.ADDRESSBOOK);
        tvRight.setVisibility(View.GONE);
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvName, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvAddress, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPhone, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvEmail, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRemark, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNameError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvAddressError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, edAddress, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edEmail, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edName, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edPhone, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edRemark, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvSave, 1, LATO_BOLD_TTF);
    }

    private void setData() {
        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString().trim();
                checkName(name);
                setBtnClickable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address = s.toString().trim();
                checkAddress(address);
                setBtnClickable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setBtnClickable() {
        if (isNameOk && isAddressOk) {
            tvSave.setEnabled(true);
            tvSave.setBackground(getResources().getDrawable(R.drawable.bg_btn));
        } else {
            tvSave.setEnabled(false);
            tvSave.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
        }
    }

    //联系人名称
    private void checkName(String name) {
        if (StringUtils.isEmpty(name)) {
            isNameOk = false;
            tvNameError.setText("Please input contact name");
            tvNameError.setVisibility(View.VISIBLE);
            return;
        } else {
            isNameOk = true;
            tvNameError.setVisibility(View.GONE);
        }
    }

    //检验地址
    private void checkAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            isAddressOk = false;
            tvAddressError.setText("Address Error");
            tvAddressError.setVisibility(View.VISIBLE);
        } else if (address.length() != 42 || !address.substring(0, 2).equals("0x")) {
            isAddressOk = false;
            tvAddressError.setText("Address Error");
            tvAddressError.setVisibility(View.VISIBLE);
        } else {
            if (null != list && list.size() > 0) {
                for (AddressBookBean nowBean : list) {
                    if (nowBean.getAddress().equals(address)) {
                        isAddressOk = false;
                        tvAddressError.setText("Address Exists");
                        tvAddressError.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        isAddressOk = true;
                        tvAddressError.setVisibility(View.GONE);
                    }
                }
            } else {
                isAddressOk = true;
                tvAddressError.setVisibility(View.GONE);
            }
        }
        if (isAddressOk) {
            tvSave.setClickable(true);
            tvSave.setBackground(getResources().getDrawable(R.drawable.bg_btn));
        } else {
            tvSave.setClickable(false);
            tvSave.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
        }

    }

    @OnClick({R.id.tv_save, R.id.rl_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                String phone = edPhone.getText().toString().trim();
                String mail = edEmail.getText().toString().trim();
                String remark = edRemark.getText().toString().trim();
                if (null != list && list.size() > 0) {
                    boolean isExit = false;
                    for (AddressBookBean nowBean : list) {
                        if (nowBean.getAddress().equals(address) && nowBean.getName().equals(name)) {
                            if (StringUtils.isEmpty(phone) && StringUtils.isEmpty(nowBean.getPhone())) {
                                tvAddressError.setText("Address Exists");
                                tvAddressError.setVisibility(View.VISIBLE);
                                isExit = true;
                                return;
                            }
                            if ((!StringUtils.isEmpty(phone) && !StringUtils.isEmpty(nowBean.getPhone())) && (phone.equals(nowBean.getPhone()))) {
                                tvAddressError.setText("Address Exists");
                                tvAddressError.setVisibility(View.VISIBLE);
                                isExit = true;
                                return;
                            }
                        }
                    }
                    if (isExit == false) {
                        AddressBookBean bean = new AddressBookBean();
                        bean.setName(name);
                        bean.setAddress(address);
                        bean.setPhone(phone);
                        bean.setEmail(mail);
                        bean.setRemarks(remark);
                        bean.setCreatTime(TimeUtil.getDate());
//                        list.add(bean);
                        BIUTApplication.addressBookBeanDao.save(bean);
//                        PlatformConfig.putList(Constant.SpConstant.ADDRESSBOOK, list);
                        finish();
                    }

                } else {
                    AddressBookBean bean = new AddressBookBean();
                    bean.setName(name);
                    bean.setAddress(address);
                    bean.setPhone(phone);
                    bean.setEmail(mail);
                    bean.setRemarks(remark);
                    bean.setCreatTime(TimeUtil.getDate());
                    BIUTApplication.addressBookBeanDao.save(bean);
//                    list = new ArrayList<>();
//                    list.add(bean);
//                    PlatformConfig.putList(Constant.SpConstant.ADDRESSBOOK, list);
                    finish();
                }
                break;
            case R.id.rl_scan:
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //二维码扫描结果
        if (requestCode == SWEEP) {
            String result = "";
            try {
                result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            } catch (Exception e) {
//                ToastUtil.show(this, R.string.scan_error);
                return;
            }
            if (!StringUtils.isEmpty(result)) {
                ReceiptBean bean = JSON.parseObject(result, ReceiptBean.class);
                if (null != bean) {
                    String myGetAdress = bean.getAddress();
                    if (myGetAdress.length() == 40 && !myGetAdress.substring(0, 2).equals("0x")) {
                        myGetAdress = "0x" + myGetAdress;
                    }
                    edAddress.setText(myGetAdress);
                    checkAddress(myGetAdress);
                    setBtnClickable();
                }
            }
        } else {
            ToastUtil.show(this, "please retry");
        }
    }
}
