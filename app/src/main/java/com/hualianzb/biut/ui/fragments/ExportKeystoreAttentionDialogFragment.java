package com.hualianzb.biut.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.ui.activitys.MakeMoneyActicity;
import com.hualianzb.biut.ui.basic.BaseDialogFragment;
import com.hualianzb.biut.utils.Util;

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
public class ExportKeystoreAttentionDialogFragment extends BaseDialogFragment {

    Unbinder unbinder;
    private ExportAttenion listterner;

    // 定义activity必须实现的接口方法
    public interface ExportAttenion {
        void goIt();
    }

    // 当FRagmen被加载到activity的时候会被回调
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ExportAttenion) {
            listterner = (ExportAttenion) activity; // 2.2 获取到宿主activity并赋值
        } else {
            throw new IllegalArgumentException("activity must implements ExportAttenion");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.END);
        mWindow.setWindowAnimations(R.style.RightDialog);
        mWindow.setLayout(mWidth * 2 / 3, mHeight -
                ImmersionBar.getNavigationBarHeight(getActivity())
                - ImmersionBar.getStatusBarHeight(getActivity()));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.export_keystore_attention_dialog;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .statusBarColor(R.color.white).
                statusBarDarkFont(true).init();
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
        TextView tv_right = rootView.findViewById(R.id.tv_right);
        TextView tv_theme = rootView.findViewById(R.id.tv_theme);
        TextView tv_tip1 = rootView.findViewById(R.id.tv_tip1);
        TextView tv_tip2 = rootView.findViewById(R.id.tv_tip2);
        TextView tv_go_it = rootView.findViewById(R.id.tv_go_it);
        ImageView iv_back_top = rootView.findViewById(R.id.iv_back_top);
        iv_back_top.setVisibility(View.GONE);
        tv_right.setText("Close");
        tv_theme.setText("Export Keystore");

        Util.setFontType(getActivity(), tv_right, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_theme, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_tip1, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_tip2, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(getActivity(), tv_go_it, 1, LATO_BOLD_TTF);
        return rootView;
    }

    @OnClick({R.id.tv_right, R.id.tv_go_it})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                getDialog().dismiss();
                break;
            case R.id.tv_go_it:
                listterner.goIt();
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
