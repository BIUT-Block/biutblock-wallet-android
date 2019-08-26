package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
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

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class BackupMnemonicsTwoActy extends BasicActivity {
    @BindView(R.id.tv_my_mnemonics)
    TextView tvMyMnemonics;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    private String mnemonics;
    private List<RemembBIUT> list;

    private String address;
    private Dialog dialogNoShot;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankup_mn2);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initData();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        tvRight.setVisibility(View.GONE);
        tvTheme.setText("Backup Phrase");
        dialogNoShot = DialogUtil.dialogNoShot(this, (v, d) -> d.dismiss());
        dialogNoShot.show();
        setFontStyle();
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvOne, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTwo, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvThree, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvFour, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvFive, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvMyMnemonics, 1, LATO_BOLD_TTF);
        Util.setFontType(this, btnNext, 1, LATO_BOLD_TTF);
    }

    private void initData() {
        list = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT remembBIUT : list) {
            if (remembBIUT.getAddress().equals(address)) {
                mnemonics = remembBIUT.getMnemonics();
                tvMyMnemonics.setText(mnemonics);
                break;
            }
        }
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                UiHelper.startBackupMnemonicsActy3(BackupMnemonicsTwoActy.this, mnemonics, address);
                break;
        }
    }

}