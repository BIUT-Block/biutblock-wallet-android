package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2019/1/10
 * auther:wangtianyun
 * describe:验证密码类
 */
public class CheckPassActivity extends BasicActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.iv_clear1)
    ImageView ivClear1;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    private String pass, input;
    private int requestCode;//001 转账界面
    private Dialog errorPasaDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpass);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        initView();
    }

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            pass = bundle.getString("pass");
            requestCode = bundle.getInt("requestCode");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            requestCode = savedInstanceState.getInt("requestCode");
            pass = savedInstanceState.getString("pass");
        }
    }

    private void initView() {
        ivBackTop.setVisibility(View.GONE);
        tvRight.setText(" Cancel ");
        setFontStyle();
        errorPasaDialog = DialogUtil.TipsDialog(this, R.drawable.error_icon, "Input Password",
                0, "Invalid Password.\nPlease check and try again.", (v, d) -> d.dismiss());
        edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input = s.toString().trim();
                if (s.toString().contains(" ")) {
                    String[] str = input.split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    edPass.setText(str1);
                    edPass.setSelection(start);
                }
                if (!StringUtils.isEmpty(input)) {
                    ivClear1.setVisibility(View.VISIBLE);
                    if (input.length() < 8) {
                        tvSure.setClickable(false);
                        tvSure.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                    } else {
                        tvSure.setClickable(true);
                        tvSure.setBackground(getResources().getDrawable(R.drawable.bg_btn));
                    }
                } else {
                    ivClear1.setVisibility(View.GONE);
                    tvSure.setClickable(false);
                    tvSure.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, edPass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvSure, 1, LATO_BOLD_TTF);
    }

    @OnClick({R.id.tv_sure, R.id.tv_right, R.id.iv_clear1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                if (!StringUtils.isEmpty(input)) {
                    if (!input.equals(pass)) {
                        errorPasaDialog.show();
                    } else {
                        Intent intent = null;
                        switch (requestCode) {
                            case 001://转账校验
                                intent = new Intent(this, TransferActivity.class);
                                intent.putExtra("isCheckedPass", true);
                                setResult(1, intent);
                                finish();
                                break;
                            case 002://导出私钥
                                intent = new Intent(this, MakeMoneyActicity.class);
                                intent.putExtra("isCheckedPass", true);
                                setResult(1, intent);
                                finish();
                                break;
                            case 003://导出Keystore
                                intent = new Intent(this, MakeMoneyActicity.class);
                                intent.putExtra("isCheckedPass", true);
                                setResult(1, intent);
                                finish();
                                break;
                            case 004://备注助记词
                                intent = new Intent(this, MakeMoneyActicity.class);
                                intent.putExtra("isCheckedPass", true);
                                setResult(1, intent);
                                finish();
                                break;
                            case 005://删除钱包
                                intent = new Intent(this, MakeMoneyActicity.class);
                                intent.putExtra("isCheckedPass", true);
                                setResult(1, intent);
                                finish();
                                break;
                            case 006://备注助记词第一个页面
                                intent = new Intent(this, BackupMnemonicsOneActy.class);
                                intent.putExtra("isCheckedPass", true);
                                setResult(1, intent);
                                finish();
                                break;
                        }
                    }
                }
                break;
            case R.id.iv_clear1:
                edPass.setText("");
                tvSure.setClickable(false);
                tvSure.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                break;
            case R.id.tv_right:
                finish();
                break;
        }
    }
}
