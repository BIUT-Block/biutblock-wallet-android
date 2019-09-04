package com.hualianzb.biut.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hualianzb.biut.R;
import com.hualianzb.biut.models.ResultInChainBeanOrPool;
import com.hualianzb.biut.utils.TimeUtil;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;

public class AdapterTradeRecordEach extends BaseAdapter {
    private Context context;
    private String address;
    private String today, titie;
    private List<ResultInChainBeanOrPool> listAll;

    public AdapterTradeRecordEach(Context context, String address, String titie) {
        this.context = context;
        this.address = address;
        this.titie = titie;
        listAll = new ArrayList<>();
        today = TimeUtil.getDay();
    }

    public void setData(List<ResultInChainBeanOrPool> listAll) {
        this.listAll = Util.listSortRecord(listAll);//排序
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listAll.size();
    }

    @Override
    public Object getItem(int position) {
        return listAll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trade_record2, parent, false);
            holder.ivLogo = convertView.findViewById(R.id.iv_logo);
            holder.tvBalance = convertView.findViewById(R.id.tv_balance);
            holder.tvKind = convertView.findViewById(R.id.tv_kind);
            holder.tvDate = convertView.findViewById(R.id.tv_date);
            holder.tv_state = convertView.findViewById(R.id.tv_state);
            holder.tvAddress = convertView.findViewById(R.id.tv_address);
            holder.line = convertView.findViewById(R.id.line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ResultInChainBeanOrPool bean = listAll.get(position);
        if (null != bean) {
            String from = bean.getTxFrom();
            String status = bean.getTxReceiptStatus();
            String to = bean.getTxTo();
            String money = Util.getStringFromSN(8, bean.getValue()) + " " + titie;
            long time_Stamp = bean.getTimeStamp();
            String tvStringStatus;
            if (from.equals(address.substring(2))) {
                holder.tvAddress.setText("0x" + to.substring(0, 10) + "…" + to.substring((to.length() - 10), to.length()));
            } else {
                holder.tvAddress.setText("0x" + from.substring(0, 10) + "…" + from.substring((from.length() - 10), from.length()));
            }
            switch (status) {
                case "pending":
                    tvStringStatus = "(Pending)";
                    holder.tvBalance.setTextColor(context.getResources().getColor(R.color.text_yellow02));
                    holder.tv_state.setTextColor(context.getResources().getColor(R.color.text_yellow02));
                    holder.tv_state.setText(tvStringStatus);
                    holder.ivLogo.setImageResource(R.drawable.icon_trans_ing);
                    holder.tv_state.setVisibility(View.VISIBLE);
                    break;
                case "success":
                    holder.tv_state.setVisibility(View.GONE);
                    holder.tv_state.setTextColor(context.getResources().getColor(R.color.text_yellow02));
                    holder.tvBalance.setTextColor(context.getResources().getColor(R.color.text_selected_green));
                    if (from.contains("000000000000")) {//挖矿
                        tvStringStatus = "Mined";
                        holder.tvBalance.setTextColor(context.getResources().getColor(R.color.mineBlue));
                        holder.tv_state.setTextColor(context.getResources().getColor(R.color.mineBlue));
                        holder.tv_state.setText(tvStringStatus);
                        holder.ivLogo.setImageResource(R.drawable.icon_mined);
                    } else {
                        if (to.equals(address.substring(2))) {
                            holder.ivLogo.setImageResource(R.drawable.icon_trans_received);
                        } else {
                            holder.ivLogo.setImageResource(R.drawable.icon_send_success);
                        }
                    }
                    break;
                case "failed":
                    tvStringStatus = "Failed";
                    holder.tvBalance.setTextColor(context.getResources().getColor(R.color.text_error));
                    holder.tv_state.setTextColor(context.getResources().getColor(R.color.text_error));
                    holder.tv_state.setText(tvStringStatus);
                    holder.ivLogo.setImageResource(R.drawable.icon_trans_failed);
                    break;
            }
            if (to.equals(address.substring(2))) {
                money = "+" + money;
            } else {
                money = "-" + money;
            }
            holder.tvBalance.setText(money);
            String timeTemp = TimeUtil.getTime12(time_Stamp);
            if (timeTemp.equals(today)) {
                holder.tvDate.setText(TimeUtil.getTime11(time_Stamp));
            } else {
                holder.tvDate.setText(TimeUtil.getTime2(time_Stamp));
            }
        }
        Util.setFontType(context, holder.tvAddress, 1, LATO_BOLD_TTF);
        Util.setFontType(context, holder.tvDate, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(context, holder.tvBalance, 1, LATO_BOLD_TTF);
        Util.setFontType(context, holder.tv_state, 1, LATO_REGULAR_WOFF_TTF);
        return convertView;
    }


    static class ViewHolder {
        ImageView ivLogo;
        TextView tvAddress, tvBalance, tvKind, tvDate, tv_state;
        View line;
    }
}

