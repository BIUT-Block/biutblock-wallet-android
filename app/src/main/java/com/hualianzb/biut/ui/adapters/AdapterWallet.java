package com.hualianzb.biut.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.models.TokenBean;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class AdapterWallet extends BaseAdapter {
    private Context context;
    private List<TokenBean> list;
    private String address;

    public AdapterWallet(Context context, String address) {
        this.context = context;
        this.address = address;
        list = new ArrayList<>();
    }

    public void setList(List<TokenBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_wallet, parent, false);
            holder.iv_avater = convertView.findViewById(R.id.iv_avater);
            holder.tv_kind = convertView.findViewById(R.id.tv_kind);
            holder.tv_money = convertView.findViewById(R.id.tv_money);
            holder.tv_cn_property = convertView.findViewById(R.id.tv_cn_property);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TokenBean tokenBean = list.get(position);
        String kind = tokenBean.getName();
        if (kind.toLowerCase().equals("biut")) {
            holder.iv_avater.setImageResource(R.drawable.icon_biut);
        } else {
            holder.iv_avater.setImageResource(R.drawable.icon_biu);
        }

        holder.tv_kind.setText(tokenBean.getName());
        String momeyStr = tokenBean.getToken();
        double money = Double.parseDouble(momeyStr);
        if (money == 0) {
            holder.tv_money.setText("0");
        } else if (money > 0) {
            momeyStr = Util.getStringFromSN(8, momeyStr);
            holder.tv_money.setText(momeyStr);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_avater;
        TextView tv_kind, tv_money, tv_cn_property;
    }
}

