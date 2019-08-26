package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.RequestHost;
import com.hualianzb.biut.commons.interfaces.GlobalMessageType;
import com.hualianzb.biut.models.BalanceResultBean;
import com.hualianzb.biut.models.ManageWalletBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.TransRecordTimeRequestBean;
import com.hualianzb.biut.ui.adapters.AdapterManageWallet;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.MainRequest.GETTOKEN_ERROR;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BLACK_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_LIGHT_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class ManagerWalletActy extends BasicActivity {
    /**
     * 管理钱包
     */
    @BindView(R.id.lv_wallets)
    ListView lvWallets;
    @BindView(R.id.re_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_im_top)
    TextView tvImTop;
    @BindView(R.id.tv_im_bottom)
    TextView tvImBottom;
    @BindView(R.id.tv_c_top)
    TextView tvCTop;
    @BindView(R.id.tv_c_bottom)
    TextView tvCBottom;
    private List<RemembBIUT> list;
    private AdapterManageWallet adapter;
    private Dialog dialogLoading;
    private List<ManageWalletBean> listItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_manage_wallet);
        BIUTApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTheme.setText("Manage Wallet");
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        setFontStyle();
        tvRight.setVisibility(View.GONE);
        adapter = new AdapterManageWallet(this);
        list = new ArrayList<>();
        listItems = new ArrayList<>();
        lvWallets.setAdapter(adapter);
        rlTitle.setBackgroundColor(getResources().getColor(R.color.white));
        lvWallets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UiHelper.startMakeMoneyActicity(
                        ManagerWalletActy.this,
                        list.get(position).getAddress(),
                        listItems.get(position).getMoney(),
                        false);
            }
        });
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvCTop, 1, LATO_BLACK_PFB_TTF);
        Util.setFontType(this, tvImTop, 1, LATO_BLACK_PFB_TTF);
        Util.setFontType(this, tvCBottom, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvImBottom, 1, LATO_REGULAR_WOFF_TTF);
    }

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        switch (message.what) {
            case GlobalMessageType.MainRequest.GETTOKEN_LAST:
//                Collections.reverse(listItems);
                adapter.setList(listItems);
                dialogLoading.dismiss();
                break;
            case GETTOKEN_ERROR:
                dialogLoading.dismiss();
                initData();
                break;
        }
    }

    @OnClick({R.id.ll_create, R.id.ll_import, R.id.iv_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_create:
                UiHelper.startActyCreateWalletActivity(this);
                break;
            case R.id.ll_import:
                UiHelper.startActyImportWalletActivity(this);
                break;
            case R.id.iv_back_top:
                finish();
                break;
        }
    }

    private void getDaiBi(int index) {
        try {
            getBiutBalance(index);
        } catch (Exception e) {
            e.printStackTrace();
            if (dialogLoading.isShowing()) {
                dialogLoading.dismiss();
            }
            if (list.size() > 0) {
                list.clear();
                DialogUtil.noNetTips(this, getString(R.string.net_work_err), () -> finish());
            }
        } finally {

        }
    }

    //获取BIUT余额
    private void getBiutBalance(int position) {
        TransRecordTimeRequestBean bean = new TransRecordTimeRequestBean();
        List<Object> listRequestData = new ArrayList<>();
        bean.setId(1);
        bean.setJsonrpc("2.0");
        bean.setMethod("sec_getBalance");
        bean.setParams(listRequestData);
        listRequestData.add(list.get(position).getAddress().substring(2));//address
        listRequestData.add("latest");
        String json = JSON.toJSONString(bean);
        RequestParams params = new RequestParams(RequestHost.biut_url);
        params.setAsJsonContent(true);
        params.setBodyContent(json);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtils.isEmpty(result)) {
                    String money = null;
                    BalanceResultBean balanceResultBean = JSON.parseObject(result, BalanceResultBean.class);
                    if (null != balanceResultBean) {
                        BalanceResultBean.ResultBean resultBean = balanceResultBean.getResult();
                        if (resultBean != null) {
                            if (!StringUtils.isEmpty(resultBean.getStatus()) && resultBean.getStatus().equals("1")) {
                                money = resultBean.getValue() + "";
                                ManageWalletBean itemBeam = new ManageWalletBean();
                                itemBeam.setAddress(list.get(position).getAddress());
                                itemBeam.setWalletName(list.get(position).getWalletName());
                                itemBeam.setHowToCreate(list.get(position).getHowToCreate());
                                itemBeam.setBackup(list.get(position).getIsBackup());
                                itemBeam.setMoney(money);
                                listItems.add(itemBeam);
                                if (position == (list.size() - 1)) {
                                    sendEmptyMessage(GlobalMessageType.MainRequest.GETTOKEN_LAST);
                                } else {
                                    getBiutBalance(position + 1);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                sendEmptyMessage(GETTOKEN_ERROR);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
//                Log.e("web3", "onFinished");
            }
        });
    }

    @Override
    public void onChangeListener(int status) {

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (isNetworkAvailable(this)) {
                initData();
            } else {
                DialogUtil.noNetTips(this, getString(R.string.net_work_err), () -> initData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        dialogLoading = DialogUtil.showLoadingDialog(this, getString(R.string.loading));
        if (list.size() > 0) {
            list.clear();
        }
        if (listItems.size() > 0) {
            listItems.clear();
        }

        list = BIUTApplication.dao_remeb.loadAll();
        if (null != list && list.size() > 0) {
            list = Util.ListSort(list);
        }
        dialogLoading.show();
        getDaiBi(0);
    }
}
