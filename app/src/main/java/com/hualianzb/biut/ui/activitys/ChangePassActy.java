package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.ClickUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_LIGHT_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class ChangePassActy extends BasicActivity {
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ed_old_pass)
    EditText edOldPass;
    @BindView(R.id.ed_new_pass)
    EditText edNewPass;
    @BindView(R.id.ed_re_pass)
    EditText edRePass;
    @BindView(R.id.ll_import)
    LinearLayout llImport;
    @BindView(R.id.ed_pass_tips)
    EditText edPassTips;
    @BindView(R.id.tv_pass_error)
    TextView tvPassError;
    @BindView(R.id.tv_new_pass_error)
    TextView tvNewPassError;
    @BindView(R.id.tv_re_pass_error)
    TextView tvRePassError;
    @BindView(R.id.rl_pass)
    RelativeLayout rlPass;
    @BindView(R.id.rl_new_pass)
    RelativeLayout rlNewPass;
    @BindView(R.id.rl_re_pass)
    RelativeLayout rlRePass;
    @BindView(R.id.tv_tip1)
    TextView tvTip1;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.iv_clear1)
    ImageView ivClear1;
    @BindView(R.id.iv_clear2)
    ImageView ivClear2;
    @BindView(R.id.iv_clear3)
    ImageView ivClear3;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.iv_yellow)
    ImageView ivYellow;
    @BindView(R.id.iv_blue)
    ImageView ivBlue;
    @BindView(R.id.ll_pass_reminder)
    LinearLayout llPassReminder;
    @BindView(R.id.rl_level)
    RelativeLayout rlLevel;
    @BindView(R.id.tv_c_pass)
    TextView tvCPass;
    @BindView(R.id.tv_new_pass)
    TextView tvNewPass;
    @BindView(R.id.tv_re_pass)
    TextView tvRePass;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_now)
    TextView tvNow;
    private RemembBIUT remembBIUT;
    private String address;
    private List<RemembBIUT> list;
    private boolean isOldPassOk, isNewPassOk, isRepassOk;
    private String oldPass, newPass, rePass;
    int passLevel = 0;
    private String strTips = "Import phrase or private key to " + " <font color='#388ED8'>reset the password</font>" + ".";

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
        }
    }

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvCPass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNewPass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRePass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPrompt, 1, LATO_BOLD_TTF);
        Util.setFontType(this, edOldPass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edNewPass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edRePass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edPassTips, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvPassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNewPassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRePassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTip1, 1, LATO_LIGHT_WOFF_TTF);
        Util.setFontType(this, tvTips, 1, LATO_LIGHT_WOFF_TTF);
        Util.setFontType(this, tvNow, 1, LATO_REGULAR_WOFF_TTF);
    }

    private void initView() {
        setFontStyle();
        tvTheme.setText("Change Password");
        tvRight.setText("Complete");
        tvTips.setText(Html.fromHtml(strTips));
        tvTip1.setText("Forget password?");
        tvTips.setText(Html.fromHtml(strTips));
        list = new ArrayList<>();
        list = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT rememb : list) {
            if (rememb.getAddress().equals(address)) {
                remembBIUT = rememb;
                break;
            }
        }
        edOldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldPass = s.toString().trim();
                if (s.toString().contains(" ")) {
                    String[] str = oldPass.split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    edOldPass.setText(str1);
                    edOldPass.setSelection(start);
                }
                if (!StringUtils.isEmpty(oldPass)) {
                    ivClear1.setVisibility(View.VISIBLE);
                } else {
                    ivClear1.setVisibility(View.GONE);
                }
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPass = s.toString().trim();
                if (s.toString().contains(" ")) {
                    String[] str = newPass.split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    edNewPass.setText(str1);
                    edNewPass.setSelection(start);
                }
                if (!StringUtils.isEmpty(newPass)) {
                    ivClear2.setVisibility(View.VISIBLE);
                } else {
                    ivClear2.setVisibility(View.GONE);
                }
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rePass = s.toString().trim();
                if (s.toString().contains(" ")) {//去除空格键
                    String[] str = rePass.split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    edRePass.setText(str1);
                    edRePass.setSelection(start);
                }
                if (!StringUtils.isEmpty(rePass)) {
                    ivClear3.setVisibility(View.VISIBLE);
                } else {
                    ivClear3.setVisibility(View.GONE);
                }
