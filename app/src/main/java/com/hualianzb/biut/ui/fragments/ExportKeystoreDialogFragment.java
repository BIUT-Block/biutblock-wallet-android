package com.hualianzb.biut.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.BiutBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.activitys.MakeMoneyActicity;
import com.hualianzb.biut.ui.basic.BaseDialogFragment;
import com.hualianzb.biut.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;

/**
 * Date:2019/1/5
 * auther:wangtianyun
 * 右边DialogFragment
 */
public class ExportKeystoreDialogFragment extends BaseDialogFragment {
    String address, keyStore;
    TextView tv_keystore;
    Unbinder unbinder;
    private ExportLinster listterner;
    private RemembBIUT bean;

    // 定义activity必须实现的接口方法
    public interface ExportLinster {
        void copyKeyStore(String sssd);
    }

    // 当FRagmen被加载到activity的时候会被回调
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof QrCodeDialogFragment.InfacnCopy) {
            listterner = (ExportLinster) activity; // 2.2 获取到宿主activity并赋值
        } else {
            throw new IllegalArgumentException("activity must implements ExportLinster");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        address = getArguments().getString("address");
        keyStore = getArguments().getString("keyStore");
        List<RemembBIUT> list = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT remembBIUT : list) {
            if (remembBIUT.getAddress().equals(address)) {
                bean = remembBIUT;
                break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.END);
        mWindow.setWindowAnimations(R.style.RightDialog);
        mWindow.setLayout(mWidth * 5 / 7, mHeight -
                ImmersionBar.getNavigationBarHeight(getActivity())
                - ImmersionBar.getStatusBarHeight(getActivity()));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_dialog_export_keystore;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .statusBarColor(R.color.white).
                statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MakeMoneyActicity) getActivity()).getActivityImmersionBar().keyboardEnable(true).init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        tv_keystore = rootView.findViewById(R.id.tv_keystore);
        tv_keystore.setText(keyStore);

        TextView tv_right = rootView.findViewById(R.id.tv_right);
        TextView tv_tip1 = rootView.findViewById(R.id.tv_tip1);
        TextView tv_tip2 = rootView.findViewById(R.id.tv_tip2);
        TextView tv_tip3 = rootView.findViewById(R.id.tv_tip3);
        TextView tv_tip4 = rootView.findViewById(R.id.tv_tip4);
        TextView tv_copy = rootView.findViewById(R.id.tv_copy);
        TextView tv_theme = rootView.findViewById(R.id.tv_theme);
        ImageView iv_back_top = rootView.findViewById(R.id.iv_back_top);
        iv_back_top.setVisibility(View.GONE);
        tv_right.setText("Close");
        tv_theme.setText("Export Keystore");
        Util.setFontType(getActivity(), tv_right, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_theme, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_tip1, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_tip2, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(getActivity(), tv_tip3, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_tip4, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(getActivity(), tv_keystore, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(getActivity(), tv_copy, 1, LATO_BOLD_TTF);
        return rootView;
    }


    @OnClick({R.id.tv_right, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                getDialog().dismiss();
                break;
            case R.id.tv_copy:
                listterner.copyKeyStore("1");
                getDialog().dismiss();
                break;
        }
    }

    //把传递进来的activity对象释放掉
    @Override
    public void onDetach() {
        super.onDetach();
        listterner = null;
    }
}