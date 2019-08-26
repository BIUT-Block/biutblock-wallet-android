package com.hualianzb.biut.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.activitys.HomePageActivity;
import com.hualianzb.biut.ui.activitys.MakeMoneyActicity;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class AdapterWallets extends BaseAdapter {
    private Activity context;
    private List<RemembBIUT> list;
    private long id;
    private String pool;

    public AdapterWallets(Activity context, long id, String pool) {
        this.context = context;
        this.id = id;
        this.pool = pool;
        list = new ArrayList<>();
    }


    public void setList(List<RemembBIUT> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_change_wal, parent, false);
            holder.iv_logo = convertView.findViewById(R.id.iv_logo);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.ll_item = convertView.findViewById(R.id.ll_item);
            holder.iv_checked = convertView.findViewById(R.id.iv_checked);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RemembBIUT remembBIUT = list.get(position);
        if (null != remembBIUT) {
            String name = remembBIUT.getWalletName();
            if (!StringUtils.isEmpty(name)) {
                holder.tv_name.setText(name);
            }
            if (id == remembBIUT.getId()) {
                holder.ll_item.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_can_green));
                holder.tv_name.setTextColor(context.getResources().getColor(R.color.white));
                holder.iv_checked.setVisibility(View.VISIBLE);
            } else {
                holder.ll_item.setBackground(context.getResources().getDrawable(R.drawable.bg_edit_gray));
                holder.tv_name.setTextColor(context.getResources().getColor(R.color.text_black));
                holder.iv_checked.setVisibility(View.GONE);
            }
        }
        holder.ll_item.setOnClickListener(v -> {

            if (id == list.get(position).getId()) {
                return;
            } else {
                RemembBIUT old = BIUTApplication.daoSession.getRemembBIUTDao().load(id);
                old.setIsNow(false);
                BIUTApplication.daoSession.getRemembBIUTDao().update(old);
                //当前的钱包改为选中
                RemembBIUT newReme = list.get(position);
                newReme.setIsNow(true);
                BIUTApplication.daoSession.getRemembBIUTDao().update(old);
                if (StringUtils.isEmpty(pool)) {
                    context.finish();
                } else {
                    Intent intent = new Intent(context, HomePageActivity.class);
                    context.setResult(100, intent);
                    context.finish();
                }
            }
            notifyDataSetChanged();
        });

        Util.setFontType(context, holder.tv_name, 1, LATO_BOLD_TTF);
        return convertView;
    }


    class ViewHolder {
        ImageView iv_logo, iv_checked;
        TextView tv_name;
        LinearLayout ll_item;
    }

}