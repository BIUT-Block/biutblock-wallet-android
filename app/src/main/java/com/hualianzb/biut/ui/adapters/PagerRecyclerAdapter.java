package com.hualianzb.biut.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUM_PFB_TTF;

/**
 * Date:2018/10/22
 * auther:wangtianyun
 * describe:首页切卡的适配器
 */
public class PagerRecyclerAdapter extends RecyclerView.Adapter<PagerRecyclerAdapter.ViewHolder> {
    private List<RemembBIUT> rememberBIUTList;
    private Context context;
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public PagerRecyclerAdapter(Context context) {
        rememberBIUTList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pager, parent, false);
        ViewHolder vh = new ViewHolder(view);
        Util.setFontType(context, vh.tvName, 1, MONTSERRAT_MEDIUM_PFB_TTF);
        Util.setFontType(context, vh.tvProperty, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(context, vh.tvAddress, 1, LATO_REGULAR_WOFF_TTF);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RemembBIUT rememberBIUT = rememberBIUTList.get(position);
        if (rememberBIUT != null) {
            holder.tvName.setText(rememberBIUT.getWalletName());
            if (rememberBIUT.getHowToCreate() == 1) {//创建的
                if (rememberBIUT.getIsBackup()) {//已经保存了
                    holder.tvBack.setVisibility(View.GONE);
                } else {
                    holder.tvBack.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tvBack.setVisibility(View.GONE);
            }
            String addrss = rememberBIUT.getAddress();
            holder.tvAddress.setText(addrss.substring(0, 10) + "…" + addrss.substring(addrss.length() - 10, addrss.length()));
            money = getMoney();
            if (!StringUtils.isEmpty(money)) {
                if (money.equals("0")) {
                    holder.tvProperty.setText(money + " BIUT");
                    return;
                } else {
                    money = Util.getStringFromSN(8, money);
                    holder.tvProperty.setText(money + " BIUT");
                }
            } else {
                holder.tvProperty.setText("0" + " BIUT");
            }
        }
        holder.ivCode.setOnClickListener(v -> UiHelper.startMakeCodeActivity(context, rememberBIUTList.get(position).getAddress(), 0));
        holder.ll_base.setOnClickListener(v -> UiHelper.startManagerWalletActy(context));
    }

    @Override
    public int getItemCount() {
        return rememberBIUTList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void setData(List<RemembBIUT> rememberBIUTList) {
        this.rememberBIUTList = rememberBIUTList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tvBack)
        TextView tvBack;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_code)
        ImageView ivCode;
        @BindView(R.id.tv_property)
        TextView tvProperty;
        @BindView(R.id.ll_base)
        LinearLayout ll_base;

        ViewHolder(View view) {
            super(view.getRootView());
            ButterKnife.bind(this, view);
        }
    }
}

