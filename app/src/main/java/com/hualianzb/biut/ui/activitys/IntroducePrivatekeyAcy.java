package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2018/9/26
 * auther:wangtianyun
 * describe:什么是Privatekey
 */
public class IntroducePrivatekeyAcy extends BasicActivity {
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
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_tip_content)
    TextView tvTipContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_pk);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        tvRight.setVisibility(View.GONE);
        setFontStyle();
        tvTheme.setText("What is a private key?");
        tvContent1.setText(Html.fromHtml(getString(R.string.private_1)));
        tvContent2.setText(Html.fromHtml(getString(R.string.private_2)));
        tvContent3.setText(Html.fromHtml(getString(R.string.private_3)));
        tvContent4.setText(Html.fromHtml(getString(R.string.private_4)));
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvContent1, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent2, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent3, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvContent4, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTip, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTipContent, 1, LATO_REGULAR_WOFF_TTF);
    }
}
