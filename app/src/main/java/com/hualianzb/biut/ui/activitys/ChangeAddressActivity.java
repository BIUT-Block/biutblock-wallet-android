package com.hualianzb.biut.ui.activitys;

/**
 * Date:2018/11/2
 * auther:wangtianyun
 * describe:
 */

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.Constant;
import com.hualianzb.biut.models.AddressBookBean;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.TimeUtil;
import com.hualianzb.biut.utils.ToastUtil;
import com.hysd.android.platform_huanuo.base.config.PlatformConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.constants.Constant.SpConstant.SWEEP;

/**
 * Date:2018/10/17
 * auther:wangtianyun
 * describe:编辑地址
 */
public class ChangeAddressActivity extends BasicActivity {
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
    @BindView(R.id.rl_scan)
    RelativeLayout rl_scan;
    private List<AddressBookBean> list;
    private AddressBookBean addressBookBean;
    private long bookIndex;
    private String name,
            phone,
            mail,
            remark,
            address;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            bookIndex = bundle.getLong("bookIndex");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            bookIndex = savedInstanceState.getLong("bookIndex");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        tvRight.setVisibility(View.GONE);
//        list = PlatformConfig.getList(this, Constant.SpConstant.ADDRESSBOOK);
        list = BIUTApplication.addressBookBeanDao.loadAll();
        ivBackTop.setImageDrawable(getResources().getDrawable(R.drawable.icon_close_black));
        tvSave.setText("Save");
        edAddress.setEnabled(false);
        rl_scan.setVisibility(View.GONE);
    }

    private void setData() {
        addressBookBean = BIUTApplication.addressBookBeanDao.load(bookIndex);
        edName.setHint(addressBookBean.getName());
        edAddress.setHint(addressBookBean.getAddress());
        if (!StringUtils.isEmpty(addressBookBean.getPhone())) {
            edPhone.setHint(addressBookBean.getPhone());
        }
        if (!StringUtils.isEmpty(addressBookBean.getEmail())) {
            edEmail.setHint(addressBookBean.getEmail());
        }
        if (!StringUtils.isEmpty(addressBookBean.getRemarks())) {
            edRemark.setHint(addressBookBean.getRemarks());
        }
        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString().trim();
                if (!StringUtils.isEmpty(name)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (name.equals(list.get(i).getName())) {
                            tvSave.setEnabled(false);
                            tvSave.setBackgroundResource(R.drawable.bg_btn_cannot);
                        } else {
                            tvSave.setEnabled(true);
                            tvSave.setBackgroundResource(R.drawable.bg_btn);
                        }
                    }
                } else {
                    tvSave.setEnabled(false);
                    tvSave.setBackgroundResource(R.drawable.bg_btn_cannot);
                }
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

    @OnClick({R.id.tv_save, R.id.rl_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                name = edName.getText().toString().trim();
                phone = edPhone.getText().toString().trim();
                mail = edEmail.getText().toString().trim();
                remark = edRemark.getText().toString().trim();
                address = addressBookBean.getAddress();

//                AddressBookBean bean = new AddressBookBean();
                addressBookBean.setName(name);
                addressBookBean.setAddress(address);
                addressBookBean.setPhone(StringUtils.isEmpty(phone) == true ? addressBookBean.getPhone() : phone);
                addressBookBean.setEmail(StringUtils.isEmpty(mail) == true ? addressBookBean.getEmail() : mail);
                addressBookBean.setRemarks(StringUtils.isEmpty(remark) == true ? addressBookBean.getRemarks() : remark);
                addressBookBean.setCreatTime(TimeUtil.getDate());
                addressBookBean.setId(addressBookBean.getId());
//                int oldIndex = list.indexOf(addressBookBean);
//                list.set(oldIndex, bean);
                BIUTApplication.addressBookBeanDao.update(addressBookBean);
//                PlatformConfig.putList(Constant.SpConstant.ADDRESSBOOK, list);
                finish();
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
                return;
            }
            if (!StringUtils.isEmpty(result)) {
                String[] resultArray = result.split("###");
                if (resultArray != null && resultArray.length > 0) {
                    String myGetAdress = resultArray[0];
                    edAddress.setText(myGetAdress);
                }
            } else {
                DialogUtil.showErrorDialog(this, getString(R.string.scan_error));
            }
        }
    }
}
