package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2018/9/26
 * auther:wangtianyun
 * describe:什么是keystore
 */
public class IntroduceKeystoreAcy extends BasicActivity {
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_content1)
    TextView tvContent1;
    @BindView(R.id.tv_content2)
    TextView tvContent2;
    @BindView(R.id.tv_content3)
    TextView tvContent3;
    @BindView(R.id.tv_content4)
    TextView tvContent4;
    @BindView(R.id.tv_content5)
    TextView tvContent5;
    @BindView(R.id.tv_content6)
    TextView tvContent6;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_tip_content)
    TextView tvTipContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_keystore);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        tvRight.setVisibility(View.GONE);
        setFontStyle();
        tvTheme.setText("What is a keystore?");
        tvContent1.setText(getString(R.string.keystore_1));
        tvContent2.setText(getString(R.string.keystore_2));
        tvContent3.setText(getString(R.string.keystore_3));
        tvContent4.setText(getString(R.string.keystore_4));
        tvContent5.setText(getString(R.string.keystore_5));
        tvContent6.setText(getString(R.string.keystore_6));
//        tvContent1.setText(getString(R.string.keystore_1));
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvContent1, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent2, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent3, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent4, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent5, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent6, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTip, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTipContent, 1, LATO_REGULAR_WOFF_TTF);
    }
}
