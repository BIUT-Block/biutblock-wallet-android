package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.RequestHost;
import com.hualianzb.biut.models.BiutTransactionBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.ResultInChainBeanOrPool;
import com.hualianzb.biut.models.ResultInChainBeanOrPoolDao;
import com.hualianzb.biut.models.TransRecordTimeRequestBean;
import com.hualianzb.biut.ui.adapters.AdapterTradeRecordAll;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hualianzb.biut.views.AutoListView;
import com.hualianzb.biut.views.PullDownHeader;
import com.hualianzb.biut.views.PullUpFooter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUM_PFB_TTF;

/**
 * 交易记录//所有币种
 */
public class TransactionRecordActy extends BasicActivity {
    @BindView(R.id.lv_record)
    AutoListView lv;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.line_wallet)
    RelativeLayout lineWallet;
    @BindView(R.id.tv_change_wallet)
    TextView tvChangeWallet;
    @BindView(R.id.tv_wallet_name)
    TextView tvWalletName;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_no_record)
    TextView tvNoRecord;
    private AdapterTradeRecordAll adapter;
    private String address;
    private String title = "BIUT";
    private List<ResultInChainBeanOrPool> lastListCache;//本地缓存的数据
    private List<BiutTransactionBean.ResultBean.FatherBean> listChain;
    private List<BiutTransactionBean.ResultBean.FatherBean> listPoor;
    private List<ResultInChainBeanOrPool> listGet;//获取到的数据
    private List<ResultInChainBeanOrPool> listGetMy;
    int lastSize;
    private List<RemembBIUT> myDates;
    private RemembBIUT walletBean;
    private Dialog dialogLoading;
    private TransRecordTimeRequestBean bean;
    private String json, requestUrl;
    private int currentPage = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 002:
                    dialogLoading.dismiss();
                    setDataResult();
                    break;
                case 003:
                    String resultData = (String) msg.obj;
                    int type = msg.getData().getInt("type");
                    BiutTransactionBean biutTransactionBean = JSON.parseObject(resultData, BiutTransactionBean.class);
                    if (null != biutTransactionBean) {
                        BiutTransactionBean.ResultBean resultBean = biutTransactionBean.getResult();
                        if (null != resultBean) {
                            if (!StringUtils.isEmpty(resultBean.getStatus()) && resultBean.getStatus().equals("1")) {
                                listChain = resultBean.getResultInChain();
                                listPoor = resultBean.getResultInPool();
                                setDataTypeForList(listChain, type);
                                setDataTypeForList(listPoor, type);
                                if (type != 0) {
                                    setDataResult();
                                } else {
                                    requestUrl = RequestHost.biu_url;
                                    setParams(requestUrl, json, 1);
                                }
                            } else {
                                dialogLoading.dismiss();
                                refreshLayout.finishRefresh();
                                refreshLayout.setVisibility(View.GONE);
                                llNone.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (type != 0) {
                                setDataResult();
                            } else {
                                requestUrl = RequestHost.biu_url;
                                setParams(requestUrl, json, 1);
                            }
                        }
                    } else {
                        if (type != 0) {
                            setDataResult();
                        } else {
                            requestUrl = RequestHost.biu_url;
                            setParams(requestUrl, json, 1);
                        }
                    }
                    break;
            }

        }
    };

    private void setDataTypeForList(List<BiutTransactionBean.ResultBean.FatherBean> theList, int theType) {
        if (theList.size() > 0) {
            for (BiutTransactionBean.ResultBean.FatherBean poorOrchain : theList) {
                ResultInChainBeanOrPool result = new ResultInChainBeanOrPool();
                result.setTheAddress(address);
                result.setValue(poorOrchain.getValue());
                result.setTxTo(poorOrchain.getTxTo());
                result.setTxReceiptStatus(poorOrchain.getTxReceiptStatus());
                result.setTxHeight(poorOrchain.getTxHeight());
                result.setTxFee(poorOrchain.getTxFee());
                result.setTxFrom(poorOrchain.getTxFrom());
                result.setBlockNumber(poorOrchain.getBlockNumber());
                result.setTimeStamp(poorOrchain.getTimeStamp());
                result.setNonce(poorOrchain.getNonce());
                result.setInputData(poorOrchain.getInputData());
                result.setBlockHash(poorOrchain.getBlockHash());
                result.setTxHash(poorOrchain.getTxHash());
                result.setType(theType + "");
                listGet.add(result);
            }
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
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tride_record);
        BIUTApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        tvRight.setVisibility(View.GONE);
        tvTheme.setText("All Transactions");
        setFontStyle();
        listChain = new ArrayList<>();
        listPoor = new ArrayList<>();
        listGet = new ArrayList<>();
        lastListCache = new ArrayList<>();
        listGetMy = new ArrayList<>();
        myDates = new ArrayList<>();
        dialogLoading = DialogUtil.showLoadingDialog(this, getString(R.string.loading));
        adapter = new AdapterTradeRecordAll(this);
        lv.setAdapter(adapter);
        adapter.setData(lastListCache, address);
        refreshLayout.setRefreshHeader(new PullDownHeader(this));
        refreshLayout.setRefreshFooter(new PullUpFooter(this));
        myDates = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT remembe : myDates) {
            if (remembe.getIsNow()) {
                walletBean = remembe;
                address = remembe.getAddress();
                break;
            }
        }
        tvWalletName.setText(walletBean.getWalletName());
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshlay -> {
            currentPage = 1;
            getData(address, currentPage);
        });
        lv.setOnItemClickListener((parent, view, position, id) -> UiHelper.startTransaAllActivity(TransactionRecordActy.this, address, lastListCache.get(position).getId()));
        //上拉加载
        refreshLayout.setOnLoadmoreListener(refreshlay -> {
            currentPage++;
            getData(address, currentPage);
        });
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, name, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvWalletName, 1, MONTSERRAT_MEDIUM_PFB_TTF);
        Util.setFontType(this, tvChangeWallet, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNoRecord, 1, LATO_BOLD_TTF);
    }

    private void biutRecordRequest(String nowAddress, int currentPage) {
        dialogLoading.show();
        bean = new TransRecordTimeRequestBean();
        List<Object> list = new ArrayList<>();
        bean.setId(1);
        bean.setJsonrpc("2.0");
        bean.setMethod("sec_getTransactions");
        bean.setParams(list);
        list.add(nowAddress.substring(2));//address
        list.add(currentPage);
        list.add(4);
        json = JSON.toJSONString(bean);
        requestUrl = RequestHost.biut_url;
        setParams(requestUrl, json, 0);
    }

    private void setParams(String url, String json, int type) {
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(json);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("type", type);
                message.what = 003;
                message.obj = result;
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                handler.sendEmptyMessage(002);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                handler.sendEmptyMessage(002);
            }

            @Override
            public void onFinished() {
            }
        });
    }

    @OnClick({R.id.tv_change_wallet, R.id.iv_back_top})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_change_wallet:
                UiHelper.startChangeWalletRecordActyResult(this, walletBean.getId(), false);
                break;
            case R.id.iv_back_top:
                goBacktoMyPage();
                break;
        }
    }

    private void goBacktoMyPage() {
        UiHelper.goBackHomaPageAc(this, address, true);
    }

    @Override
    public void onBackPressed() {
        goBacktoMyPage();
    }

    @Override
    public void onResume() {
        super.onResume();
        clearLList();
        currentPage = 1;
        lastListCache = BIUTApplication.recordResulttAllDao.queryBuilder()
                .where(ResultInChainBeanOrPoolDao.Properties.TheAddress.eq(address)).orderDesc(ResultInChainBeanOrPoolDao.Properties.TimeStamp).list();
        lastSize = lastListCache.size();
        myDates = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT remembe : myDates) {
            if (remembe.getIsNow()) {
                walletBean = remembe;
                address = remembe.getAddress();
                break;
            }
        }
        tvWalletName.setText(walletBean.getWalletName());
        lineWallet.setLayoutParams(new RelativeLayout.LayoutParams(25 * (walletBean.getWalletName().length()), 4));
        lineWallet.setBackgroundColor(getResources().getColor(R.color.text_green_increase));
        currentPage = 1;
        getData(address, currentPage);
    }

    private void clearLList() {
        listChain.clear();
        listPoor.clear();
        listGet.clear();
        listGetMy.clear();
        lastListCache.clear();
    }

    private void getData(String address, int currentPage) {
        if (currentPage == 1) {
            clearCanch();
        }
        clearLList();
        biutRecordRequest(address, currentPage);
    }

    private void setDataResult() {
        dialogLoading.dismiss();
        //清除收款方pengding状态的记录
        try {
            if (listGet.size() > 0) {
                for (ResultInChainBeanOrPool bean : listGet) {
                    if (bean.getTxReceiptStatus().equals("pending") && bean.getTxTo().equals(address.substring(2))) {
                        listGet.remove(bean);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastListCache = BIUTApplication.recordResulttAllDao.queryBuilder()
                .where(ResultInChainBeanOrPoolDao.Properties.TheAddress.eq(address)).orderDesc(ResultInChainBeanOrPoolDao.Properties.TimeStamp).list();
        lastSize = lastListCache.size();
        if (listGet.size() == 0) {
            if (lastListCache.size() == 0) {
                dialogLoading.dismiss();
                refreshLayout.finishRefresh();
                refreshLayout.setVisibility(View.GONE);
                llNone.setVisibility(View.VISIBLE);
            } else {
                adapter.setData(lastListCache, address);
                refreshLayout.finishRefresh();
            }
        } else {
            for (ResultInChainBeanOrPool pool : listGet) {
                BIUTApplication.recordResulttAllDao.save(pool);
            }
            lastListCache = BIUTApplication.recordResulttAllDao.queryBuilder()
                    .where(ResultInChainBeanOrPoolDao.Properties.TheAddress.eq(address)).orderDesc(ResultInChainBeanOrPoolDao.Properties.TimeStamp).list();
            adapter.setData(lastListCache, address);
            refreshLayout.finishRefresh();
            refreshLayout.setVisibility(View.VISIBLE);
            llNone.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 100:
                if (null != data) {
                    String nowAddress = data.getStringExtra("nowAddress");
                    if (!StringUtils.isEmpty(nowAddress)) {
                        if (!nowAddress.equals(address)) {
                            clearLList();
                            for (RemembBIUT remembe : myDates) {
                                if (remembe.getIsNow()) {
                                    walletBean = remembe;
                                    address = remembe.getAddress();
                                    break;
                                }
                            }
                            tvWalletName.setText(walletBean.getWalletName());
                            currentPage = 1;
                            getData(address, currentPage);
                        }
                    }
                }
                break;
        }
    }

    private void clearCanch() {
        lastListCache = BIUTApplication.recordResulttAllDao.queryBuilder()
                .where(ResultInChainBeanOrPoolDao.Properties.TheAddress.eq(address)).orderDesc(ResultInChainBeanOrPoolDao.Properties.TimeStamp).list();
        if (null != lastListCache && lastListCache.size() > 0) {
            for (ResultInChainBeanOrPool beanOrPool : lastListCache) {
                BIUTApplication.recordResulttAllDao.deleteByKey(beanOrPool.getId());
            }
        }
    }
}
