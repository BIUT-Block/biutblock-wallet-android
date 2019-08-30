package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.zxing.activity.CaptureActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.RequestHost;
import com.hualianzb.biut.commons.interfaces.GlobalMessageType;
import com.hualianzb.biut.models.BiutTransactionBean;
import com.hualianzb.biut.models.ResultInChainBeanOrPool;
import com.hualianzb.biut.models.ResultInChainBeanOrPoolDao;
import com.hualianzb.biut.models.TransRecordTimeRequestBean;
import com.hualianzb.biut.ui.adapters.AdapterTradeRecordEach;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.ClickUtil;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.ToastUtil;
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
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.constants.Constant.SpConstant.SWEEP;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.MessgeCode.ContainMessage;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2018/8/16
 * auther:wangtianyun
 * describe:        BIUT资产详情--交易记录
 */
public class TransactionRecordBiutActivity extends BasicActivity {
    @BindView(R.id.tv_all)
    TextView tvAll;//总资产
    @BindView(R.id.tv_key_content)
    TextView tvKeyContent;
    @BindView(R.id.lv_record)
    AutoListView lvRecord;
    @BindView(R.id.ll_none)
    LinearLayout ll_none;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.tv_available)
    TextView tvAvailable;
    @BindView(R.id.tv_frozen)
    TextView tvFrozen;
    @BindView(R.id.tv_available_left)
    TextView tvAvailableLeft;
    @BindView(R.id.tv_frozen_left)
    TextView tvFrozenLeft;
    @BindView(R.id.tv_all_left)
    TextView tvAllLeft;
    @BindView(R.id.tv_empty_tip)
    TextView tvEmptyTip;
    @BindView(R.id.tv_transfer)
    TextView tvTransfer;
    @BindView(R.id.tv_receipt)
    TextView tvReceipt;
    private AdapterTradeRecordEach adapter;
    private List<ResultInChainBeanOrPool> lastListCache;//本地缓存的数据
    private List<BiutTransactionBean.ResultBean.FatherBean> listChain;
    private List<BiutTransactionBean.ResultBean.FatherBean> listPoor;
    private List<BiutTransactionBean.ResultBean.FatherBean> listGet;//获取到的数据
    private String address;
    private String
            title,
            biuAmount,//biu的数量
            money;//是可用资产
    private Dialog dialogLoading, noNetDialog;
    private String type;
    private double gas = 0;
    private List<ResultInChainBeanOrPool> listDate;
    private String tag;
    List<ResultInChainBeanOrPool> listLLL = new ArrayList<>();
    int pageSize = 1;
    private static String ADDMORE = "ADDMORE";
    private static String FRESH = "FRESH";

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            money = bundle.getString("money");
            title = bundle.getString("kind");
            biuAmount = bundle.getString("biuAmount");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
            title = savedInstanceState.getString("kind");
            money = savedInstanceState.getString("money");
            biuAmount = savedInstanceState.getString("biuAmount");
        }
    }

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        switch (message.what) {
            case GlobalMessageType.MessgeCode.CANCELORERROR:
                dialogLoading.dismiss();
                noNetDialog.show();
                break;
            case ContainMessage:
                String resultData = (String) message.obj;
                BiutTransactionBean biutTransactionBean = JSON.parseObject(resultData, BiutTransactionBean.class);
                if (null != biutTransactionBean) {
                    BiutTransactionBean.ResultBean resultBean = biutTransactionBean.getResult();
                    if (null != resultBean) {
                        if (!StringUtils.isEmpty(resultBean.getStatus()) && resultBean.getStatus().equals("1")) {
                            listChain = resultBean.getResultInChain();
                            listPoor = resultBean.getResultInPool();

                            listGet.addAll(listChain);
                            listGet.addAll(listPoor);
                            setDataResult();//此处是获所有数据
                        } else {
                            dialogLoading.dismiss();
                            refreshLayout.finishRefresh();
                            llData.setVisibility(View.GONE);
                            ll_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        setDataResult();
                    }

                } else {
                    setDataResult();
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionrecord);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        dialogLoading = DialogUtil.showLoadingDialog(this, getString(R.string.loading));
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        setFontStyle();
        initData();
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvAll, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvAllLeft, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvAvailable, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvAvailableLeft, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvFrozen, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvFrozenLeft, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvKeyContent, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvEmptyTip, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTransfer, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvReceipt, 1, LATO_BOLD_TTF);
    }

    private void initData() {
        money = Util.getStringFromSN(8, money);
        if (title.toLowerCase().equals("biut")) {
            tvTheme.setText("BIUT Transactions");
            type = "0";
        } else {
            tvTheme.setText("BIU Transactions");
            type = "1";
        }
        tvRight.setVisibility(View.GONE);
        noNetDialog = DialogUtil.noNetTips(this, getString(R.string.net_work_err), () -> {
            clearList();
            biutRecordRequest();
        });
        listChain = new ArrayList<>();
        listPoor = new ArrayList<>();
        listGet = new CopyOnWriteArrayList<>();
        lastListCache = new ArrayList<>();
        listDate = new ArrayList<>();

        adapter = new AdapterTradeRecordEach(this, address, title);
        refreshLayout.setRefreshHeader(new PullDownHeader(this));
        refreshLayout.setRefreshFooter(new PullUpFooter(this));
        tvAll.setText(money);
        lvRecord.setAdapter(adapter);

        listDate = BIUTApplication.recordResulttDao.queryBuilder()
                .where(ResultInChainBeanOrPoolDao.Properties.Type.eq(type)).list();
        adapter.setData(listDate);

        if (null != listDate && listDate.size() > 0) {
            adapter.setData(listDate);
            llData.setVisibility(View.VISIBLE);
            ll_none.setVisibility(View.GONE);
        } else {
            llData.setVisibility(View.GONE);
            ll_none.setVisibility(View.VISIBLE);
            listDate = new ArrayList<>();
        }

        lvRecord.setOnItemClickListener((parent, view, position, id) -> {
            long theId = listDate.get(position).getId();
            UiHelper.startTransaAllActivity(TransactionRecordBiutActivity.this, address, theId);
        });
        //首次请求
        biutRecordRequest();
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            tag = FRESH;
            pageSize = 1;
            clearList();
            biutRecordRequest();
            refreshLayout.finishRefresh();
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            tag = ADDMORE;
            pageSize++;
            biutRecordRequest();
        });
    }

    boolean isNoMore;

    private void biutRecordRequest() {
        //首次加载或者刷新
        if (StringUtils.isEmpty(tag) || tag.equals(FRESH)) {
            dialogLoading.show();
            TransRecordTimeRequestBean bean = new TransRecordTimeRequestBean();
            List<Object> list = new ArrayList<>();
            bean.setId(1);
            bean.setJsonrpc("2.0");
            bean.setMethod("sec_getTransactions");
            bean.setParams(list);
            list.add(address.substring(2));//address
            String json = JSON.toJSONString(bean);
            String url;
            if (type.equals("0")) {
                url = RequestHost.biut_url;
            } else {
                url = RequestHost.biu_url;
            }
            RequestParams params = new RequestParams(url);
            params.setAsJsonContent(true);
            params.setBodyContent(json);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    sendMessage(ContainMessage, result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("web3", ex.toString());
                    sendEmptyMessage(GlobalMessageType.MessgeCode.CANCELORERROR);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    sendEmptyMessage(GlobalMessageType.MessgeCode.CANCELORERROR);
                }

                @Override
                public void onFinished() {
//                Log.e("web3", "onFinished");
                }
            });
        } else {
            setListDatdToAdapter();
        }
    }

    private void clearList() {
        if (listGet.size() > 0) {
            listGet.clear();
        }
    }

    @OnClick({R.id.tv_transfer, R.id.tv_receipt, R.id.iv_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_transfer://转账
                ClickUtil.checkFisrtAndNet(this);
                boolean canGo = true;
                switch (type) {
                    case "0":
                        if (Double.parseDouble(money) == 0) {
                            DialogUtil.showErrorDialog(this, "BIUT isn't enough to sent");
                            canGo = false;
                        } else if (Double.parseDouble(biuAmount) < 0.01) {
                            DialogUtil.showErrorDialog(this, "BIU isn't enough to sent");
                            canGo = false;
                        }
                        break;
                    case "1":
                        if (Double.parseDouble(biuAmount) <= 0.01) {
                            DialogUtil.showErrorDialog(this, "BIU isn't enough to sent");
                            canGo = false;
                        }
                        break;
                }
                if (canGo) {
                    UiHelper.startTransferActivity(this, title, address, money, biuAmount);
                }
                break;
            case R.id.tv_receipt://收款
                UiHelper.startMakeCodeActivity(this, address, title.toLowerCase().equals("biut") ? 0 : 1);
                break;
            case R.id.iv_back_top:
                UiHelper.goBackHomaPageAc(this, address, false);
                break;
        }
    }

    private void setTvAvailableAndFrozen(List<ResultInChainBeanOrPool> theList) {
        double frozen = 0;
        for (int i = 0; i < theList.size(); i++) {
            if (theList.get(i).getTxReceiptStatus().equals("pending")) {
                frozen += Double.parseDouble(theList.get(i).getValue());
            }
        }
//        adapter.setData(theList);
        setTextMoney(tvFrozen, frozen + "");
        setTextMoney(tvAvailable, money);
        String allmoney = (Double.parseDouble(money) + frozen) + "";
        setTextMoney(tvAll, Util.getStringFromSN(8, allmoney));
        dialogLoading.dismiss();
    }

    private void setTextMoney(TextView tv, String args) {
        String myString = Util.getStringFromSN(8, args);
        tv.setText(myString);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UiHelper.goBackHomaPageAc(this, address, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            double reduceMoney = Double.parseDouble(data.getStringExtra("reduceMoney"));
            gas = Double.parseDouble(data.getStringExtra("gas"));
            if (type.equals("1")) {
                money = String.valueOf((Double.parseDouble(money) - reduceMoney) - gas);
            } else {
                money = String.valueOf((Double.parseDouble(money) - reduceMoney));
            }
            clearList();
            biutRecordRequest();
        }
        if (requestCode == SWEEP) {
            String result = "";
            try {
                result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            } catch (Exception e) {
                ToastUtil.show(this, R.string.scan_error);
                return;
            }
            if (!StringUtils.isEmpty(result)) {
                String[] resultArray = result.split("###");
                if (resultArray != null && resultArray.length > 0) {
                    String myGetAdress = resultArray[0];

                }
            } else {
                ToastUtil.show(this, "please retry");
            }
        }
    }

    private void setDataResult() {

        //清除收款方pengding状态的记录
        if (null != listGet && listGet.size() > 0) {
            for (BiutTransactionBean.ResultBean.FatherBean bean : listGet) {
                if (bean.getTxReceiptStatus().equals("pending") && bean.getTxTo().equals(address.substring(2))) {
                    listGet.remove(bean);
                }
            }
        }

        if (listGet.size() == 0) {
            if (lastListCache.size() == 0) {
                refreshLayout.finishRefresh();
                llData.setVisibility(View.GONE);
                ll_none.setVisibility(View.VISIBLE);
                setTextMoney(tvAvailable, money);
            } else {
                setTvAvailableAndFrozen(lastListCache);
            }
            dialogLoading.dismiss();
        } else {
            int lastSize = listDate.size();
            if (lastSize > 0) {
                BIUTApplication.recordResulttDao.deleteAll();
            } else {
                setTvAvailableAndFrozen(lastListCache);
            }
            for (BiutTransactionBean.ResultBean.FatherBean fatherBean : listGet) {
                ResultInChainBeanOrPool resultBean = new ResultInChainBeanOrPool();
                resultBean.setBlockHash(fatherBean.getBlockHash());
                resultBean.setInputData(fatherBean.getInputData());
                resultBean.setNonce(fatherBean.getNonce());
                resultBean.setTimeStamp(fatherBean.getTimeStamp());
                resultBean.setTxFee(fatherBean.getTxFee());
                resultBean.setTxFrom(fatherBean.getTxFrom());
                resultBean.setTxHash(fatherBean.getTxHash());
                resultBean.setTxHeight(fatherBean.getTxHeight());
                resultBean.setBlockNumber(fatherBean.getBlockNumber());
                resultBean.setTxReceiptStatus(fatherBean.getTxReceiptStatus());
                resultBean.setTxTo(fatherBean.getTxTo());
                resultBean.setValue(fatherBean.getValue());
                resultBean.setType(type);
                BIUTApplication.recordResulttDao.save(resultBean);
            }
            listDate = BIUTApplication.recordResulttDao.queryBuilder()
                    .where(ResultInChainBeanOrPoolDao.Properties.Type.eq(type)).list();
            setTvAvailableAndFrozen(listDate);
            if (listDate.size() > lastSize) {
                DialogUtil.showErrorDialog(this, (listDate.size() - lastSize) + " data have been updated…");
            }
            llData.setVisibility(View.VISIBLE);
            ll_none.setVisibility(View.GONE);
            setListDatdToAdapter();
        }
    }

    private void setListDatdToAdapter() {
        if (StringUtils.isEmpty(tag) || tag.equals(FRESH)) {
            if (listDate.size() > 7) {
                listLLL = listDate.subList(0, 7);
            } else {
                listLLL = listDate;
            }
            adapter.setData(listLLL);
            refreshLayout.finishRefresh();
        } else {
            if (listDate.size() > (7 * pageSize)) {
                listLLL = listDate.subList(0, 7 * pageSize);
            } else {
                listLLL = listDate;
            }
            adapter.setData(listLLL);
            refreshLayout.finishLoadmore();
        }
    }


}
