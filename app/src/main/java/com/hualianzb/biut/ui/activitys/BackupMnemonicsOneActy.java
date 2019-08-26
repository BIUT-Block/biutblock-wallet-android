package com.hualianzb.biut.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.application.BIUTApplication.dao_remeb;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_LIGHT_WOFF_TTF;

/**
 * 备份钱包
 */
public class BackupMnemonicsOneActy extends BasicActivity {
    @BindView(R.id.tv_third)
    TextView tvThird;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.first)
    TextView tvFirst;
    @BindView(R.id.second)
    TextView second;
    @BindView(R.id.four)
    TextView four;
    @BindView(R.id.five)
    TextView five;
    @BindView(R.id.btn_bank_up)
    TextView btnBankUp;
    private RemembBIUT remembBIUT;
    private String getAddress;
    List<RemembBIUT> list;

    @Override

    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            getAddress = bundle.getString("address");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            getAddress = savedInstanceState.getString("address");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankup_mn);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        String str = "Backup wallet,export phrases and  copy them to a " + " <font color='#388EDA'>safe place</font>" + ".";
        tvThird.setText(Html.fromHtml(str));
        tvRight.setText("Skip");
        tvTheme.setText("Backup Wallet");
        ivBackTop.setVisibility(View.GONE);
        setFontStyle();
        initData();
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvFirst, 1, MONTSERRAT_LIGHT_WOFF_TTF);
        Util.setFontType(this, second, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvThird, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, four, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, five, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, btnBankUp, 1, LATO_BOLD_TTF);
    }

    private void initData() {
        list = dao_remeb.loadAll();
        for (RemembBIUT myBean : list) {
            if (myBean.getAddress().equals(getAddress)) {
                remembBIUT = myBean;
                break;
            }
        }
    }

    @OnClick({R.id.btn_bank_up, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bank_up:
                if (isNetworkAvailable(this)) {
                    UiHelper.startCheckPassActivity(this, remembBIUT.getPass(), 006);
                } else {
                    DialogUtil.showErrorDialog(this, "net work error");
                }
                break;
            case R.id.tv_right:
                UiHelper.startHomaPageAcB(this, getAddress, 1000);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 006://
                if (resultCode == 1) {
                    if (null != data) {
                        boolean isCheckedPass = data.getExtras().getBoolean("isCheckedPass");
                        if (isCheckedPass) {
                            UiHelper.startBackupMnemonicsActy2(this, getAddress);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
