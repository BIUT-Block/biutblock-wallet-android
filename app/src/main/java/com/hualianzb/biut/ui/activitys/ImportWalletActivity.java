package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.biutUtil.SECBlockJavascriptAPI;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.ClickUtil;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.TimeUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.application.BIUTApplication.dao_remeb;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BLACK_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUM_PFB_TTF;

public class ImportWalletActivity extends BasicActivity {

    @BindView(R.id.tv_mn)
    TextView tvMn;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.re_mn)
    RelativeLayout reMn;
    @BindView(R.id.tv_officialW)
    TextView tvOfficialW;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.rl_official_w)
    RelativeLayout rlOfficialW;
    @BindView(R.id.tv_private_key)
    TextView tvPrivateKey;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.rl_private_key)
    RelativeLayout rlPrivateKey;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ed_top)
    EditText edTop;
    @BindView(R.id.tv_official_tips)
    TextView tvOfficialTips;
    @BindView(R.id.tv_edit_top_error)
    TextView tvEditTopError;
    @BindView(R.id.ll_middle)
    LinearLayout llMiddle;
    @BindView(R.id.tv_pass_error)
    TextView tvPassError;
    @BindView(R.id.tv_re_pass)
    TextView tvRePass;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.tv_re_pass_error)
    TextView tvRePassError;
    @BindView(R.id.tv_mumber_error)
    TextView tv_mumber_error;
    @BindView(R.id.ed_repass)
    EditText edRepass;
    @BindView(R.id.ed_pass_reminder)
    EditText edPassReminder;
    @BindView(R.id.cb_agree)
    ImageView cbAgree;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_start_import)
    TextView tvStartImport;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.rl_re_pass)
    RelativeLayout rlRePass;
    @BindView(R.id.ll_prompt)
    LinearLayout llPrompt;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.iv_yellow)
    ImageView ivYellow;
    @BindView(R.id.iv_blue)
    ImageView ivBlue;
    @BindView(R.id.iv_clear1)
    ImageView ivClear1;
    @BindView(R.id.iv_clear2)
    ImageView ivClear2;
    @BindView(R.id.rl_level)
    RelativeLayout rlLevel;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    private int index = 0;
    private int passLevel = 0;
    private String topToString, mnemonics, officials, privatekeys, pass, rePass, tips;
    private boolean isNameOk = true;//钱包名称不参与判断，默认为真
    private boolean isMnOk, isOfficialOk, isPrivatekeyOk, isPassOk, isRePassOk;
    private List<RemembBIUT> list;
    private Dialog dialogLoading, errorPasaDialog;
    private int imgIcon;
    private RemembBIUT beCheckedBiut, nowBiutBean;
    private String walletAddress = null;
    private WalletFile walletFile = null;
    private boolean canGo = true;
    private String initWalletName = "New Import";
    private boolean isWallteNumberOk = true;//钱包数量限制10个
    SECBlockJavascriptAPI secJsApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_wallet);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        tvTheme.setText("Import Wallet");
        dialogLoading = DialogUtil.showLoadingDialog(this, getString(R.string.importing));
        errorPasaDialog = DialogUtil.TipsDialog(this, R.drawable.error_icon, "Input Password",
                0, "Invalid Password.\nPlease check and try again.", (v, d) -> d.dismiss());
        try {
            secJsApi = new SECBlockJavascriptAPI(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        initView(index);
        setFontStyle();
        initData();
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvRight, 1, LATO_BLACK_PFB_TTF);
        Util.setFontType(this, tvMn, 1, MONTSERRAT_MEDIUM_PFB_TTF);
        Util.setFontType(this, tvOfficialW, 1, MONTSERRAT_MEDIUM_PFB_TTF);
        Util.setFontType(this, tvPrivateKey, 1, MONTSERRAT_MEDIUM_PFB_TTF);
        Util.setFontType(this, edTop, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvPass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRePass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPrompt, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvEditTopError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRePassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tv_mumber_error, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRePassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, edPass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edRepass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edPassReminder, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvStartImport, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvOfficialTips, 1, LATO_REGULAR_WOFF_TTF);
    }

    private void initData() {

        list = dao_remeb.loadAll();
        if (null == list || list.size() == 0) {
            list = new ArrayList<>();
        } else {
            if (list.size() == 10) {
                isWallteNumberOk = false;
                tv_mumber_error.setVisibility(View.VISIBLE);
            } else {
                isWallteNumberOk = true;
                tv_mumber_error.setVisibility(View.GONE);
            }
            for (RemembBIUT bean : list) {
                if (bean.getIsNow()) {
                    nowBiutBean = bean;
                }
            }
        }

        edTop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                topToString = s.toString().trim();
                checkEditeString(topToString);
                setBtnClickable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass = s.toString().trim();
                if (s.toString().contains(" ")) {
                    String[] str = pass.split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    edPass.setText(str1);
                    edPass.setSelection(start);
                }
                if (index != 1) {
                    checkPassStrength(pass);
                }
                if (StringUtils.isEmpty(pass)) {
                    ivClear1.setVisibility(View.GONE);
                    tvPassError.setText("Input Password");
                    tvPassError.setVisibility(View.VISIBLE);
                    rlLevel.setVisibility(View.GONE);
                } else {
                    ivClear1.setVisibility(View.VISIBLE);
                }
                checkPassword(pass);
                setBtnClickable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edRepass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rePass = s.toString().trim();
                if (s.toString().contains(" ")) {
                    String[] str = rePass.split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    edRepass.setText(str1);
                    edRepass.setSelection(start);
                }
                if (!StringUtils.isEmpty(rePass)) {
                    ivClear2.setVisibility(View.VISIBLE);
                    tvRePassError.setText("");
                } else {
                    ivClear2.setVisibility(View.GONE);
                }
                if (isPassOk) {
                    checkRePassword(rePass);
                    setBtnClickable();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkEditeString(String topToString) {
        switch (index) {
            case 0:
                mnemonics = topToString;
                if (StringUtils.isEmpty(mnemonics)) {
                    isMnOk = false;
                    tvEditTopError.setText("Input Phrase");
                    tvEditTopError.setVisibility(View.VISIBLE);
                } else {
                    String[] arr = mnemonics.split("\\s+");
                    if (arr.length != 24) {
                        isMnOk = false;
                        tvEditTopError.setText("Phrase Error");
                        tvEditTopError.setVisibility(View.VISIBLE);
                    } else {
                        tvEditTopError.setVisibility(View.GONE);
                        isMnOk = true;
                    }
                }
                break;
            case 1:
                officials = topToString;
                if (StringUtils.isEmpty(officials)) {
                    isOfficialOk = false;
                    tvEditTopError.setText("Keystore type error");
                    tvEditTopError.setVisibility(View.VISIBLE);
                }
//                else if (officials.length() != 728) {
//                    isOfficialOk = false;
//                    tvEditTopError.setText("Keystore type error");
//                    tvEditTopError.setVisibility(View.VISIBLE);
//                }
                else {
                    isOfficialOk = true;
                    tvEditTopError.setVisibility(View.GONE);
                }
                break;
            case 2:
                privatekeys = topToString;
                if (StringUtils.isEmpty(privatekeys) || privatekeys.length() != 64) {
                    isPrivatekeyOk = false;
                    tvEditTopError.setText("PrivateKey type error");
                    tvEditTopError.setVisibility(View.VISIBLE);
                } else {
                    isPrivatekeyOk = true;
                    tvEditTopError.setVisibility(View.GONE);
                }
                break;
        }
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
                rlLevel.setVisibility(View.VISIBLE);
                ivBlue.setVisibility(View.GONE);
                ivYellow.setVisibility(View.GONE);
                return;
            }
            if (passLevel == 3 && password.length() >= 8 && password.length() <= 12) {
                rlLevel.setVisibility(View.VISIBLE);
                ivYellow.setVisibility(View.VISIBLE);
                ivBlue.setVisibility(View.GONE);
                return;
            }
            if (passLevel == 3 && password.length() >= 12) {
                rlLevel.setVisibility(View.VISIBLE);
                ivYellow.setVisibility(View.VISIBLE);
                ivBlue.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    //检查密码的合法性
    private void checkPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            isPassOk = false;
            tvPassError.setText("Please Input Password");
            tvPassError.setVisibility(View.VISIBLE);
            return;
        } else if (password.length() < 8) {
            isPassOk = false;
            tvPassError.setText(getString(R.string.format_error));
            tvPassError.setVisibility(View.VISIBLE);
            return;
        } else {
            String regEx4 = getString(R.string.patters_all);
            isPassOk = password.matches(regEx4);
            if (isPassOk) {
                tvPassError.setVisibility(View.GONE);
            } else {
                tvPassError.setText(getString(R.string.format_error));
                tvPassError.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    //检查重复密码的合法性
    private void checkRePassword(String repassword) {
        if (StringUtils.isEmpty(repassword)) {
            isRePassOk = false;
            tvRePassError.setText("Please Repeat Password");
            tvRePassError.setVisibility(View.VISIBLE);
            return;
        } else {
            tvRePassError.setVisibility(View.GONE);
            if (!repassword.equals(edPass.getText().toString().trim())) {
                isRePassOk = false;
                tvRePassError.setText("Two passwords are inconsistent");
                tvRePassError.setVisibility(View.VISIBLE);
                return;
            } else {
                isRePassOk = true;
                tvRePassError.setVisibility(View.GONE);
            }
        }
    }

    private void setBtnClickable() {
        if (isWallteNumberOk == false) {
            tvStartImport.setEnabled(false);
            tvStartImport.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
        } else {
            switch (index) {
                case 0:
                    if (isNameOk && isMnOk && isPassOk && isRePassOk) {
                        tvStartImport.setEnabled(true);
                        tvStartImport.setBackground(getResources().getDrawable(R.drawable.bg_btn));
                    } else {
                        tvStartImport.setEnabled(false);
                        tvStartImport.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                    }
                    break;
                case 1:
                    if (isPassOk && isOfficialOk) {
                        tvStartImport.setEnabled(true);
                        tvStartImport.setBackground(getResources().getDrawable(R.drawable.bg_btn));
                    } else {
                        tvStartImport.setEnabled(false);
                        tvStartImport.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                    }
                    break;
                case 2:
                    if (isNameOk && isPrivatekeyOk && isPassOk && isRePassOk) {
                        tvStartImport.setEnabled(true);
                        tvStartImport.setBackground(getResources().getDrawable(R.drawable.bg_btn));
                    } else {
                        tvStartImport.setEnabled(false);
                        tvStartImport.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.re_mn, R.id.rl_official_w, R.id.rl_private_key, R.id.tv_right, R.id.tv_agreement, R.id.iv_clear1, R.id.iv_clear2, R.id.tv_start_import})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_mn:
                index = 0;
                initView(index);
                break;
            case R.id.rl_official_w:
                index = 1;
                initView(index);
                break;
            case R.id.rl_private_key:
                index = 2;
                initView(index);
                break;
            case R.id.tv_right:
                switch (index) {
                    case 0:
                        UiHelper.startIntroduceMnActy(this);
                        break;
                    case 1:
                        UiHelper.startIntroduceKeystoreActy(this);
                        break;
                    case 2:
                        UiHelper.startIntroducePKActy(this);
                        break;
                }
                break;
            case R.id.tv_agreement:
                break;
            case R.id.tv_start_import:
                //避免连续点击
                if (ClickUtil.isNotFirstClick()) {
                    return;
                }
                RemembBIUT newBiut = new RemembBIUT();
                switch (index) {
                    case 0:
                        dialogLoading.show();
                        imgIcon = (int) (Math.random() * 5);
                        String mnenic = edTop.getText().toString().trim();
                        String privateKey = null;
                        newBiut = new RemembBIUT();
                        try {
                            privateKey = "0x" + secJsApi.MnemonicToEntropy(mnenic);
                            checkExistPrivateKey(privateKey.substring(2));
                            if (canGo) {
                                walletAddress = "0x" + secJsApi.PrivKeytoAddress(privateKey.substring(2));
                                newBiut.setPrivateKey(privateKey.substring(2));
                                newBiut.setMnemonics("");
                                newBiut.setAddress(walletAddress);
                                newBiut.setWalletincon(imgIcon);
                                newBiut.setPass(rePass);
                                newBiut.setIsNow(true);
                                newBiut.setWalletName(initWalletName);
                                newBiut.setCreatTime(TimeUtil.getDate());
                                newBiut.setHowToCreate(2);
                                newBiut.setPublicKey(getPublicKey(privateKey.substring(2)).substring(2));
                                tips = edPassReminder.getText().toString().trim();
                                if (!StringUtils.isEmpty(tips)) {
                                    newBiut.setTips(tips);
                                }
                                if (list != null && list.size() > 0) {
                                    nowBiutBean.setIsNow(false);
                                    dao_remeb.update(nowBiutBean);
                                    dao_remeb.insert(newBiut);
                                } else {
                                    list = new ArrayList<>();
                                    dao_remeb.insert(newBiut);
                                }
                                dialogLoading.dismiss();
                                UiHelper.startHomaPageAcB(ImportWalletActivity.this, walletAddress, 1000);
                                finish();
                            } else {
                                dialogLoading.dismiss();
                                DialogUtil.twoButtonTipsDialog(this, R.drawable.icon_confire_blue,
                                        "Are you sure",
                                        getString(R.string.wallet_exist), (v, d) -> {
                                            beCheckedBiut.setWalletName(initWalletName);
                                            beCheckedBiut.setPass(rePass);
                                            beCheckedBiut.setIsNow(true);
                                            if (!StringUtils.isEmpty(edPassReminder.getText().toString().trim())) {
                                                beCheckedBiut.setTips(edPassReminder.getText().toString().trim());
                                            } else {
                                                beCheckedBiut.setTips("");
                                            }
                                            if (index == 0) {
                                                beCheckedBiut.setHowToCreate(2);
                                            }
                                            if (index == 2) {
                                                beCheckedBiut.setHowToCreate(3);
                                            }
                                            nowBiutBean.setIsNow(false);
                                            dao_remeb.update(nowBiutBean);
                                            dao_remeb.update(beCheckedBiut);
                                            UiHelper.startHomaPageAcB(ImportWalletActivity.this, beCheckedBiut.getAddress(), 1000);
                                            finish();
                                            d.dismiss();
                                        }).show();
                            }
                        } catch (Exception e) {
                            dialogLoading.dismiss();
                            DialogUtil.showErrorDialog(this, "Invalid phrase");
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        dialogLoading.show();
                        newBiut = new RemembBIUT();
                        String pass = edPass.getText().toString();
                        String jsonOffical = null;
                        try {
                            String secretString = edTop.getText().toString().trim();
                            jsonOffical = secJsApi.decryptKeystore(secretString, pass);
                            if (StringUtils.isEmpty(jsonOffical)) {
                                dialogLoading.dismiss();
                                DialogUtil.showErrorDialog(this, "Keystore or password is wrong");
                                return;
                            } else {
                                int left = jsonOffical.indexOf(": {");
                                if (left == -1) {
                                    left = jsonOffical.indexOf(":{");
                                }
                                if (left != -1) {
                                    left = left + 1;
                                    jsonOffical = jsonOffical.substring(left, (jsonOffical.length() - 1));
                                    JSONObject object = JSON.parseObject(jsonOffical);
                                    String prik = object.getString("privateKey");
                                    String publicKey = object.getString("publicKey");
                                    checkExistPrivateKey(prik);
                                    walletAddress = "0x" + secJsApi.PrivKeytoAddress(prik);
                                    newBiut = new RemembBIUT();
                                    if (canGo) {
                                        newBiut.setPrivateKey(prik);
                                        newBiut.setMnemonics("");
                                        newBiut.setAddress(walletAddress);
                                        newBiut.setWalletincon(imgIcon);
                                        newBiut.setPass(pass);
                                        newBiut.setIsNow(true);
                                        newBiut.setWalletName(initWalletName);
                                        newBiut.setCreatTime(TimeUtil.getDate());
                                        newBiut.setHowToCreate(4);
                                        tips = edPassReminder.getText().toString().trim();
                                        newBiut.setPublicKey(publicKey);
                                        if (!StringUtils.isEmpty(tips)) {
                                            newBiut.setTips(tips);
                                        }
                                        if (list != null && list.size() > 0) {
                                            nowBiutBean.setIsNow(false);
                                            dao_remeb.update(nowBiutBean);
                                            dao_remeb.insert(newBiut);
                                        } else {
                                            list = new ArrayList<>();
                                            dao_remeb.insert(newBiut);
                                        }
                                        dialogLoading.dismiss();
                                        UiHelper.startHomaPageAcB(ImportWalletActivity.this, walletAddress, 1000);
                                        finish();
                                    } else {
                                        dialogLoading.dismiss();
                                        DialogUtil.twoButtonTipsDialog(this, R.drawable.icon_confire_blue,
                                                "Are you sure",
                                                getString(R.string.wallet_exist), (v, d) -> {
                                                    beCheckedBiut.setWalletName(initWalletName);
                                                    beCheckedBiut.setPass(edPass.getText().toString().trim());
                                                    beCheckedBiut.setIsNow(true);
                                                    beCheckedBiut.setHowToCreate(4);
                                                    beCheckedBiut.setPublicKey(publicKey);
                                                    beCheckedBiut.setPrivateKey(prik);
                                                    beCheckedBiut.setMnemonics("");
                                                    beCheckedBiut.setIsNow(false);
                                                    if (null != nowBiutBean) {
                                                        dao_remeb.update(nowBiutBean);
                                                    }
                                                    dao_remeb.update(beCheckedBiut);
                                                    UiHelper.startHomaPageAcB(ImportWalletActivity.this, walletAddress, 1000);
                                                    finish();
                                                    d.dismiss();
                                                }).show();
                                    }
                                } else {
                                    dialogLoading.dismiss();
                                    DialogUtil.showErrorDialog(this, "Keystore or password is wrong");
                                }
                            }

                        } catch (Exception e) {
                            dialogLoading.dismiss();
                            Log.e("web3", e.toString());
                            DialogUtil.showErrorDialog(this, "Password error");
                        }
                        break;
                    case 2:
                        imgIcon = (int) (Math.random() * 5);
                        RemembBIUT newBiut2 = new RemembBIUT();
                        String getPrivateKey = edTop.getText().toString();
                        try {
                            checkExistPrivateKey(getPrivateKey);
                            if (canGo) {
                                walletAddress = "0x" + secJsApi.PrivKeytoAddress(getPrivateKey);
                                newBiut2.setPrivateKey(getPrivateKey);
                                newBiut2.setMnemonics("");
                                newBiut2.setAddress(walletAddress);
                                newBiut2.setWalletincon(imgIcon);
                                newBiut2.setPass(rePass);
                                newBiut2.setIsNow(true);
                                newBiut2.setWalletName(initWalletName);
                                newBiut2.setCreatTime(TimeUtil.getDate());
                                newBiut2.setHowToCreate(3);
                                tips = edPassReminder.getText().toString().trim();
                                newBiut.setPublicKey(getPublicKey(getPrivateKey).substring(2));
                                if (!StringUtils.isEmpty(tips)) {
                                    newBiut2.setTips(tips);
                                }
                                if (list != null && list.size() > 0) {
                                    nowBiutBean.setIsNow(false);
                                    dao_remeb.update(nowBiutBean);
                                    dao_remeb.insert(newBiut2);
                                } else {
                                    list = new ArrayList<>();
                                    dao_remeb.insert(newBiut2);
                                }
                                dialogLoading.dismiss();
                                UiHelper.startHomaPageAcB(ImportWalletActivity.this, walletAddress, 1000);
                                finish();
                            } else {
                                dialogLoading.dismiss();
                                DialogUtil.twoButtonTipsDialog(this, R.drawable.icon_confire_blue,
                                        "Are you sure",
                                        getString(R.string.wallet_exist), (v, d) -> {
                                            beCheckedBiut.setWalletName(initWalletName);
                                            beCheckedBiut.setPass(rePass);
                                            beCheckedBiut.setIsNow(true);
                                            if (!StringUtils.isEmpty(edPassReminder.getText().toString().trim())) {
                                                beCheckedBiut.setTips(edPassReminder.getText().toString().trim());
                                            } else {
                                                beCheckedBiut.setTips("");
                                            }
                                            beCheckedBiut.setHowToCreate(3);
                                            nowBiutBean.setIsNow(false);
                                            dao_remeb.update(nowBiutBean);
                                            dao_remeb.update(beCheckedBiut);
                                            UiHelper.startHomaPageAcB(ImportWalletActivity.this, beCheckedBiut.getAddress(), 1000);
                                            finish();
                                            d.dismiss();
                                        }).show();
                            }
                        } catch (Exception e) {
                            dialogLoading.dismiss();
                            Log.e("web3", e.toString());
                            DialogUtil.showErrorDialog(this, "password error");
                            e.printStackTrace();
                        }
                }
                break;
            case R.id.iv_clear1:
                edPass.setText("");
                rlLevel.setVisibility(View.GONE);
                break;
            case R.id.iv_clear2:
                edRepass.setText("");
                break;
        }
    }

    private void initView(int index) {
        tvMn.setTextColor(getResources().getColor(R.color.text_gray));
        TextPaint tpMn = tvMn.getPaint();
        tpMn.setFakeBoldText(false);
        line1.setVisibility(View.GONE);

        tvOfficialW.setTextColor(getResources().getColor(R.color.text_gray));
        TextPaint tpoFF = tvOfficialW.getPaint();
        tpoFF.setFakeBoldText(false);
        line2.setVisibility(View.GONE);

        tvPrivateKey.setTextColor(getResources().getColor(R.color.text_gray));
        TextPaint tpoPk = tvPrivateKey.getPaint();
        tpoPk.setFakeBoldText(false);
        line3.setVisibility(View.GONE);

        tvOfficialTips.setVisibility(View.GONE);
        tvEditTopError.setText("");
        tvEditTopError.setVisibility(View.GONE);
        switch (index) {
            case 0:
                tvRight.setText("What is phrase?");
                tvMn.setTextColor(getResources().getColor(R.color.text_green_increase));
                line1.setVisibility(View.VISIBLE);
                tpMn.setFakeBoldText(true);
                llMiddle.setVisibility(View.VISIBLE);
                edTop.setHint("Please separate phrases by space");
                rlRePass.setVisibility(View.VISIBLE);
                llPrompt.setVisibility(View.VISIBLE);
                goneSomeView();
                break;
            case 1:
                tvRight.setText("What is a keystore?");
                tvOfficialW.setTextColor(getResources().getColor(R.color.text_green_increase));
                line2.setVisibility(View.VISIBLE);
                tpoFF.setFakeBoldText(true);
                tvOfficialTips.setVisibility(View.VISIBLE);
                edTop.setHint(R.string.import_keystore_hint);
                llMiddle.setVisibility(View.VISIBLE);
                goneSomeView();
                rlRePass.setVisibility(View.GONE);
                llPrompt.setVisibility(View.GONE);
                break;
            case 2:
                tvRight.setText("What is a private key?");
                tvPrivateKey.setTextColor(getResources().getColor(R.color.text_green_increase));
                line3.setVisibility(View.VISIBLE);
                tpoPk.setFakeBoldText(true);
                llMiddle.setVisibility(View.VISIBLE);
                rlRePass.setVisibility(View.VISIBLE);
                llPrompt.setVisibility(View.VISIBLE);
                edTop.setHint(R.string.import_privateKey_hint);
                goneSomeView();
                break;
        }
    }


    //获得ekeypair以后就可以根据私钥检测钱包是否已经存在了
    private void checkExistPrivateKey(String privateKey) {
        boolean isExist = false;//是否已经存在
        if (list != null && list.size() > 0) {
            for (RemembBIUT rememb : list) {
                if (rememb.getPrivateKey().equals(privateKey)) {
                    isExist = true;//存在了
                    beCheckedBiut = rememb;
                    break;
                }
            }

        }
        if (isExist) {
            canGo = false;
        } else {
            canGo = true;
        }
    }

    //获得ekeypair以后就可以根据私钥检测钱包是否已经存在了
    private void checkExistpublicKey(String publicKey) {
        boolean isExist = false;//是否已经存在
        RemembBIUT existBiutBean = null;

        if (null != list && list.size() > 0) {
            for (RemembBIUT rememb : list) {
                if (rememb.getPublicKey().equals(publicKey)) {
                    isExist = true;//存在了
                    existBiutBean = rememb;
                    break;
                }
            }

        }
        if (isExist) {
            dialogLoading.dismiss();
            RemembBIUT finalExistBiutBean = existBiutBean;
            DialogUtil.twoButtonTipsDialog(this, R.drawable.icon_confire_blue,
                    "Are you sure",
                    getString(R.string.wallet_exist), (v, d) -> {
                        finalExistBiutBean.setWalletName(initWalletName);
                        finalExistBiutBean.setPass(rePass);
                        finalExistBiutBean.setIsNow(true);
                        finalExistBiutBean.setCreatTime(finalExistBiutBean.getCreatTime());
                        if (!StringUtils.isEmpty(edPassReminder.getText().toString().trim())) {
                            finalExistBiutBean.setTips(edPassReminder.getText().toString().trim());
                        } else {
                            finalExistBiutBean.setTips("");
                        }
                        finalExistBiutBean.setHowToCreate(4);
                        dao_remeb.update(finalExistBiutBean);
                        UiHelper.startHomaPageAcB(ImportWalletActivity.this, finalExistBiutBean.getAddress(), 1000);
                        finish();
                        d.dismiss();
                    }).show();
        }
    }

    private void goneSomeView() {
        edTop.setText("");
        edPass.setText("");
        edRepass.setText("");
        edPassReminder.setText("");
        tvEditTopError.setVisibility(View.GONE);
        tvPassError.setVisibility(View.GONE);
        tvRePassError.setVisibility(View.GONE);
        edPass.setBackgroundResource(R.drawable.bg_edit_gray);
        edRepass.setBackgroundResource(R.drawable.bg_edit_gray);
    }

    private String getPublicKey(String privateKey) {
        Credentials credentials = Credentials.create(privateKey);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        return Numeric.encodeQuantity(ecKeyPair.getPublicKey());
    }
}
