package com.hualianzb.biut.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.models.HomePoolBean;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.ToastUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

/**
 * Date:2019/8/5
 * auther:wangtianyun
 * describe:
 */
public class AdapterHomePool extends BaseAdapter {
    Context context;
    String address;
    String biut, biu;
    List<HomePoolBean> list;
    boolean hasjioned;

    public AdapterHomePool(Context context, String address, String biut, String biu) {
        this.context = context;
        this.address = address;
        this.biut = biut;
        this.biu = biu;
        this.list = new ArrayList<>();
    }

    public void setList(List<HomePoolBean> list) {
        this.list = list;
    }

    public void setHasjioned(boolean hasjioned) {
        this.hasjioned = hasjioned;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_pool, parent, false);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_day_profit = convertView.findViewById(R.id.tv_day_profit);
            holder.tv_jion = convertView.findViewById(R.id.tv_jion);
            Util.setFontType(context, holder.tv_name, 1, MONTSERRAT_BOLD_OTF);
            Util.setFontType(context, holder.tv_day_profit, 1, LATO_BOLD_TTF);
            Util.setFontType(context, holder.tv_jion, 1, LATO_REGULAR_WOFF_TTF);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomePoolBean homePoolBean = list.get(position);
        if (null != homePoolBean) {
            if (homePoolBean.getName().length() > 6) {
                holder.tv_name.setText(homePoolBean.getName().substring(0, 6) + "...");
            } else {
                holder.tv_name.setText(homePoolBean.getName());
            }
            holder.tv_day_profit.setText(homePoolBean.getDayprofit());
            if (hasjioned) {
                holder.tv_jion.setText("Joined");
                holder.tv_jion.setVisibility(View.GONE);
                holder.tv_jion.setEnabled(false);
                if (homePoolBean.isJion()) {
                    holder.tv_jion.setVisibility(View.VISIBLE);
                    holder.tv_jion.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_cannot));
                }
            } else {
                holder.tv_jion.setEnabled(true);
                holder.tv_jion.setText("Join");
                holder.tv_jion.setVisibility(View.VISIBLE);
                holder.tv_jion.setBackground(context.getResources().getDrawable(R.drawable.bg_btn));
            }
        }

        holder.tv_jion.setOnClickListener(v -> UiHelper.startJoinPoolActivity(context, address, biut));
//        holder.tv_jion.setOnClickListener(v -> DialogUtil.jionPoolDialog(context, biut, biu, (v1, d) -> ToastUtil.show(context, biut)));
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_day_profit, tv_jion;
    }
}