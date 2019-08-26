package com.hualianzb.biut.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.models.ProfirBean;
import com.hualianzb.biut.utils.Util;

import java.util.ArrayList;
import java.util.List;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;

/**
 * Date:2019/8/5
 * auther:wangtianyun
 * describe:
 */
public class AdapterProfit extends BaseAdapter {
    Context context;
    List<ProfirBean> list;

    public AdapterProfit(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setList(List<ProfirBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_profit, parent, false);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_profit = convertView.findViewById(R.id.tv_profit);
            Util.setFontType(context, holder.tv_date, 1, LATO_REGULAR_WOFF_TTF);
            Util.setFontType(context, holder.tv_profit, 1, LATO_BOLD_TTF);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProfirBean profirBean = list.get(position);
        if (null != profirBean) {
            holder.tv_date.setText(profirBean.getDate());
            String money = Util.getStringFromSN(3, profirBean.getProfit());
            holder.tv_profit.setText("+" + money + " BIU");
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_date, tv_profit;
    }
}