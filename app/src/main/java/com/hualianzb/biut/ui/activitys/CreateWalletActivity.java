package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.biutUtil.SECBlockJavascriptAPI;
import com.hualianzb.biut.commons.interfaces.GlobalMessageType;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.RemembBIUTDao;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.ClickUtil;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.TimeUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class CreateWalletActivity extends BasicActivity {

    @BindView(R.id.ed_wname)
    EditText edWname;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.ed_repass)
    EditText edRepass;
    @BindView(R.id.ed_pass_reminder)
    EditText edPassReminder;
    @BindView(R.id.cb_agree)
    ImageView cbAgree;
    @BindView(R.id.ll_clear1)
    LinearLayout llClear1;
    @BindView(R.id.ll_clear2)
    LinearLayout llClear2;
    @BindView(R.id.btn_create_new)
    TextView btnCreate;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_error)
    TextView tvNameError;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    @BindView(R.id.tv_pass_error)
    TextView tvPassError;
    @BindView(R.id.tv_re_pass)
    TextView tvRePass;
    @BindView(R.id.ll_edPass)
    LinearLayout llEdPass;
    @BindView(R.id.ll_rePass)
    LinearLayout llRePass;
    @BindView(R.id.tv_mumber_error)
    TextView tvMumbErroe;
    @BindView(R.id.tv_re_pass_error)
    TextView tvRePassError;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.iv_yellow)
    ImageView ivYellow;
    @BindView(R.id.iv_blue)
    ImageView ivBlue;
    @BindView(R.id.rl_level)
    RelativeLayout rlLevel;
    @BindView(R.id.tv_illustration)
    TextView tvIllustration;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.ll_clear_name)
    LinearLayout llClearName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    private boolean iChecked = true;//功能暂时去掉，默认改成true，不影响功能
    private boolean isWallteNumberOk = true;//钱包数量限制10个
    private String walletName, pass, rePass, remind;
    private boolean isWallteNameOk, isPassOk, isRePassOk;
    //钱包地址
    private String walletAddress = null;
    private int imgIcon;//0-5之间的随机整数
    private String mnemonics;
    private ECKeyPair ecKeyPair;
    private WalletFile walletFile = null;
    private String tips;
    private Dialog dialogLoading, TipsDialog;


    private RemembBIUTDao dao_remeb;
    List<RemembBIUT> list;
    private RemembBIUT rememb;
    private int passLevel = 0;

    @Override
    protected void initLogics() {
        super.initLogics();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);

        dao_remeb = BIUTApplication.dao_remeb;
        list = dao_remeb.loadAll();
        tvRight.setText("Import Wallet");
        tvTheme.setText("Create Wallet");

        dialogLoading = DialogUtil.showLoadingDialog(this, getString(R.string.creating));
        setFontStyle();
        rememb = new RemembBIUT();
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
        } else {
            if (list.size() == 10) {
                isWallteNumberOk = false;
                tvMumbErroe.setVisibility(View.VISIBLE);
            } else {
                isWallteNumberOk = true;
                tvMumbErroe.setVisibility(View.GONE);
            }
        }

        edWname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                walletName = s.toString().trim();
                if (StringUtils.isEmpty(walletName)) {
                    llClearName.setVisibility(View.GONE);
                    isWallteNameOk = false;
                } else {
                    llClearName.setVisibility(View.VISIBLE);
                }
                check();
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
                if (StringUtils.isEmpty(pass)) {
                    llClear1.setVisibility(View.GONE);
                    tvPassError.setText("Input Password");
                    tvPassError.setVisibility(View.VISIBLE);
                    rlLevel.setVisibility(View.GONE);
                    isPassOk = false;
                } else {
                    llClear1.setVisibility(View.VISIBLE);
                }
                check();
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
                    llClear2.setVisibility(View.VISIBLE);
                    tvRePassError.setText("");
                } else {
                    llClear2.setVisibility(View.GONE);
                    isRePassOk = false;
                }
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvIllustration, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvName, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRePass, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPrompt, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNameError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvMumbErroe, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRePassError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, btnCreate, 1, LATO_BOLD_TTF);
        Util.setFontType(this, edWname, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edPass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edRepass, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edPassReminder, 1, LATO_REGULAR_WOFF_TTF);
    }

    //检测钱包名称的合法性
    private void checkWalletName(String walletName) {
        if (StringUtils.isEmpty(walletName)) {//名称为空
            isWallteNameOk = false;
            llName.setBackgroundResource(R.drawable.bg_edit_error);
            edWname.setBackgroundResource(R.drawable.bg_edit_error);
            tvNameError.setText(getString(R.string.wallet_name_null));
            tvNameError.setVisibility(View.VISIBLE);
            return;
        } else {
            if (null == list || list.size() == 0) {
                if (StringUtils.isEmpty(walletName)) {//名称为空
                    isWallteNameOk = false;
                    llName.setBackgroundResource(R.drawable.bg_edit_error);
                    edWname.setBackgroundResource(R.drawable.bg_edit_error);
                    tvNameError.setVisibility(View.VISIBLE);
                    tvNameError.setText(getString(R.string.wallet_name_null));
                    return;
                } else {
                    isWallteNameOk = true;
                    llName.setBackgroundResource(R.drawable.bg_edit_gray);
                    edWname.setBackgroundResource(R.drawable.bg_edit_gray);
                    tvNameError.setVisibility(View.GONE);
                    return;
                }
            } else {
                boolean hasRe = false;//是否有重名的
                for (RemembBIUT remembBIUT : list) {
                    if (walletName.equals(remembBIUT.getWalletName())) {
                        hasRe = true;
                        break;
                    }
                }
                if (hasRe) {//有重名的
                    isWallteNameOk = false;
                    llName.setBackgroundResource(R.drawable.bg_edit_error);
                    edWname.setBackgroundResource(R.drawable.bg_edit_error);
                    tvNameError.setText(getString(R.string.wallet_exists));
                    tvNameError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    isWallteNameOk = true;
                    llName.setBackgroundResource(R.drawable.bg_edit_gray);
                    edWname.setBackgroundResource(R.drawable.bg_edit_gray);
                    tvNameError.setVisibility(View.GONE);
                    return;
                }
            }
        }
    }

    //检测密码强度
    private void checkPassStrength(String password) {
        if (!isPassOk) {
            rlLevel.setVisibility(View.GONE);
            return;
        } else {
            rlLevel.setVisibility(View.VISIBLE);
        }
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

    //检查密码的合法性
    private void checkPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            isPassOk = false;
            tvPassError.setText(getString(R.string.input_password));
            return;
        } else if (password.length() < 8) {
            isPassOk = false;
            llEdPass.setBackgroundResource(R.drawable.bg_edit_error);
            tvPassError.setText(getString(R.string.format_error));
            tvPassError.setVisibility(View.VISIBLE);
            return;
        } else {
            String regEx4 = getString(R.string.patters_all);
            isPassOk = password.matches(regEx4);
            if (isPassOk) {
                llEdPass.setBackgroundResource(R.drawable.bg_edit_gray);
                tvPassError.setVisibility(View.GONE);
            } else {
                llEdPass.setBackgroundResource(R.drawable.bg_edit_error);
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
            tvRePassError.setText("Repeat The Password");
            tvRePassError.setVisibility(View.VISIBLE);
            llRePass.setBackgroundResource(R.drawable.bg_edit_error);
        } else {
            if (!repassword.equals(pass)) {
                isRePassOk = false;
                tvPassError.setText("Two passwords are inconsistent");
                tvPassError.setVisibility(View.VISIBLE);
                llRePass.setBackgroundResource(R.drawable.bg_edit_error);
                return;
            } else {
                isRePassOk = true;
                llRePass.setBackgroundResource(R.drawable.bg_edit_gray);
                tvRePassError.setVisibility(View.GONE);
                tvPassError.setVisibility(View.GONE);
            }
        }
    }

    private void check() {
        checkWalletName(edWname.getText().toString().trim());
        if (!isWallteNameOk) {
            llName.setBackgroundResource(R.drawable.bg_edit_error);
            edWname.setBackgroundResource(R.drawable.bg_edit_error);
            tvPassError.setVisibility(View.GONE);
            llEdPass.setBackgroundResource(R.drawable.bg_edit_gray);
            tvRePassError.setVisibility(View.GONE);
            llRePass.setBackgroundResource(R.drawable.bg_edit_gray);
            btnCreate.setEnabled(false);
            btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
            btnCreate.setEnabled(false);
            btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
            return;
        } else {
            llName.setBackgroundResource(R.drawable.bg_edit_gray);
            edWname.setBackgroundResource(R.drawable.bg_edit_gray);
            tvNameError.setVisibility(View.GONE);
            tvPassError.setVisibility(View.GONE);
            llEdPass.setBackgroundResource(R.drawable.bg_edit_gray);
            if (!StringUtils.isEmpty(edPass.getText().toString().trim())) {
                checkPassword(edPass.getText().toString().trim());
                if (!isPassOk) {
                    tvPassError.setVisibility(View.VISIBLE);
                    llEdPass.setBackgroundResource(R.drawable.bg_edit_error);
                    btnCreate.setEnabled(false);
                    btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                } else {
                    checkPassStrength(edPass.getText().toString().trim());
                    tvPassError.setVisibility(View.GONE);
                    llEdPass.setBackgroundResource(R.drawable.bg_edit_gray);
                    tvRePassError.setVisibility(View.GONE);
                    llRePass.setBackgroundResource(R.drawable.bg_edit_gray);
                    if (!StringUtils.isEmpty(edRepass.getText().toString().trim())) {
                        checkRePassword(edRepass.getText().toString().trim());
                        if (!isRePassOk) {
                            tvRePassError.setVisibility(View.VISIBLE);
                            llRePass.setBackgroundResource(R.drawable.bg_edit_error);
                            btnCreate.setEnabled(false);
                            btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                        } else {
                            tvRePassError.setVisibility(View.GONE);
                            llRePass.setBackgroundResource(R.drawable.bg_edit_gray);
                        }
                    } else {
                        btnCreate.setEnabled(false);
                        btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                    }
                }
            } else {
                btnCreate.setEnabled(false);
                btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
            }
        }
        setBtnClickable();
    }


    private void setBtnClickable() {
        if (isWallteNumberOk && isPassOk && isRePassOk && isWallteNumberOk) {
            btnCreate.setEnabled(true);
            btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn));
        } else {
            btnCreate.setEnabled(false);
            btnCreate.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
        }
    }

    @OnClick({R.id.btn_create_new, R.id.tv_right, R.id.cb_agree, R.id.tv_agreement, R.id.ll_clear1, R.id.ll_clear2, R.id.ll_clear_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_new:
                //避免连续点击
                if (ClickUtil.isNotFirstClick()) {
                    return;
                }
                create();
                break;
            case R.id.tv_right:
                UiHelper.startActyImportWalletActivity(this);
                break;
            case R.id.cb_agree:
                if (iChecked) {
                    iChecked = false;
                    cbAgree.setBackground(getResources().getDrawable(R.drawable.cb_agree_no));
                } else {
                    iChecked = true;
                    cbAgree.setBackground(getResources().getDrawable(R.drawable.cb_agree_yes));
                }
                setBtnClickable();
                break;
            case R.id.tv_agreement:
                break;
            case R.id.ll_clear_name:
                edWname.setText("");
                check();
                break;
            case R.id.ll_clear1:
                edPass.setText("");
                tvPassError.setText("Input Password");
                check();
                rlLevel.setVisibility(View.GONE);
                break;
            case R.id.ll_clear2:
                edRepass.setText("");
                tvRePassError.setText("Repeat The Password");
                check();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    //产生没有重复单词的助记词
    boolean hasRepeat = false;

    private boolean checkRepeatMn(String mnemonics) {
        String[] strings = mnemonics.split("\\s+");
        hasRepeat = StringUtils.cheakIsRepeat(strings);
        return hasRepeat;
    }

    private void create() {
        try {
            ecKeyPair = Keys.createEcKeyPair();
            String privateKey = Numeric.encodeQuantity(ecKeyPair.getPrivateKey());
            String publicKey = Numeric.encodeQuantity(ecKeyPair.getPublicKey());
            SECBlockJavascriptAPI secJsApi = null;
            secJsApi = new SECBlockJavascriptAPI(getApplicationContext());
            mnemonics = secJsApi.EntropyToMnemonic(privateKey.substring(2));
            checkRepeatMn(mnemonics);
            if (!hasRepeat) {
                create();
            } else {
                walletAddress = "0x" + secJsApi.PrivKeytoAddress(privateKey.substring(2));
                remind = edPassReminder.getText().toString().trim();
                dialogLoading.show();
                imgIcon = (int) (Math.random() * 5);
                rememb.setWalletName(walletName);
                rememb.setPass(edPass.getText().toString().trim());
                rememb.setTips(edPassReminder.getText().toString().trim());
                rememb.setMnemonics(mnemonics);
                rememb.setAddress(walletAddress);
                rememb.setPrivateKey(privateKey.substring(2));
                rememb.setPublicKey(publicKey.substring(2));
                setOtherWalletFalse();//如果有选中的钱包，置为不选中
                rememb.setIsNow(true);//把当前钱包置为选中
                rememb.setWalletincon(imgIcon);
                rememb.setHowToCreate(1);
                rememb.setCreatTime(TimeUtil.getDate());
                dao_remeb.save(rememb);
                dialogLoading.dismiss();
                TipsDialog = DialogUtil.TipsDialog(CreateWalletActivity.this, R.drawable.icon_success_green, "Create Wallet",
                        1, "Wallet create successfully", (v, d) -> {
                            UiHelper.startBackupMnemonicsActy1(CreateWalletActivity.this, walletAddress);
                            d.dismiss();
                            finish();
                        });
                TipsDialog.show();
            }
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case GlobalMessageType.RequestCode.FromCreate:
                    iChecked = data.getBooleanExtra("iChecked", false);
                    setEnable(iChecked);
                    break;
            }
        }
    }

    private void setEnable(boolean isOK) {
        if (isOK) {
            cbAgree.setBackground(getResources().getDrawable(R.drawable.cb_agree_yes));
        } else {
            cbAgree.setBackground(getResources().getDrawable(R.drawable.cb_agree_no));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setOtherWalletFalse() {

        if (null != list && list.size() > 0) {
            for (RemembBIUT rememb : list) {
                if (rememb.getIsNow() == true) {
                    rememb.setIsNow(false);
                    dao_remeb.update(rememb);
                    break;
                }
            }
        }
    }

}
