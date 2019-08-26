package com.hualianzb.biut.ui.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.ReceiptBean;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.MoneyValueFilter;
import com.hualianzb.biut.utils.QRUtils;
import com.hualianzb.biut.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2018/8/17
 * auther:wangtianyun
 * describe:生成收款码
 */
public class MakeCodeActivity extends BasicActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_code_pic)
    ImageView ivCodePic;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    Bitmap bitmap = null;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    EditText tvMoney;
    private String address, title;
    private int type;
    private ReceiptBean receiptBean;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            type = bundle.getInt("type");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
            type = savedInstanceState.getInt("type");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makecode);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        tvAddress.setText(address);
        setFontStyle();
        if (type == 0) {
            title = "BIUT";
        } else {
            title = "BIU";
        }
        tvTitle.setText(title + " Receipt");
        tvMoney.setText("0");
        receiptBean = new ReceiptBean();
        receiptBean.setAddress(address);
        receiptBean.setType(type);
        receiptBean.setValue("0");
        setBitmap();
        tvMoney.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(8)});
        tvMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                receiptBean.setValue(s.toString());
                setBitmap();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTitle, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvAddress, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvMoney, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvCopy, 1, LATO_BOLD_TTF);
    }

    @OnClick({R.id.iv_back, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_copy:
                Util.copy(this, address);
                DialogUtil.showErrorDialog(this, "Copied");
                break;
        }
    }

    private void setBitmap() {
        bitmap = QRUtils.createQRCode(JSON.toJSONString(receiptBean), 460, 460, null);
        ivCodePic.setImageBitmap(bitmap);
    }
}
