package com.hualianzb.biut.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.commons.constants.RequestHost;
import com.hualianzb.biut.commons.interfaces.GlobalMessageType;
import com.hualianzb.biut.models.BalanceResultBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.TokenBean;
import com.hualianzb.biut.models.TokenBeanDao;
import com.hualianzb.biut.models.TransRecordTimeRequestBean;
import com.hualianzb.biut.ui.activitys.HomePageActivity;
import com.hualianzb.biut.ui.adapters.AdapterWallet;
import com.hualianzb.biut.ui.adapters.PagerRecyclerAdapter;
import com.hualianzb.biut.ui.basic.BasicFragment;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hualianzb.biut.views.AutoRecyclerViewPager;

import org.greenrobot.greendao.query.QueryBuilder;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 资产页
 */
@SuppressLint("ValidFragment")
public class PropertyFragment extends BasicFragment implements HomePageActivity.DataChange {
    @BindView(R.id.lv_property)
    ListView lvProperty;
    @BindView(R.id.vp_ad)
    AutoRecyclerViewPager vpAd;
    @BindView(R.id.custom_space)
    LinearLayout customSpace;
    @BindView(R.id.iv_rig_top)
    ImageView ivBackTop;
    @BindView(R.id.ll_no_net)
    LinearLayout llNoNet;
    protected List<View> mImageViewList = null;
    protected List<View> mViewList;
    private PagerRecyclerAdapter pageAdapter;
    private List<RemembBIUT> listWallet;
    protected View dotView;
    Unbinder unbinder;
    private PropertyFragment instance;
    private View view;
    private AdapterWallet adapterWallet;
    private List<TokenBean> listToken, listMyToken;
    private String address;
    private Dialog dialogDialog;
    private String json, requestUrl;
    private TransRecordTimeRequestBean bean;
    private List<RemembBIUT> remembBIUTList;
    private RemembBIUT remembBIUT;
    private QueryBuilder queryBuilder;
    private boolean isNetworkOk = true;

    public PropertyFragment() {
    }

    @OnClick(R.id.iv_rig_top)
    public void onViewClicked() {
        UiHelper.startChangeWalletRecordActyResult(getActivity(), remembBIUT.getId(), true);
    }

