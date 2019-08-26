package com.hualianzb.biut.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.HomePoolBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.TokenBean;
import com.hualianzb.biut.ui.adapters.AdapterHomePool;
import com.hualianzb.biut.ui.basic.BasicFragment;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2019/8/5
 * auther:wangtianyun
 * describe:
 */
public class PoolFragment extends BasicFragment {
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    Unbinder unbinder;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.lv_pool)
    ListView lvPool;
    RemembBIUT biutBean;
    String biut, biu;
    AdapterHomePool adapterHomePool;
    List<HomePoolBean> list;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_pool_name)
    TextView tvPoolName;
    @BindView(R.id.tv_nissan)
    TextView tvNissan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pool, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTheme.setText("Mined Pool");
        setFontStyle();
        return view;
    }

    private void initView() {
        for (RemembBIUT bean : BIUTApplication.dao_remeb.loadAll()) {
            if (bean.getIsNow()) {
                biutBean = bean;
                break;
            }
        }
        String address = biutBean.getAddress();
        tvAddress.setText(address.substring(0, 8) + "..." + address.substring(32, 40));
        for (TokenBean tokenBean : BIUTApplication.tokenBeanDao.loadAll()) {
            if (tokenBean.getAddress().equals(address)) {
                if (tokenBean.getName().equals("BIUT")) {
                    biut = tokenBean.getToken();//可用BIUT余额
                }
                if (tokenBean.getName().equals("BIU")) {
                    biu = tokenBean.getToken();//可用BIU余额
                }
            }
        }
        list = new ArrayList<>();
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
        adapterHomePool = new AdapterHomePool(getActivity(), address, biut, biu);
        lvPool.setAdapter(adapterHomePool);
        adapterHomePool.setList(list);
        HomePoolBean jionedBean = null;
        for (HomePoolBean bean : list) {
            if (bean.isJion()) {
                jionedBean = bean;
                break;
            }
        }
        if (null != jionedBean) {
            list.remove(jionedBean);
            adapterHomePool.setHasjioned(true);
            list.add(0, jionedBean);
        } else {
            adapterHomePool.setHasjioned(false);
        }
    }

    @Override
    protected void initLogics() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setFontStyle() {
        Util.setFontType(getActivity(), tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(getActivity(), tvAddress, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_title, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(getActivity(), tv_search, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(getActivity(), tvPoolName, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tvNissan, 1, LATO_BOLD_TTF);
    }

    @OnClick({R.id.iv_rig_top, R.id.tv_address, R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_rig_top:
                UiHelper.startChangeWalletRecordActy(getActivity(), biutBean.getId(), "pool");
                break;
            case R.id.tv_address:
                UiHelper.startMyPoolActivity(getActivity());
                break;
            case R.id.rl_search:
                UiHelper.startPoolSearchActivity(getActivity(),
                        biutBean.getAddress(),
                        biut,
                        biu);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
}
