package com.hualianzb.biut.ui.activitys;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.zxing.activity.CaptureActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.biutUtil.SECBlockJavascriptAPI;
import com.hualianzb.biut.commons.constants.Constant;
import com.hualianzb.biut.commons.constants.RequestHost;
import com.hualianzb.biut.models.BIUTTransResponseBean;
import com.hualianzb.biut.models.BiutSendRawBean;
import com.hualianzb.biut.models.ReceiptBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.models.SendRawBean;
import com.hualianzb.biut.models.SignDataBean;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.MoneyValueFilter;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.ToastUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hysd.android.platform_huanuo.base.config.PlatformConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.constants.Constant.SpConstant.SWEEP;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2018/8/16
 * auther:wangtianyun
 * describe:转账
 */
public class TransferActivity extends BasicActivity {
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    @BindView(R.id.iv_close_top)
    ImageView ivClose;
    @BindView(R.id.ed_toadress)
    EditText edToadress;
    @BindView(R.id.ed_senMoney)
    EditText edSenMoney;
    @BindView(R.id.ed_remark)
    EditText edRemark;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_address_error)
    TextView tvAddressError;
    @BindView(R.id.tv_amount_error)
    TextView tvAmountError;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_biu_error)
    TextView tvBiuError;
    @BindView(R.id.tv_slow)
    TextView tvSlow;
    @BindView(R.id.tv_fast)
    TextView tvFast;
    @BindView(R.id.tv_gas)
    TextView tvGas;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.re_title)
    RelativeLayout reTitle;
    @BindView(R.id.rl_constant)
    RelativeLayout rlConstant;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    private String myGetAdress, error_address, error_money, errorType;
    private String myGetAmount = "0", biuAmount;//biuAmount 为biu数量
    private boolean isAddressOk, isAmountOk = false;
    private boolean isTypeOk = true;
    private Dialog dialog;
    private String address;

    private List<RemembBIUT> remembBIUTList;


    private RemembBIUT remembBIUT;
    private double setMoney;
    private String title;
    private String tokenValue;
    private Dialog dialogLoading;
    private BiutSendRawBean.ParamsBean paramsBean;
    private Map<String, String> tranRemark;
    private boolean isCheckedPass;
    private int type;//0--BIUT 1--BIU
    private double gas = 0;
    private String numGas = "0";
    /**
     * 屏幕宽度
     */
    protected int mWidth;
    SECBlockJavascriptAPI secJsApi;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            title = bundle.getString("title");
            address = bundle.getString("address");
            tokenValue = bundle.getString("tokenValue");
            biuAmount = bundle.getString("tokenValue");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            title = savedInstanceState.getString("title");
            address = savedInstanceState.getString("address");
            tokenValue = savedInstanceState.getString("tokenValue");
            biuAmount = savedInstanceState.getString("tokenValue");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        try {
            secJsApi = new SECBlockJavascriptAPI(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        initView();
        initData();
    }

    private void initData() {
        tokenValue = Util.getStringFromSN(8, tokenValue);
        tvBalance.setText("Balance " + tokenValue);
        if (title.toLowerCase().equals("biut")) {
            type = 0;
        } else {
            type = 1;
        }
        edToadress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(s.toString())) {
                    edToadress.setSelection(s.toString().length());
                }
                myGetAdress = s.toString().trim();
                checkAddress(myGetAdress);
                setBtnClickable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edSenMoney.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(8)});
        edSenMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myGetAmount = s.toString().trim();
                if (!StringUtils.isEmpty(myGetAmount)) {
                    edSenMoney.setSelection(s.toString().length());
                } else {
                    myGetAmount = "0";
                }
                checkMoney(myGetAmount);
                setBtnClickable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvGas.setText(0.02 + " BIU");
        seekbar.setMax(900000000);
        seekbar.setProgress(100000000);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gas = (progress + 100000000) / Double.parseDouble("10000000000");
                DecimalFormat df = new DecimalFormat("#,##0.00000000");//保留8位小数且不用科学计数法
                numGas = df.format(gas);
                if (Double.parseDouble(numGas) < 0.02) {
                    tvSlow.setTextColor(getResources().getColor(R.color.seekBarBlue));
                    tvFast.setTextColor(getResources().getColor(R.color.text_blue));
                } else {
                    tvSlow.setTextColor(getResources().getColor(R.color.text_blue));
                    tvFast.setTextColor(getResources().getColor(R.color.text_yellow02));
                }
                if ((Double.parseDouble(numGas) == 0.02)) {
                    numGas = "0.02";
                }
                if ((Double.parseDouble(numGas) == 0.01)) {
                    numGas = "0.01";
                }
                if ((Double.parseDouble(numGas) == 0.1)) {
                    numGas = "0.1";
                }
                tvGas.setText(numGas + " BIU");
                if (type == 1) {
                    if (Float.parseFloat(String.valueOf(gas + Float.parseFloat(myGetAmount))) > Float.parseFloat(tokenValue)) {
                        tvBiuError.setVisibility(View.VISIBLE);
                        tvNext.setClickable(false);
                        tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
                        isAmountOk = false;
                    } else {
                        isAmountOk = true;
                        tvBiuError.setVisibility(View.GONE);
                        tvNext.setClickable(true);
                        tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn));
                    }
                }
                setBtnClickable();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //检验地址
    private void checkAddress(String theAddress) {
        if (StringUtils.isEmpty(theAddress)) {
            isAddressOk = false;
        } else if (theAddress.length() != 42 || !theAddress.substring(0, 2).equals("0x")) {
            isAddressOk = false;
            error_address = "Unavailable Address";
        } else if (address.equals(theAddress)) {
            isAddressOk = false;
            error_address = "The same address cannot be transferred";
            tvAddressError.setText(error_address);
        } else {
            isAddressOk = true;
        }
        if (isAddressOk) {
            tvAddressError.setVisibility(View.GONE);
        } else if (isAddressOk == false) {
            if (StringUtils.isEmpty(theAddress)) {
                tvAddressError.setVisibility(View.GONE);
            } else {
                tvAddressError.setText(error_address);
                tvAddressError.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkType(int theType) {
        if (theType == type) {
            isTypeOk = true;
        } else {
            isTypeOk = false;
        }
        if (isTypeOk == false) {
            errorType = "Only the type of " + title + " can be sent on the page.";
            DialogUtil.showErrorDialog(this, errorType);
            edSenMoney.setText("0");
            edToadress.setText("");
        }
    }

    private void checkMoney(String money) {
        float moneyD = 0;
        if (type == 0) {
            if (!StringUtils.isEmpty(money)) {
                if (money.equals(".")) {
                    money = "0" + money;
                }
                moneyD = Float.parseFloat(money);
            } else {
                moneyD = 0.0F;
                if (Double.parseDouble(numGas) > Double.parseDouble(biuAmount)) {
                    tvBiuError.setVisibility(View.VISIBLE);
                } else {
                    tvBiuError.setVisibility(View.GONE);
                }
            }
            if (moneyD < 0) {
                isAmountOk = false;
                error_money = "Not correct type of tokens";
            } else if (moneyD == 0) {
                isAmountOk = false;
                if (Double.parseDouble(numGas) > Double.parseDouble(biuAmount)) {
                    tvBiuError.setVisibility(View.VISIBLE);
                } else {
                    tvBiuError.setVisibility(View.GONE);
                }
            } else {
                if (moneyD > Double.parseDouble(tokenValue)) {
                    isAmountOk = false;
                    error_money = title + " isn't enough";
//                    DialogUtil.showErrorDialog(this, error_money + " to sent");
                } else {
                    isAmountOk = true;
                }
            }
            if (isAmountOk) {
                tvAmountError.setVisibility(View.GONE);
                edSenMoney.setBackgroundResource(R.drawable.bg_edit_gray);
            } else {
                if (moneyD == 0) {
                    tvAmountError.setVisibility(View.GONE);
                } else {
                    tvAmountError.setText(error_money);
                    tvAmountError.setVisibility(View.VISIBLE);
                    edSenMoney.setBackgroundResource(R.drawable.bg_edit_error);
                }
            }
        } else {
            if (!StringUtils.isEmpty(money)) {
                if (money.equals(".")) {
                    money = "0" + money;
                }
                moneyD = Float.parseFloat(money);
            } else {
                moneyD = 0.0F;
                if (Double.parseDouble(numGas) > Double.parseDouble(biuAmount)) {
                    tvBiuError.setVisibility(View.VISIBLE);
                } else {
                    tvBiuError.setVisibility(View.GONE);
                }
            }
            if (moneyD < 0) {
                isAmountOk = false;
                error_money = "Not correct type of tokens";
            } else if (moneyD == 0) {
                isAmountOk = false;
                if (Double.parseDouble(numGas) > Double.parseDouble(biuAmount)) {
                    tvBiuError.setVisibility(View.VISIBLE);
                } else {
                    tvBiuError.setVisibility(View.GONE);
                }
            } else {
                if ((Float.parseFloat(tokenValue) - Float.parseFloat(numGas) - moneyD) < 0) {
                    isAmountOk = false;
                    error_money = title + " isn't enough";
                } else {
                    isAmountOk = true;
                    tvBiuError.setVisibility(View.GONE);
                }
            }
            if (isAmountOk) {
                tvAmountError.setVisibility(View.GONE);
                tvBiuError.setVisibility(View.GONE);
                edSenMoney.setBackgroundResource(R.drawable.bg_edit_gray);
            } else {
                if (moneyD == 0) {
                    tvAmountError.setVisibility(View.GONE);
                } else {
                    tvAmountError.setText(error_money);
                    tvAmountError.setVisibility(View.VISIBLE);
                    edSenMoney.setBackgroundResource(R.drawable.bg_edit_error);
                }
            }
        }
    }

    private void setBtnClickable() {
        if (isAddressOk && isAmountOk && isTypeOk) {
            tvNext.setClickable(true);
            tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn));
        } else {
            tvNext.setClickable(false);
            tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
        }
    }

    private void initView() {
        ivBackTop.setVisibility(View.GONE);
        ivClose.setVisibility(View.VISIBLE);
        setFontStyle();
        dialogLoading = DialogUtil.showLoadingDialog(this, "Submitting");
        if (title.toLowerCase().equals("biut")) {
            tvTheme.setText("BIUT transfer");
        } else {
            tvTheme.setText("BIU transfer");
        }
        tvRight.setText("Scan Code");
        ivBackTop.setImageResource(R.drawable.icon_close_black);
        remembBIUTList = new ArrayList<>();
        remembBIUTList = BIUTApplication.dao_remeb.loadAll();
        tranRemark = new HashMap<>();
        tranRemark = PlatformConfig.getMap(Constant.SpConstant.TRANSREMARKS);

        if (null != remembBIUTList && remembBIUTList.size() > 0) {
            for (RemembBIUT remembBIUT : remembBIUTList) {
                if (address.equals(remembBIUT.getAddress())) {
                    this.remembBIUT = remembBIUT;
                    break;
                }
            }
        }
        tvNext.setClickable(false);
        tvNext.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvRight, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvAddress, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvAmount, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRemark, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvBalance, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvAll, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvFee, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvFast, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvSlow, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvGas, 1, LATO_BOLD_TTF);
        Util.setFontType(this, edToadress, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edSenMoney, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, edRemark, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvAddressError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvAmountError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvBiuError, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvNext, 1, LATO_BOLD_TTF);
    }

    @OnClick({R.id.tv_next, R.id.rl_constant, R.id.tv_right, R.id.ll_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                boolean netWorkOk = isNetworkAvailable(this);
                if (netWorkOk == false) {
                    Dialog dialog = DialogUtil.noNetTips(this, "Submit Failure.No Network Connection.", () -> {
                        return;
                    });
                    dialog.show();
                }
                if (netWorkOk) {
                    showTradeDetailDialog(TransferActivity.this);
                }
                break;
            case R.id.rl_constant:
                UiHelper.startAddressBookActy(this, false);
                break;
            case R.id.tv_right:
                startImageByCamera();
                break;
            case R.id.ll_all:
                gas = 0.01;
                numGas = "0.01";
                seekbar.setProgress(0);
                tvGas.setText(gas + " BIU");
                if (type == 0) {
                    edSenMoney.setText(tokenValue);
                } else {
                    String tempMoney = (Double.parseDouble(biuAmount) - gas) + "";
                    tempMoney = Util.getStringFromSN(8, tempMoney);
                    edSenMoney.setText(tempMoney);
                }
                break;
            case R.id.iv_close_top:
                finish();
                break;
        }
    }

    private void startImageByCamera() {
        //拍照点击拍照无反应 20160906 wp
        if (Build.VERSION.SDK_INT >= 23) {
            //摄像头权限检测
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        5);
                return;
            } else {
                startActivityForResult(new Intent(this, CaptureActivity.class), SWEEP);
            }
        } else {
            startActivityForResult(new Intent(this, CaptureActivity.class), SWEEP);
        }
    }

    //签名hash
    private String getSign() {
        SignDataBean signDataBean = new SignDataBean();
        signDataBean.setPrivateKey(remembBIUT.getPrivateKey());
        signDataBean.setFrom(remembBIUT.getAddress().substring(2));
        signDataBean.setTo(edToadress.getText().toString().trim().substring(2));
        signDataBean.setValue(edSenMoney.getText().toString());
        signDataBean.setInputData(edRemark.getText().toString().trim());
        String jsonDataStr = JSON.toJSONString(signDataBean);
        return secJsApi.TxSign(jsonDataStr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 101:
                if (resultCode == 1) {
                    if (null != data) {
                        String result = data.getExtras().getString("result");
                        if (!StringUtils.isEmpty(result)) {
                            String[] resultArray = result.split("###");
                            if (resultArray != null && resultArray.length > 0) {
                                myGetAdress = resultArray[0];
                                edToadress.setText(myGetAdress);
                                String money = resultArray[1];
                                if (!StringUtils.isEmpty(money)) {
                                    setMoney = Double.parseDouble(resultArray[1]);
                                    edSenMoney.setText(setMoney + "");
                                } else {
                                    setMoney = 0.00;
                                    edSenMoney.setText(setMoney + "");
                                }
                                checkAddress(myGetAdress);
                                checkMoney(money);
                                setBtnClickable();
                            }
                        }
                    }
                }
                if (resultCode == 2) {
                    if (null != data) {
                        myGetAdress = data.getExtras().getString("address");
                        if (!StringUtils.isEmpty(address)) {
                            edToadress.setText(myGetAdress);
                            checkAddress(myGetAdress);
                            checkMoney(myGetAmount);
                            setBtnClickable();
                        }
                    }
                }
                break;
            case 001://来自密码校验
                if (resultCode == 1) {
                    if (null != data) {
                        isCheckedPass = data.getExtras().getBoolean("isCheckedPass");
                        if (isCheckedPass) {
                            dialog.dismiss();
                            biutFront();
                        }
                    }
                }
                break;
            case SWEEP: //二维码扫描结果
                String result = "";
                try {
                    result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                    ReceiptBean receiptBean = JSON.parseObject(result, ReceiptBean.class);
                    int theType = receiptBean.getType();
                    checkType(theType);
                    if (isTypeOk == false) {
                        break;
                    }
                    myGetAmount = receiptBean.getValue();
                    myGetAmount = Util.getStringFromSN(8, myGetAmount);
                    myGetAdress = receiptBean.getAddress();
                    if (myGetAdress.length() == 40 && !myGetAdress.substring(0, 2).equals("0x")) {
                        myGetAdress = "0x" + myGetAdress;
                    }
                    checkMoney(myGetAmount);
                    checkAddress(myGetAdress);
                    setBtnClickable();
                    edToadress.setText(myGetAdress);
                    edSenMoney.setText(myGetAmount);
                } catch (Exception e) {
                    ToastUtil.show(this, R.string.scan_error);
                }
                break;
            default:
                break;
        }

    }

    public void showTradeDetailDialog(final Activity context) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(R.layout.dialog_trade_detail);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        //测量宽高
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            mWidth = dm.widthPixels;
//            mHeight = dm.heightPixels;
        } else {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            mWidth = metrics.widthPixels;
//            mHeight = metrics.heightPixels;
        }
        dialogWindow.setLayout(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        TextView tv_to = dialog.findViewById(R.id.tv_to);
        TextView tv_from = dialog.findViewById(R.id.tv_from);
        TextView tv_money = dialog.findViewById(R.id.tv_money);
        TextView tv_sure = dialog.findViewById(R.id.tv_sure);
        TextView tv_gas = dialog.findViewById(R.id.tv_gas);
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        TextView tv_info = dialog.findViewById(R.id.tv_info);
        TextView tv_kind = dialog.findViewById(R.id.kind);
        TextView tv_to_address = dialog.findViewById(R.id.tv_to_address);
        TextView tv_from_address = dialog.findViewById(R.id.tv_from_address);
        TextView tv_amount = dialog.findViewById(R.id.tv_amount);
        TextView tv_fee = dialog.findViewById(R.id.tv_fee);
        Util.setFontType(this, tv_title, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tv_info, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_kind, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_to_address, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_from_address, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_amount, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_fee, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_to, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_from, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_money, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_gas, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tv_sure, 1, LATO_BOLD_TTF);

        tv_to.setText(myGetAdress);
        tv_from.setText(remembBIUT.getAddress());
        tv_money.setText(edSenMoney.getText().toString().trim() + " " + title);
        if (numGas.equals("0")) {
            tv_gas.setText("0.02" + " BIU");
        } else {
            tv_gas.setText(numGas + " BIU");
        }
        tv_sure.setOnClickListener(view -> {
            UiHelper.startCheckPassActivity(TransferActivity.this, remembBIUT.getPass(), 001);
            dialog.dismiss();
        });
        iv_close.setOnClickListener(v -> dialog.dismiss());
    }

    private void biutFront() {
        dialogLoading.show();
        String tx = getSign();
        paramsBean = JSON.parseObject(tx, BiutSendRawBean.ParamsBean.class);
        try {
            tranBiut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void tranBiut() {
        SendRawBean.ParamsBean paramsBean2 = new SendRawBean.ParamsBean();
        paramsBean2.setData(paramsBean.getData());
        paramsBean2.setFrom(paramsBean.getFrom());
        paramsBean2.setGasLimit(paramsBean.getGasLimit());
        paramsBean2.setInputData(paramsBean.getInputData());
        paramsBean2.setGasPrice(paramsBean.getGasPrice());
        paramsBean2.setTimestamp(paramsBean.getTimestamp());
        paramsBean2.setTo(paramsBean.getTo());
        paramsBean2.setGas(paramsBean.getGas());
        paramsBean2.setTxFee(numGas);
        paramsBean2.setValue(paramsBean.getValue());
        List<SendRawBean.ParamsBean> list = new ArrayList<>();
        list.add(paramsBean2);
        SendRawBean bean = new SendRawBean();
        bean.setId(1);
        bean.setJsonrpc("2.0");
        bean.setMethod("sec_sendRawTransaction");
        bean.setParams(list);
        String json = JSON.toJSONString(bean);
        String url;
        if (type == 0) {
            url = RequestHost.biut_url;
        } else {
            url = RequestHost.biu_url;
        }
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(json);
        Log.e("web33", json);
        new Thread(() -> x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("web33", result);
                if (!StringUtils.isEmpty(result)) {
                    BIUTTransResponseBean transResponseBean = JSON.parseObject(result, BIUTTransResponseBean.class);
                    if (null != transResponseBean) {
                        BIUTTransResponseBean.ResultBean resultBean = transResponseBean.getResult();
                        if (null != resultBean) {
                            String statrus = resultBean.getStatus();//  1 成功 0 不成功，答应info
                            String info = resultBean.getInfo();
                            String txHash = resultBean.getTxHash();
                            if (!StringUtils.isEmpty(statrus) && statrus.equals("1")) {
                                if (!StringUtils.isEmpty(txHash) && txHash.length() >= 64) {
                                    String myRemark = edRemark.getText().toString().trim();
                                    if (!StringUtils.isEmpty(myRemark)) {
                                        if (null == tranRemark) {
                                            tranRemark = new HashMap<>();
                                        }
                                        tranRemark.put(txHash, myRemark);
                                        PlatformConfig.putMap(Constant.SpConstant.TRANSREMARKS, tranRemark);
                                    }
                                    Intent intent = new Intent(TransferActivity.this, TransactionRecordBiutActivity.class);
                                    intent.putExtra("reduceMoney", paramsBean.getValue());
                                    intent.putExtra("gas", numGas);
                                    setResult(100, intent);
                                    finish();
                                } else {
//                                            ToastUtil.show(TransferActivity.this, "转账失败");
                                }
                            } else {
                                DialogUtil.showErrorDialog(TransferActivity.this, info);
                            }
                        }
                        dialogLoading.dismiss();
                        dialog.dismiss();
                    } else {
                        dialogLoading.dismiss();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("web3", ex.toString());
                dialogLoading.dismiss();
                DialogUtil.showErrorDialog(TransferActivity.this, "transfer failed");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                Log.e("web3", "onFinished");
            }
        })).start();

    }
}
