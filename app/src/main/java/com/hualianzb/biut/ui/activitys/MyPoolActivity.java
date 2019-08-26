package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.ProfirBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.adapters.AdapterProfit;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hualianzb.biut.views.AutoListView;
import com.hualianzb.biut.views.PullUpFooter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2019/8/6
 * auther:wangtianyun
 * describe:我矿池
 */
public class MyPoolActivity extends BasicActivity {
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_pool_name)
    TextView tvPoolName;
    @BindView(R.id.tv_day_profit)
    TextView tvDayProfit;
    @BindView(R.id.tv_view_join)
    TextView tv_view_join;
    @BindView(R.id.iv_rig_top)
    ImageView ivRigTop;
    @BindView(R.id.tv_week_tip)
    TextView tvWeekTip;
    @BindView(R.id.tv_week_value)
    TextView tvWeekValue;
    @BindView(R.id.tv_total_p_tip)
    TextView tvTotalPTip;
    @BindView(R.id.tv_total_p_value)
    TextView tvTotalPValue;
    @BindView(R.id.tv_frozen_tip)
    TextView tvFrozenTip;
    @BindView(R.id.tv_frozen_value)
    TextView tvFrozenValue;
    @BindView(R.id.tv_available_tip)
    TextView tvAvailableTip;
    @BindView(R.id.tv_available_balance)
    TextView tvAvailableBalance;
    @BindView(R.id.tv_my_pool)
    TextView tvMyPool;
    @BindView(R.id.tv_nissan)
    TextView tvNissan;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_join)
    TextView tvJoin;
    @BindView(R.id.ll_joined)
    LinearLayout llJoined;
    @BindView(R.id.tv_not_joined)
    TextView tvNotJoined;
    @BindView(R.id.tv_p_week)
    TextView tvPWeek;
    @BindView(R.id.tv_null)
    TextView tvNull;
    @BindView(R.id.lv_profit)
    AutoListView lvProfit;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private RemembBIUT remembBIUT;
    private List<ProfirBean> list;
    private AdapterProfit adapterProfit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pool);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        setFontStyle();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ProfirBean bean = new ProfirBean();
            list.add(bean);
        }
        adapterProfit.setList(list);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setRefreshFooter(new PullUpFooter(this));
        refreshLayout.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                list.addAll(list.subList(0, 5));
                adapterProfit.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
    }

    private void initView() {
        for (RemembBIUT remembean : BIUTApplication.dao_remeb.loadAll()) {
            if (remembean.getIsNow()) {
                remembBIUT = remembean;
                break;
            }
        }
        String address = remembBIUT.getAddress();
        tvAddress.setText(address);
        adapterProfit = new AdapterProfit(this);
        lvProfit.setAdapter(adapterProfit);
        tvNotJoined.setVisibility(View.GONE);
        tvNull.setVisibility(View.GONE);
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvAddress, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvWeekTip, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvWeekValue, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTotalPTip, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTotalPValue, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvFrozenTip, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvFrozenValue, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvAvailableTip, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvAvailableBalance, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvMyPool, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tv_view_join, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPoolName, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvName, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvDayProfit, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvJoin, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNotJoined, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPWeek, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNull, 1, LATO_BOLD_TTF);
    }

    @OnClick({R.id.iv_rig_top, R.id.tv_view_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_rig_top:
                UiHelper.startChangeWalletRecordActyResult(this, remembBIUT.getId(), false);
                break;
            case R.id.tv_view_join:
                finish();
                break;
        }
    }
}