//                checkRePass(rePass);
//                setBtnClickable();
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void check() {
        String oldPass = edOldPass.getText().toString().trim();
        checkOldPass(oldPass);
        if (isOldPassOk) {
            String newPass = edNewPass.getText().toString().trim();
            checkPassword(newPass);
            checkPassStrength(newPass);
            if (isNewPassOk) {
                String rePass = edRePass.getText().toString().trim();
                checkRePass(rePass);
            }
        }
        setBtnClickable();
    }


    //检测密码强度
    private void checkPassStrength(String password) {
        if (StringUtils.isEmpty(password)) {
            rlLevel.setVisibility(View.GONE);
        } else {
            passLevel = StringUtils.getPassLevale(password);
            if (passLevel == 0 && password.length() < 8) {
                rlLevel.setVisibility(View.GONE);
                return;
            }
            if (passLevel == 2 && password.length() >= 8) {
                llPassReminder.setVisibility(View.VISIBLE);
                rlLevel.setVisibility(View.VISIBLE);
                ivBlue.setVisibility(View.GONE);
                ivYellow.setVisibility(View.GONE);
                return;
            }
            if (passLevel == 3 && password.length() >= 8 && password.length() <= 12) {
                rlLevel.setVisibility(View.VISIBLE);
                ivYellow.setVisibility(View.VISIBLE);
                llPassReminder.setVisibility(View.VISIBLE);
                ivBlue.setVisibility(View.GONE);
                return;
            }
            if (passLevel == 3 && password.length() >= 12) {
                llPassReminder.setVisibility(View.VISIBLE);
                ivYellow.setVisibility(View.VISIBLE);
                ivBlue.setVisibility(View.VISIBLE);
                rlLevel.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    //原密码是否正确
    private void checkOldPass(String password) {
        if (StringUtils.isEmpty(password)) {
            isOldPassOk = false;
            tvPassError.setTextColor(getResources().getColor(R.color.text_error));
            tvPassError.setVisibility(View.VISIBLE);
            tvPassError.setText("Please Input Password");
            return;
        } else {
            if (!password.equals(remembBIUT.getPass())) {
                isOldPassOk = false;
                tvPassError.setTextColor(getResources().getColor(R.color.text_error));
                tvPassError.setVisibility(View.VISIBLE);
                tvPassError.setText("Password Error");
                return;
            } else {
                tvPassError.setVisibility(View.GONE);
                isOldPassOk = true;
            }
        }
    }


    //检查新密码的合法性
    private void checkPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            isNewPassOk = false;
            llPassReminder.setVisibility(View.VISIBLE);
            tvNewPassError.setText("Please Input Password");
            tvNewPassError.setVisibility(View.VISIBLE);
            rlLevel.setVisibility(View.GONE);
            return;
        } else if (password.length() < 8) {
            isNewPassOk = false;
            llPassReminder.setVisibility(View.VISIBLE);
            tvNewPassError.setText(getString(R.string.format_error));
            tvNewPassError.setVisibility(View.VISIBLE);
            rlLevel.setVisibility(View.GONE);
            return;
        } else {
            String regEx4 = getString(R.string.patters_all);
            rlLevel.setVisibility(View.VISIBLE);
            isNewPassOk = password.matches(regEx4);
            if (isNewPassOk) {
                tvPassError.setVisibility(View.GONE);
                tvNewPassError.setVisibility(View.GONE);
            } else {
                llPassReminder.setVisibility(View.VISIBLE);
                tvNewPassError.setVisibility(View.VISIBLE);
                tvNewPassError.setText(getString(R.string.format_error));
                tvNewPassError.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    //重复密码是否正确
    private void checkRePass(String password) {
        if (StringUtils.isEmpty(password)) {
            isRepassOk = false;
            tvRePassError.setText("Please Repeat Password");
            tvRePassError.setVisibility(View.VISIBLE);
            return;
        } else {
            tvRePassError.setVisibility(View.GONE);
            if (!password.equals(newPass)) {
                isRepassOk = false;
                llPassReminder.setVisibility(View.VISIBLE);
                tvNewPassError.setText("Two passwords are inconsistent");
                tvNewPassError.setVisibility(View.VISIBLE);
                return;
            } else {
                isRepassOk = true;
            }
        }
    }

    private void setBtnClickable() {
        if (isOldPassOk && isNewPassOk && isRepassOk) {
            tvRight.setEnabled(true);
        } else {
            tvRight.setEnabled(false);
        }
    }

    @OnClick({R.id.tv_right, R.id.ll_import, R.id.iv_clear1, R.id.iv_clear2, R.id.iv_clear3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                //避免连续点击
                if (ClickUtil.isNotFirstClick()) {
                    return;
                }
                if (StringUtils.isEmpty(edOldPass.getText().toString().trim())) {
                    return;
                }
                if (StringUtils.isEmpty(edNewPass.getText().toString().trim())) {
                    return;
                }
                if (StringUtils.isEmpty(edRePass.getText().toString().trim())) {
                    return;
                }
                String tip = edPassTips.getText().toString().trim();
                if (!StringUtils.isEmpty(tip)) {
                    remembBIUT.setTips(tip);
                }
                remembBIUT.setPass(rePass);
                BIUTApplication.dao_remeb.update(remembBIUT);
                finish();
                break;
            case R.id.ll_import:
                UiHelper.startActyImportWalletActivity(this);
                break;
            case R.id.iv_clear1:
                edOldPass.setText("");
                break;
            case R.id.iv_clear2:
                edNewPass.setText("");
                break;
            case R.id.iv_clear3:
                edRePass.setText("");
                break;
        }
    }
}
