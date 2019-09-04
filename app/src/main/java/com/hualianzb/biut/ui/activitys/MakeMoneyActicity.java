package com.hualianzb.biut.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.biutUtil.SECBlockJavascriptAPI;
import com.hualianzb.biut.models.BiutBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.ui.fragments.ExportKeystoreAttentionDialogFragment;
import com.hualianzb.biut.ui.fragments.ExportKeystoreDialogFragment;
import com.hualianzb.biut.ui.fragments.ExportPrivatekeyDialogFragment;
import com.hualianzb.biut.ui.fragments.QrCodeDialogFragment;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class MakeMoneyActicity extends BasicActivity implements QrCodeDialogFragment.InfacnCopy
        , ExportKeystoreAttentionDialogFragment.ExportAttenion, ExportKeystoreDialogFragment.ExportLinster
        , ExportPrivatekeyDialogFragment.ExporPrivateKeytLinster {
    @BindView(R.id.tv_wallet_name)
    TextView tvTitle;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_biut)
    TextView tvBiut;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_qr_code)
    TextView ivQrCode;
    @BindView(R.id.ed_wname)
    EditText edWname;
    @BindView(R.id.ll_changepass)
    LinearLayout llChangepass;
    @BindView(R.id.ll_out_privatekey)
    LinearLayout llOutPrivatekey;
    @BindView(R.id.ll_out_keystore)
    LinearLayout llOutKeystore;
    @BindView(R.id.btn_delete)
    TextView btn_delete;
    @BindView(R.id.iv_issee)
    ImageView ivIssee;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.btn_backups)
    TextView btnBackups;
    @BindView(R.id.tv_name_error)
    TextView tvNameError;
    @BindView(R.id.re_prompt)
    RelativeLayout rePrompt;
    @BindView(R.id.tv_name02)
    TextView tvName02;
    private boolean canSee = false;

    private List<RemembBIUT> list;

    private String address, checkedAddress;
    private String money;
    private RemembBIUT bean, checkedReme;
    private boolean isWallteNameOk;
    private String walletName,
            keyStore;
    private long walletId;
    SECBlockJavascriptAPI secJsApi = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_money);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.gray_background).init();
        try {
            secJsApi = new SECBlockJavascriptAPI(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        initView();
        initData();
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
            money = savedInstanceState.getString("money");
        }
    }

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            money = bundle.getString("money");
        }
    }

    private void initView() {
        setFontStyle();
        tvBiut.setText(Util.getStringFromSN(8, money) + "BIUT");
        list = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT remembBIUT : list) {
            if (remembBIUT.getAddress().equals(address)) {
                bean = remembBIUT;
                break;
            }
        }
        walletId = bean.getId();
        tvTitle.setText(bean.getWalletName());
        tvTitle.setTextColor(getResources().getColor(R.color.text_black));
        tvAddress.setText(bean.getAddress());
        checkCanSee();
        edPass.setHint(bean.getTips());
        ivBackTop.setImageResource(R.drawable.icon_back);
        edWname.setHint(bean.getWalletName());

        if (StringUtils.isEmpty(bean.getMnemonics())) {
            btnBackups.setVisibility(View.GONE);
        } else {
            btnBackups.setVisibility(View.VISIBLE);
        }
        if (StringUtils.isEmpty(bean.getTips())) {
            rePrompt.setVisibility(View.GONE);
        } else {
            rePrompt.setVisibility(View.VISIBLE);
        }
    }

    private void setFontStyle() {
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, ivQrCode, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTitle, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvName02, 1, LATO_BOLD_TTF);
        Util.setFontType(this, btnBackups, 1, LATO_BOLD_TTF);
        Util.setFontType(this, btn_delete, 1, LATO_BOLD_TTF);
    }

    @OnClick({R.id.tv_right, R.id.fl_see, R.id.iv_qr_code, R.id.ll_changepass, R.id.ll_out_privatekey, R.id.ll_out_keystore, R.id.btn_backups, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                walletName = edWname.getText().toString().trim();
                if (StringUtils.isEmpty(walletName)) {
                    DialogUtil.showErrorDialog(this, "Wallet name cannot be empty");
                } else {
                    bean.setWalletName(walletName);
                    BIUTApplication.dao_remeb.update(bean);
                    tvTitle.setText(walletName);
                    DialogUtil.showErrorDialog(this, "Saved");
                }
                break;
            case R.id.iv_qr_code:
                QrCodeDialogFragment qrCodeDialogFragment = new QrCodeDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                qrCodeDialogFragment.setArguments(bundle);//数据传递到fragment中
                FragmentManager fragmentManager = getSupportFragmentManager();
                qrCodeDialogFragment.show(fragmentManager, QrCodeDialogFragment.class.getSimpleName());
                break;
            case R.id.ll_changepass:
                UiHelper.startChangePassActy(this, address);
                break;
            case R.id.ll_out_privatekey:
                bean = BIUTApplication.dao_remeb.load(walletId);
                UiHelper.startCheckPassActivity(this, bean.getPass(), 002);
                break;
            case R.id.ll_out_keystore:
                bean = BIUTApplication.dao_remeb.load(walletId);
                UiHelper.startCheckPassActivity(this, bean.getPass(), 003);
                break;
            case R.id.btn_delete:
                bean = BIUTApplication.dao_remeb.load(walletId);
                UiHelper.startCheckPassActivity(this, bean.getPass(), 005);
                break;
            case R.id.fl_see:
                if (canSee) {
                    canSee = false;
                } else {
                    canSee = true;
                }
                checkCanSee();
                break;
            case R.id.btn_backups:
                bean = BIUTApplication.dao_remeb.load(walletId);
                UiHelper.startCheckPassActivity(this, bean.getPass(), 004);
                break;
        }
    }

    private void IntentMove() {
        BIUTApplication.dao_remeb.deleteByKey(walletId);
        list = BIUTApplication.dao_remeb.loadAll();
        if (list.size() == 0) {
            UiHelper.startActyCreateInsertWallet(this);
            finish();
        } else {
            if (bean.getIsNow()) {
                RemembBIUT firstBean = list.get(0);
                firstBean.setIsNow(true);
                BIUTApplication.dao_remeb.update(firstBean);
                finish();
            } else {
                finish();
            }
        }
    }

    //检测钱包名称的合法性
    private void checkWalletName(String walletName) {
        if (null == list || list.size() == 0) {
            if (StringUtils.isEmpty(walletName)) {//名称为空
                isWallteNameOk = false;
                tvNameError.setVisibility(View.VISIBLE);
                tvNameError.setText(getString(R.string.wallet_name_null));
                return;
            } else {
                isWallteNameOk = true;
                tvNameError.setVisibility(View.GONE);
                return;
            }
        } else {
            if (StringUtils.isEmpty(walletName)) {//名称为空
                isWallteNameOk = false;
                tvNameError.setText(getString(R.string.wallet_name_null));
                tvNameError.setVisibility(View.VISIBLE);
                return;
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
                    tvNameError.setText(getString(R.string.wallet_exists));
                    tvNameError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    isWallteNameOk = true;
                    tvNameError.setVisibility(View.GONE);
                    return;
                }

            }
        }
    }

    private void setClickeble() {
        if (isWallteNameOk) {
            tvRight.setEnabled(true);
        } else {
            tvRight.setEnabled(false);
        }
    }

    private void checkCanSee() {
        if (canSee) {
            //如果选中，显示密码提示
            edPass.setText(bean.getTips());
            ivIssee.setImageResource(R.drawable.icon_eye_open);
        } else {
            //否则隐藏密码
            edPass.setText("******");
            ivIssee.setImageResource(R.drawable.icon_eye_close);
        }
    }

    public ImmersionBar getActivityImmersionBar() {
        return ImmersionBar.with(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        edWname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                walletName = s.toString().trim();
                checkWalletName(walletName);
                setClickeble();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //传过来的地址，在宿主Activity中操作
    @Override
    public void copy(String str) {
        Util.copy(this, str);
        DialogUtil.showErrorDialog(this, "Address has been copied");
    }

    @Override
    public void goIt() {
        ExportKeystoreDialogFragment exportKeystoreDialogFragment = new ExportKeystoreDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address", address);
        bundle.putString("keyStore", keyStore);
        exportKeystoreDialogFragment.setArguments(bundle);//数据传递到fragment中
        FragmentManager fragmentManager = getSupportFragmentManager();
        exportKeystoreDialogFragment.show(fragmentManager, ExportKeystoreDialogFragment.class.getSimpleName());
    }

    //复制keystore
    @Override
    public void copyKeyStore(String str) {
        if ("1".equals(str)) {
            Util.copy(this, keyStore);
            DialogUtil.showErrorDialog(this, "Keystore has been copied");
        }
    }

    //复制私钥
    @Override
    public void copyPrivatekey(String str) {
        if ("1".equals(str)) {
            Util.copy(this, bean.getPrivateKey());
            DialogUtil.showErrorDialog(this, "Private key has been copied");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 002://私钥
                if (resultCode == 1) {
                    if (null != data) {
                        boolean isCheckedPass = data.getExtras().getBoolean("isCheckedPass");
                        if (isCheckedPass) {
                            ExportPrivatekeyDialogFragment privatekeyDialogFragment = new ExportPrivatekeyDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("address", address);
                            privatekeyDialogFragment.setArguments(bundle);//数据传递到fragment中
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            privatekeyDialogFragment.show(fragmentManager, ExportPrivatekeyDialogFragment.class.getSimpleName());
                        }
                    }
                }
                break;
            case 003://keyStore
                if (resultCode == 1) {
                    if (null != data) {
                        boolean isCheckedPass = data.getExtras().getBoolean("isCheckedPass");
                        if (isCheckedPass) {
                            ExportKeystoreAttentionDialogFragment exportAttentionDialogFragment = new ExportKeystoreAttentionDialogFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            BiutBean biutBean = new BiutBean();
                            biutBean.setPrivateKey(bean.getPrivateKey());
                            biutBean.setPublicKey(bean.getPublicKey());
                            String addd = bean.getPrivateKey();
                            String zhujici = secJsApi.EntropyToMnemonic(addd);
                            biutBean.setEnglishWords(zhujici);
                            biutBean.setUserAddress(bean.getAddress().substring(2));
                            keyStore = "{{\"" + bean.getPrivateKey() + "\": " + JSON.toJSONString(biutBean) + "}";
                            keyStore = secJsApi.encryptKeystore(keyStore, bean.getPass());
                            exportAttentionDialogFragment.show(fragmentManager, ExportKeystoreAttentionDialogFragment.class.getSimpleName());
                        }
                    }
                }
                break;
            case 004://助记词
                if (resultCode == 1) {
                    if (null != data) {
                        boolean isCheckedPass = data.getExtras().getBoolean("isCheckedPass");
                        if (isCheckedPass) {
                            UiHelper.startBackupMnemonicsActy2(MakeMoneyActicity.this, address);
                        }
                    }
                }
                break;
            case 005://删除钱包
                if (resultCode == 1) {
                    if (null != data) {
                        boolean isCheckedPass = data.getExtras().getBoolean("isCheckedPass");
                        if (isCheckedPass) {
                            IntentMove();
                        }
                    }
                }
                break;
            default:
                break;
        }

    }
}