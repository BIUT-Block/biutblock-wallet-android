package com.hualianzb.biut.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.ui.activitys.HomePageActivity;
import com.hualianzb.biut.ui.basic.BasicFragment;
import com.hualianzb.biut.utils.ClickUtil;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hysd.android.platform_huanuo.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class MyFragment extends BasicFragment {
    @BindView(R.id.ll_manage)
    LinearLayout llManage;
    @BindView(R.id.ll_transaction_record)
    LinearLayout llTransactionRecord;
    @BindView(R.id.ll_addressbook)
    LinearLayout llAddressbook;
    @BindView(R.id.ll_check_update)
    LinearLayout llCheck;
    Unbinder unbinder;
    TextView tvVersion, tvUpdate;
    private MyFragment instance;
    private View view;
    //    private AdapterWallet adapterWallet;
    private List<Object> list;
    private String address;
    private CheckUpdateLinster listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        address = ((HomePageActivity) activity).getAddress();
        if (activity instanceof MyFragment.CheckUpdateLinster) {
            listener = (CheckUpdateLinster) activity; // 2.2 获取到宿主activity并赋值
        } else {
            throw new IllegalArgumentException("activity must implements CheckUpdateLinster");
        }
    }

    public interface CheckUpdateLinster {
        void check();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmengt_my, container, false);
        initView();
        initData();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        list = new ArrayList<>();
        tvVersion = view.findViewById(R.id.tv_version);
        tvUpdate = view.findViewById(R.id.tv_update);
        TextView tvTop = view.findViewById(R.id.tv_top);
        Util.setFontType(getActivity(), tvTop, 1, MONTSERRAT_BOLD_OTF);
    }

    private void initData() {
        String versionName = ActivityUtil.getVersionName(getActivity());
        tvVersion.setText(versionName);
    }

    @Override
    protected void initLogics() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_manage, R.id.ll_transaction_record, R.id.ll_addressbook, R.id.ll_check_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_manage:
                ClickUtil.checkFisrtAndNet(getActivity());
                UiHelper.startManagerWalletActy(getActivity());
                break;
            case R.id.ll_transaction_record:
                ClickUtil.checkFisrtAndNet(getActivity());
                boolean netWorkOk = isNetworkAvailable(getActivity());
                if (netWorkOk == false) {
                    DialogUtil.showErrorDialog(getActivity(), "No Network");
                } else {
                    UiHelper.startTransactionRecordActy(getActivity(), address);
                }
                break;
            case R.id.ll_addressbook:
                UiHelper.startAddressBookActy(getActivity(), true);
                break;
            case R.id.ll_check_update:
                ClickUtil.checkFisrtAndNet(getActivity());
                listener.check();
                break;
        }
    }

}
