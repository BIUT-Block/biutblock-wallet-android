package com.hualianzb.biut.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hualianzb.biut.R;
import com.hualianzb.biut.commons.constants.Constant;
import com.hualianzb.biut.commons.interfaces.GlobalMessageType;
import com.hualianzb.biut.models.AddressBookBean;
import com.hualianzb.biut.ui.activitys.TransferActivity;
import com.hualianzb.biut.utils.StringUtils;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hysd.android.platform_huanuo.base.config.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_MEDIUM_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_REGULAR_TTF;

/**
 * Date:2018/10/17
 * auther:wangtianyun
 * describe:
 */
public class AdapterAddressBook extends BaseAdapter {
    private List<AddressBookBean> list;
    private Activity context;
    Handler handler;
    private boolean isFromMy;

    public AdapterAddressBook(Activity context, Handler handler, boolean isFromMy) {
        this.context = context;
        this.list = new ArrayList<>();
        this.handler = handler;
        this.isFromMy = isFromMy;
    }

    public void setList(List<AddressBookBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_book, parent, false);
            holder.rl_change = convertView.findViewById(R.id.rl_change);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_address = convertView.findViewById(R.id.tv_address);
            holder.tv_phone = convertView.findViewById(R.id.tv_phone);
            holder.tv_cn_property = convertView.findViewById(R.id.tv_cn_property);
            holder.ll_delete = convertView.findViewById(R.id.ll_delete);
            holder.rl_item = convertView.findViewById(R.id.rl_item);
            holder.line = convertView.findViewById(R.id.line);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AddressBookBean bean = list.get(position);
        if (null != bean) {
            holder.tv_name.setText(bean.getName());
            holder.tv_address.setText(bean.getAddress().substring(0, 8) + "…" + bean.getAddress().substring(34, 42));
            if (StringUtils.isEmpty(bean.getPhone())) {
                holder.tv_phone.setVisibility(View.GONE);
            } else {
                holder.tv_phone.setText(bean.getPhone());
            }
        }
        holder.ll_delete.setOnClickListener(v -> {
            list.remove(position);
            PlatformConfig.putList(Constant.SpConstant.ADDRESSBOOK, list);
            handler.sendEmptyMessage(GlobalMessageType.MessgeCode.NOTIFYBOOKLIST);
        });
        holder.rl_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.startChangeAddressBookActys(context, position);
            }
        });
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFromMy) {
                    Intent intent = new Intent(context, TransferActivity.class);
                    intent.putExtra("address", list.get(position).getAddress());
                    context.setResult(2, intent);
                    context.finish();
                }
            }
        });
        Util.setFontType(context, holder.tv_name, 1, LATO_BOLD_TTF);
        Util.setFontType(context, holder.tv_phone, 1, LATO_BOLD_TTF);
        Util.setFontType(context, holder.tv_address, 1, MONTSERRAT_REGULAR_TTF);
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_phone, tv_address, tv_cn_property;
        RelativeLayout rl_item, rl_change;
        LinearLayout ll_delete;
        View line;
    }
}
