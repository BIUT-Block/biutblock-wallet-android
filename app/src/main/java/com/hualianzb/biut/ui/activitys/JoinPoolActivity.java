package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.MoneyValueFilter;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2019/8/9
 * auther:wangtianyun
 * describe:
 */
public class JoinPoolActivity extends BasicActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.iv_close_top)
    ImageView ivCloseTop;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.ed_money)
    EditText edMoney;
    @BindView(R.id.tv_flag)
    TextView tvFlag;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_next)
    TextView tvNext;
    String myGetAmount, address, allBiut, errorString;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            allBiut = bundle.getString("biut");
            allBiut = "19999";
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
            allBiut = savedInstanceState.getString("biut");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joid_pool);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        setFontStyle();
        initView();
    }

    private void initView() {
        tvTheme.setText("Join The Mine Pool");
        ivCloseTop.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        tvError.setVisibility(View.INVISIBLE);
        allBiut = Util.getStringFromSN(8, allBiut);
        tvBalance.setText(allBiut);
        tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
        tvNext.setEnabled(false);
        if (Double.parseDouble(allBiut) < 10000) {
            tvError.setVisibility(View.VISIBLE);
            llAll.setEnabled(false);
            edMoney.setEnabled(false);
        } else {
            llAll.setEnabled(true);
            edMoney.setEnabled(true);
        }
        edMoney.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(8)});
        edMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myGetAmount = s.toString().trim();
                if (!StringUtils.isEmpty(myGetAmount)) {
                    edMoney.setSelection(s.toString().length());
                    check(myGetAmount);
                } else {
                    myGetAmount = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void check(String getMoney) {
        if (Double.parseDouble(getMoney) < 10000) {
            errorString = getString(R.string.frozen_not_enough);
            tvError.setText(errorString);
            tvError.setVisibility(View.VISIBLE);
            tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
            tvNext.setEnabled(false);
        } else {
            if (Double.parseDouble(getMoney) > Double.parseDouble(allBiut)) {
                errorString = "Biut is not enough";
                tvError.setText(errorString);
                tvError.setVisibility(View.VISIBLE);
                tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                tvNext.setEnabled(false);
            } else {
                tvError.setVisibility(View.INVISIBLE);
                tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn));
                tvNext.setEnabled(true);
            }
        }
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tv_name, 1, LATO_BOLD_TTF);
        Util.setFontType(this, edMoney, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvFlag, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvBalance, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvAll, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTip, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNext, 1, LATO_BOLD_TTF);
    }

    @OnClick({R.id.tv_next, R.id.ll_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_all:
                edMoney.setText(allBiut);
//                if (Double.parseDouble(allBiut) < 10000) {
//                    errorString = getString(R.string.frozen_not_enough);
//                    tvError.setText(errorString);
//                    tvError.setVisibility(View.VISIBLE);
//                    tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
//                    tvNext.setEnabled(false);
//                } else {
//                    tvError.setVisibility(View.INVISIBLE);
//                    tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn));
//                    tvNext.setEnabled(true);
//                }
                break;
            case R.id.tv_next:
                UiHelper.startMyPoolActivity(this);
                finish();
                break;
        }
    }
}
