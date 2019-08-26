package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.HomePoolBean;
import com.hualianzb.biut.ui.adapters.AdapterHomePool;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUM_PFB_TTF;

/**
 * Date:2019/8/8
 * auther:wangtianyun
 * describe:矿池搜索类
 */
public class PoolSearchActivity extends BasicActivity {
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.tv_pool_name)
    TextView tvPoolName;
    @BindView(R.id.tv_nissan)
    TextView tvNissan;
    @BindView(R.id.tv_search_result)
    TextView tvSearchResult;
    @BindView(R.id.lv_pool)
    ListView lvPool;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    List<HomePoolBean> list;
    AdapterHomePool adapterHomePool;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.ll_result)
    LinearLayout llResult;
    @BindView(R.id.tv_no_result)
    TextView tvNoResult;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    String address, biut, biu;
    @BindView(R.id.tv_result_for)
    TextView tvResultFor;
    @BindView(R.id.tv_search_for)
    TextView tvSearchFor;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        switch (message.what) {
            case 0x000100://search-result
                ll_empty.setVisibility(View.GONE);
                llResult.setVisibility(View.VISIBLE);
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                list.add(new HomePoolBean(false));
                tvSearchResult.setText(getString(R.string.search_result, message.obj.toString()));
                tvSearchFor.setText(" " + edSearch.getText());
                adapterHomePool.setList(list.subList(0, 5));
                break;
            case 0x000110://空
                llResult.setVisibility(View.GONE);
                tvResultFor.setText(edSearch.getText());
                ll_empty.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_pool);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        setFontStyle();
        initData();
    }

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            biut = bundle.getString("biut");
            biu = bundle.getString("biu");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
            biut = savedInstanceState.getString("biut");
            biu = savedInstanceState.getString("biu");
        }
    }

    private void initData() {
        llResult.setVisibility(View.GONE);
        list = new ArrayList<>();
        adapterHomePool = new AdapterHomePool(this, address, biut, biu);
        lvPool.setAdapter(adapterHomePool);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    ivSearch.setEnabled(false);
                    iv_clear.setVisibility(View.GONE);
                } else {
                    ivSearch.setEnabled(true);
                    iv_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setFontStyle() {
        Util.setFontType(this, edSearch, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvCancel, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPoolName, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNissan, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvPoolName, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvSearchResult, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvSearchFor, 1, MONTSERRAT_MEDIUM_PFB_TTF);
        Util.setFontType(this, tvResultFor, 1, MONTSERRAT_MEDIUM_PFB_TTF);
    }

    @OnClick({R.id.iv_search, R.id.tv_cancel, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                view.requestFocus();
                if (!StringUtils.isEmpty(edSearch.getText().toString())) {
                    if (edSearch.getText().toString().equals("hello")) {
                        sendEmptyMessage(0x000110);
                    } else {
                        sendMessage(0x000100, 5);
                    }
                }
                break;
            case R.id.iv_clear:
                iv_clear.setVisibility(View.GONE);
                edSearch.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }
}
