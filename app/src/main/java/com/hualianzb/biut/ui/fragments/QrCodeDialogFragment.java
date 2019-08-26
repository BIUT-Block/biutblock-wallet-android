package com.hualianzb.biut.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.models.ReceiptBean;
import com.hualianzb.biut.ui.activitys.MakeMoneyActicity;
import com.hualianzb.biut.ui.basic.BaseDialogFragment;
import com.hualianzb.biut.utils.QRUtils;
import com.hualianzb.biut.utils.Util;

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
public class QrCodeDialogFragment extends BaseDialogFragment {

    String address;
    @BindView(R.id.iv_code_pic)
    ImageView ivCodePic;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    Unbinder unbinder;
    private Bitmap bitmap;
    private InfacnCopy listterner;
    private ReceiptBean bean;

    // 定义activity必须实现的接口方法
    public interface InfacnCopy {
        void copy(String str);
    }

    // 当FRagmen被加载到activity的时候会被回调
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof InfacnCopy) {
            listterner = (InfacnCopy) activity; // 2.2 获取到宿主activity并赋值
        } else {
            throw new IllegalArgumentException("activity must implements InfacnCopy");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        address = getArguments().getString("address");
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.END);
        mWindow.setWindowAnimations(R.style.RightDialog);
        mWindow.setLayout(mWidth * 5 / 8, mHeight -
                ImmersionBar.getNavigationBarHeight(getActivity())
                - ImmersionBar.getStatusBarHeight(getActivity()));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.qr_dialog;
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
        tvAddress.setText(address);
        bean = new ReceiptBean();
        bean.setAddress(address.trim());
        bean.setType(0);
        bean.setValue("0");
        bitmap = QRUtils.createQRCode(JSON.toJSONString(bean), 200, 200, null);
        ivCodePic.setImageBitmap(bitmap);
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
        TextView tv_close = rootView.findViewById(R.id.tv_right);
        tv_close.setText("Close");
        TextView tv_add_top = rootView.findViewById(R.id.tv_add_top);
        TextView tv_address = rootView.findViewById(R.id.tv_address);
        TextView tv_copy = rootView.findViewById(R.id.tv_copy);
        ImageView iv_back_top = rootView.findViewById(R.id.iv_back_top);
        iv_back_top.setVisibility(View.GONE);
        Util.setFontType(getActivity(), tv_close, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_add_top, 1, LATO_BOLD_TTF);
        Util.setFontType(getActivity(), tv_address, 1, LATO_REGULAR_WOFF_TTF);
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
                listterner.copy(address);
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
