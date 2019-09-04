package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.ResultInChainBeanOrPool;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.NetUtil;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.TimeUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BLACK_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2018/8/19
 * auther:wangtianyun
 * describe:交易记录的详情
 */
public class TransactionRecordDetailAllActivity extends BasicActivity {
    @BindView(R.id.iv_back_top)
    ImageView ivBack;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_send_address)
    TextView tvSendAddress;
    @BindView(R.id.tv_get_address)
    TextView tvGetAddress;
    @BindView(R.id.tv_gas)
    TextView tvGas;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.tv_trade_num)
    TextView tvTradeNum;
    @BindView(R.id.tv_block)
    TextView tvBlock;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_toWeb)
    LinearLayout ll_toWeb;
    @BindView(R.id.iv_avater)
    ImageView ivAvater;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.ll_normal)
    LinearLayout llNormal;
    @BindView(R.id.tv_block_hash)
    TextView tvBlockHash;
    @BindView(R.id.tv_blocks)
    TextView tvBlocks;
    @BindView(R.id.tv_mined_by)
    TextView tvMinedBy;
    @BindView(R.id.tv_mined_time)
    TextView tvMinedTime;
    @BindView(R.id.tv_reward)
    TextView tvReward;
    @BindView(R.id.ll_mined)
    LinearLayout llMined;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_web_net)
    TextView tvWebNet;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_to)
    TextView tvTo;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_trans_num)
    TextView tvTransNum;
    @BindView(R.id.tv_block_tip)
    TextView tvBlockTip;
    @BindView(R.id.tv_trans_date)
    TextView tvTransDate;
    @BindView(R.id.tv_block_hash_tip)
    TextView tvBlockHashTip;
    @BindView(R.id.tv_mined_tip)
    TextView tvMinedTip;
    @BindView(R.id.tv_mined_time_tip)
    TextView tvMinedTimeTip;
    @BindView(R.id.tv_reward_tip)
    TextView tvRewardTip;
    @BindView(R.id.tv_block_top)
    TextView tvBlockTop;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.re_title)
    RelativeLayout reTitle;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    private String address;
    private ResultInChainBeanOrPool resultBean;
    private String today = TimeUtil.getDay();
    private String type;
    private long theId;
    private String kind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_record_detail);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.bg_detail).statusBarDarkFont(true).init();
        initData();
    }

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            address = bundle.getString("address");
            theId = bundle.getLong("theId");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            address = savedInstanceState.getString("address");
            theId = savedInstanceState.getLong("theId");
        }
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvMoney, 1, LATO_BLACK_PFB_TTF);
        Util.setFontType(this, tvState, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvFrom, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvSendAddress, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTo, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvGetAddress, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvFee, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvGas, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRemark, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvRemarks, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTransNum, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTradeNum, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvBlockTop, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvBlocks, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvTransDate, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvDate, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvRewardTip, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvBlock, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvReward, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvBlockTip, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvMinedTip, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvBlockHashTip, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvMinedTimeTip, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvWebNet, 1, LATO_REGULAR_WOFF_TTF);
    }

    private void initData() {
        tvRight.setVisibility(View.GONE);
        reTitle.setBackgroundColor(getResources().getColor(R.color.bg_tra_detail));
        setFontStyle();
        resultBean = BIUTApplication.recordResulttDao.load(theId);
        type = resultBean.getType();
        if (type.equals("0")) {
            kind = "BIUT";
            ivLogo.setBackgroundResource(R.drawable.icon_biut);
        } else {
            kind = "BIU";
            ivLogo.setBackgroundResource(R.drawable.icon_biu);
        }
        tvWebNet.setText("See more details at " + kind + " BLOCKCHAIN");
        String money = resultBean.getValue();
        money = Util.getStringFromSN(8, money);
        tvMoney.setText(money + " " + kind);
        llMined.setVisibility(View.GONE);
        String status = resultBean.getTxReceiptStatus();
        String from = resultBean.getTxFrom();
        String to = resultBean.getTxTo();
        String txFee = resultBean.getTxFee();
        String remarks = resultBean.getInputData();
        String transNumber = resultBean.getTxHash();
        String block = resultBean.getBlockNumber() + "";
        long time_Stamp = resultBean.getTimeStamp();

        switch (status) {
            case "pending":
                ivAvater.setImageResource(R.drawable.icon_trans_ing);
                tvMoney.setTextColor(getResources().getColor(R.color.text_yellow02));
                tvState.setTextColor(getResources().getColor(R.color.text_yellow02));
                tvBlocks.setText("Not in Block yet");
                tvState.setText("Transfer Pending");
                break;
            case "success":
                if (from.contains("00000000000000000")) {
                    status = "mined";
                    tvMoney.setTextColor(getResources().getColor(R.color.mineBlue));
                    tvState.setTextColor(getResources().getColor(R.color.mineBlue));
                    if (to.equals(address.substring(2))) {
                        ivAvater.setImageResource(R.drawable.icon_mined);
                        tvState.setText("Mined");
                        llMined.setVisibility(View.VISIBLE);
                        llNormal.setVisibility(View.GONE);
                        llBottom.setVisibility(View.GONE);
                        rlBottom.setBackgroundColor(getResources().getColor(R.color.white));
                        tvBlockHash.setText(resultBean.getBlockHash());
                        tvBlocks.setText(resultBean.getBlockNumber() + "");
                        tvMinedBy.setText(address);
                        tvReward.setText("+ " + tvMoney.getText());
                    }
                } else {
                    tvMoney.setTextColor(getResources().getColor(R.color.text_selected_green));
                    tvState.setTextColor(getResources().getColor(R.color.text_selected_green));
                    if (to.equals(address.substring(2))) {
                        ivAvater.setImageResource(R.drawable.icon_trans_received);
                        tvState.setText("Receive Successful");
                    } else {
                        ivAvater.setImageResource(R.drawable.icon_send_success);
                        tvState.setText("Send Successful");
                    }
                }
                break;
            case "failed":
                ivAvater.setImageResource(R.drawable.icon_trans_failed);
                tvMoney.setTextColor(getResources().getColor(R.color.text_error));
                tvState.setTextColor(getResources().getColor(R.color.text_error));
                tvState.setText("Transfer Failed");
                break;
        }
        if (status.equals("mined")) {
            ll_toWeb.setBackgroundResource(R.drawable.bg_normal_white_gray_10);
        }
        if (to.equals(address.substring(2))) {
            tvMoney.setText("+" + tvMoney.getText());
        } else {
            tvMoney.setText("-" + tvMoney.getText());
        }

        tvSendAddress.setText("0x" + from);
        tvGetAddress.setText("0x" + to);
        tvGas.setText(txFee + " BIU");
        tvTradeNum.setText("0x" + transNumber.substring(0, 8) + "…" + transNumber.substring((transNumber.length() - 10), transNumber.length()));
        tvBlock.setText(block.equals("0") || StringUtils.isEmpty(block) ? "Not in Block yet" : block);

        if (StringUtils.isEmpty(remarks) || remarks.length() > 15) {
            llRemark.setVisibility(View.GONE);
        } else {
            tvRemarks.setText(remarks);
        }
        String timeTemp = TimeUtil.getTime12(time_Stamp);
        if (timeTemp.equals(today)) {
            tvDate.setText(TimeUtil.getTime11(time_Stamp));
            tvMinedTime.setText(TimeUtil.getTime11(time_Stamp));
        } else {
            tvDate.setText(TimeUtil.getTime2(time_Stamp));
            tvMinedTime.setText(TimeUtil.getTime2(time_Stamp));
        }


        if (status.equals("pending")) {
            ll_toWeb.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_toWeb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toWeb:
                boolean netOk = isNetworkAvailable(this);
                reGo(netOk);
                break;
        }
    }

    private void reGo(boolean netOk) {
        NetUtil.getNetWorkState(this);
        UiHelper.startWebActivity(this, resultBean.getTxHash());
    }

}