    @Override
    public void setDataChange(boolean isNetOk) {
        if (isNetOk) {
            if (null != llNoNet) {
                llNoNet.setVisibility(View.GONE);
            }
        } else {
            if (null != llNoNet) {
                llNoNet.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        address = ((HomePageActivity) activity).getAddress();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmengt_property, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initView() {
        if (isNetworkOk) {
            llNoNet.setVisibility(View.GONE);
        } else {
            llNoNet.setVisibility(View.VISIBLE);
        }
        listToken = new ArrayList<>();
        listMyToken = new ArrayList<>();
        remembBIUTList = new ArrayList<>();
        mViewList = new ArrayList<>();
        mImageViewList = new ArrayList<>();
        dialogDialog = DialogUtil.showLoadingDialog(getActivity(), getString(R.string.loading));
        listWallet = new ArrayList<>();
        lvProperty.setOnItemClickListener((parent, view, position, id) ->
                UiHelper.TransactionRecordActivity(getActivity(),
                        remembBIUT.getAddress(),
                        listMyToken.get(position).getName(),
                        listMyToken.get(position).getToken(),
                        listMyToken.get(1).getToken()));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        vpAd.setLayoutManager(mLayoutManager);
        vpAd.setHasFixedSize(true);
        pageAdapter = new PagerRecyclerAdapter(getActivity());
        vpAd.setAdapter(pageAdapter);
        queryBuilder = BIUTApplication.tokenBeanDao.queryBuilder();
        listToken = queryBuilder.list();
        for (TokenBean tokenBean : listToken) {
            if (tokenBean.getAddress() == address) {
                listMyToken.add(tokenBean);
            }
        }
        pageAdapter.setData(listWallet);
        vpAd.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                pageAdapter.notifyDataSetChanged();
                adapterWallet.notifyDataSetChanged();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (vpAd != null) {
                    int childCount = vpAd.getChildCount();
                    int width = vpAd.getChildAt(0).getWidth();
                    int padding = (vpAd.getWidth() - width) / 2;

                    for (int j = 0; j < childCount; j++) {
                        View v = recyclerView.getChildAt(j);
                        //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                        float rate = 0;
                        if (v.getLeft() <= padding) {
                            if (v.getLeft() >= padding - v.getWidth()) {
                                rate = (padding - v.getLeft()) * 1f / v.getWidth();
                            } else {
                                rate = 1;
                            }
                            v.setScaleY(1 - rate * 0.1f);
                            v.setScaleX(1 - rate * 0.1f);
                        } else {
                            //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                            if (v.getLeft() <= recyclerView.getWidth() - padding) {
                                rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                            }
                            v.setScaleY(0.9f + rate * 0.1f);
                            v.setScaleX(0.9f + rate * 0.1f);
                        }
                    }
                }
                if (null != adapterWallet) {
                    adapterWallet.notifyDataSetChanged();
                }
            }
        });
        vpAd.addOnPageChangedListener((oldPosition, newPosition) -> {
            dialogDialog.show();
            mViewList.get(oldPosition).setBackgroundResource(R.drawable.home_banner_normal);
            mViewList.get(newPosition).setBackgroundResource(R.drawable.home_banner_green);
            //
//            list.clear();
            //改变钱包的选中状态 重新保存
            RemembBIUT old = listWallet.get(oldPosition);
            old.setIsNow(false);

            RemembBIUT newOne = listWallet.get(newPosition);
            newOne.setIsNow(true);

            BIUTApplication.dao_remeb.update(old);
            BIUTApplication.dao_remeb.update(newOne);

            remembBIUT = newOne;
            address = remembBIUT.getAddress();
            getBalance(address.substring(2));
            adapterWallet.notifyDataSetChanged();
        });
        vpAd.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (vpAd.getChildCount() < 3) {
                if (vpAd.getChildAt(1) != null) {
                    if (vpAd.getCurrentPosition() == 0) {
                        View v1 = vpAd.getChildAt(1);
                        v1.setScaleY(0.9f);
                        v1.setScaleX(0.9f);
                    } else {
                        View v1 = vpAd.getChildAt(0);
                        v1.setScaleY(0.9f);
                        v1.setScaleX(0.9f);
                    }
                }
            } else {
                if (vpAd.getChildAt(0) != null) {
                    View v0 = vpAd.getChildAt(0);
                    v0.setScaleY(0.9f);
                    v0.setScaleX(0.9f);
                }
                if (vpAd.getChildAt(2) != null) {
                    View v2 = vpAd.getChildAt(2);
                    v2.setScaleY(0.9f);
                    v2.setScaleX(0.9f);
                }
            }
            if (null != adapterWallet) {
                adapterWallet.notifyDataSetChanged();
            }
        });

    }


    private void initData() {
        listToken.clear();
        listMyToken.clear();
        remembBIUTList.clear();
        listWallet.clear();
        mImageViewList.clear();
        mViewList.clear();
        remembBIUTList = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT r : remembBIUTList) {
            listWallet.add(r);
            if (r.getIsNow() == true) {
                remembBIUT = r;
                address = remembBIUT.getAddress();
            }
        }
        listToken = BIUTApplication.tokenBeanDao.loadAll();
        for (TokenBean tokenBean : listToken) {
            if (tokenBean.getAddress() == address) {
                listMyToken.add(tokenBean);
            }
        }
        listWallet = Util.ListSort(listWallet);
        if (null != remembBIUT) {
            adapterWallet = new AdapterWallet(getActivity(), remembBIUT.getAddress());
            lvProperty.setAdapter(adapterWallet);
            address = remembBIUT.getAddress();
            if (listMyToken.size() > 0) {
                adapterWallet.setList(listMyToken);
            }
        }
        try {
            handleViewPager(listWallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleViewPager(List<RemembBIUT> rememberBIUTList) {
        if (rememberBIUTList == null || rememberBIUTList.isEmpty()) {
            return;
        }
        if (customSpace != null) {
            customSpace.removeAllViews();
        } else {
        }
        mViewList.clear();
        mImageViewList.clear();
        for (RemembBIUT rememberBIUT : rememberBIUTList) {
            addPoint();
        }
        if (mViewList != null && mViewList.size() != 0) {
            int position = listWallet.indexOf(remembBIUT);
            mViewList.get(position).setBackgroundResource(R.drawable.home_banner_green);
        }
        vpAd.scrollToPosition(listWallet.indexOf(remembBIUT));
        vpAd.stopAutoScroll();//禁止自由滚动
        pageAdapter.setData(rememberBIUTList);
    }

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        switch (message.what) {
            case GlobalMessageType.MainRequest.BIUT:
                setData03(message, 0);
                break;
            case GlobalMessageType.MainRequest.BIU:
                setData03(message, 1);
                break;
        }
    }

    @Override
    protected void initLogics() {

    }

    private void addPoint() {
        dotView = LayoutInflater.from(view.getContext()).inflate(R.layout.dotview, customSpace, false);
        dotView.setBackgroundResource(R.drawable.home_banner_normal);
        mViewList.add(dotView);
        customSpace.addView(dotView);
    }


    private void setData03(Message message, int type) {
        String money = (String) message.obj;
        TokenBean tokenBean = new TokenBean();
        tokenBean.setToken(money);
        tokenBean.setAddress(address);
        if (type == 0) {//-------------0为biut 1为biu
            tokenBean.setName("BIUT");
            pageAdapter.setMoney(money);//为切卡设置钱数
            pageAdapter.notifyDataSetChanged();
        } else {
            tokenBean.setName("BIU");
        }
//        QueryBuilder queryBuilder = BIUTApplication.tokenBeanDao.queryBuilder();
        List<TokenBean> listTokenBean = BIUTApplication.tokenBeanDao.loadAll();
        TokenBean tokenBeanBiut = null;
        if (type == 0) {
            if (null != listTokenBean) {
                for (TokenBean bean : listTokenBean) {
                    if (bean.getAddress().equals(address) && bean.getName().equals(tokenBean.getName())) {
                        tokenBeanBiut = bean;
                        break;
                    }
                }
            }
            if (null != tokenBeanBiut) {
                tokenBeanBiut.setToken(tokenBean.getToken());
                BIUTApplication.tokenBeanDao.update(tokenBeanBiut);
            } else {
                BIUTApplication.tokenBeanDao.insert(tokenBean);
            }
//            queryBuilder.where(queryBuilder.and(TokenBeanDao.Properties.Address.eq(address), TokenBeanDao.Properties.Name.eq("BIUT")));
//            TokenBean tokenBeanBiut = (TokenBean) queryBuilder.unique();
//            //biut
//            if (null != tokenBeanBiut) {
//                tokenBeanBiut.setToken(tokenBean.getToken());
//                BIUTApplication.tokenBeanDao.update(tokenBeanBiut);
//            } else {
//                BIUTApplication.tokenBeanDao.insert(tokenBean);
//            }
            requestUrl = RequestHost.biu_url;
            setParams(requestUrl, json);
        } else {
//            queryBuilder.where(queryBuilder.and(TokenBeanDao.Properties.Address.eq(address), TokenBeanDao.Properties.Name.eq("BIU")));
//            TokenBean tokenBeanBiu = (TokenBean) queryBuilder.unique();
//            if (null != tokenBeanBiu) {
//                tokenBeanBiu.setToken(tokenBean.getToken());
//                BIUTApplication.tokenBeanDao.update(tokenBeanBiu);
//            } else {
//                BIUTApplication.tokenBeanDao.insert(tokenBean);
//            }

            if (null != listTokenBean) {
                for (TokenBean bean : listTokenBean) {
                    if (bean.getAddress().equals(address) && bean.getName().equals(tokenBean.getName())) {
                        tokenBeanBiut = bean;
                        break;
                    }
                }
            }
            if (null != tokenBeanBiut) {
                tokenBeanBiut.setToken(tokenBean.getToken());
                BIUTApplication.tokenBeanDao.update(tokenBeanBiut);
            } else {
                BIUTApplication.tokenBeanDao.insert(tokenBean);
            }


            listToken = BIUTApplication.tokenBeanDao.queryBuilder().where(TokenBeanDao.Properties.Address.eq(address)).list();
            if (null != listMyToken && listMyToken.size() > 0) {
                listMyToken.clear();
            }
            for (TokenBean bean : listToken) {
                if (tokenBean.getAddress().equals(address)) {
                    listMyToken.add(bean);
                }
            }
            adapterWallet.setList(listMyToken);
            dialogDialog.dismiss();
        }
    }

    //获取余额
    private void getBalance(String address) {
        bean = new TransRecordTimeRequestBean();
        List<Object> list = new ArrayList<>();
        bean.setId(1);
        bean.setJsonrpc("2.0");
        bean.setMethod("sec_getBalance");
        bean.setParams(list);
        list.add(address);//address
        list.add("latest");
        json = JSON.toJSONString(bean);
        requestUrl = RequestHost.biut_url;
        setParams(requestUrl, json);
        Log.e("+++", json);
    }

    private void setParams(String url, String json) {
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(json);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtils.isEmpty(result)) {
                    Log.e("+++", result);
                    String money = null;
                    BalanceResultBean balanceResultBean = JSON.parseObject(result, BalanceResultBean.class);
                    if (null != balanceResultBean) {
                        BalanceResultBean.ResultBean resultBean = balanceResultBean.getResult();
                        if (resultBean != null) {
                            if (!StringUtils.isEmpty(resultBean.getStatus()) && resultBean.getStatus().equals("1")) {
                                money = resultBean.getValue() + "";
                                if (url.equals(RequestHost.biut_url)) {
                                    sendMessage(GlobalMessageType.MainRequest.BIUT, money);
                                } else {
                                    sendMessage(GlobalMessageType.MainRequest.BIU, money);
                                }
                            }
                            if (!StringUtils.isEmpty(resultBean.getStatus()) && resultBean.getStatus().equals("0")) {
                                money = "0";
                                if (url.equals(RequestHost.biut_url)) {
                                    sendMessage(GlobalMessageType.MainRequest.BIUT_LAST, money);
                                } else {
                                    sendMessage(GlobalMessageType.MainRequest.BIU_LAST, money);
                                }
                            }
                        } else {
                            dialogDialog.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listToken = BIUTApplication.tokenBeanDao.queryBuilder().where(TokenBeanDao.Properties.Address.eq(address)).list();
                pageAdapter.setData(listWallet);
                dialogDialog.dismiss();
                DialogUtil.noNetTips(getActivity(), getString(R.string.net_work_err), () -> getBalance(address.substring(2))).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                dialogDialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initData();
    }

}
