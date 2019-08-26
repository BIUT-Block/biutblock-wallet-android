package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.adapters.AdapterWallets;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUM_PFB_TTF;

/**
 * Date:2018/8/16
 * auther:wangtianyun
 * describe:切换账户
 */
public class ChangeWalletActivity extends BasicActivity {
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.lv_wallet)
    ListView lvWallet;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    private AdapterWallets adapter;
    private List<RemembBIUT> list, dataList;
    private RemembBIUT remembBIUT;
    private long id;
    private boolean isFromHome;
    private String pool;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            id = bundle.getLong("id");
            isFromHome = bundle.getBoolean("isFromHome");
            pool = bundle.getString("pool");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            isFromHome = savedInstanceState.getBoolean("isFromHome");
            id = savedInstanceState.getLong("id");
            pool = savedInstanceState.getString("pool");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_record);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        tvTheme.setText("Switch Wallet");
    }

    private void initView() {
        list = new ArrayList<>();
        dataList = new ArrayList<>();
        setFontStyle();
        ivBackTop.setImageResource(R.drawable.icon_close_black);
        tvRight.setVisibility(View.GONE);
        adapter = new AdapterWallets(this, id,pool);
        lvWallet.setAdapter(adapter);
        dataList = BIUTApplication.dao_remeb.loadAll();
        remembBIUT = BIUTApplication.dao_remeb.load(id);
        if (null != dataList && dataList.size() > 0) {
            for (RemembBIUT rem : dataList) {
                list.add(rem);
            }
            list = Util.ListSort(list);
            adapter.setList(list);
        } else {
            lvWallet.setVisibility(View.GONE);
        }
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
}
