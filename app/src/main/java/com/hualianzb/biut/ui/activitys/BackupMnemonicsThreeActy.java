package com.hualianzb.biut.ui.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.models.MnemonicsBean;
import com.hualianzb.biut.models.RemembBIUT;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.DialogUtil;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;
import com.hysd.android.platform_huanuo.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BOLD_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class BackupMnemonicsThreeActy extends BasicActivity {
    @BindView(R.id.ll_my_mns)//上面
            LinearLayout llMyMns;
    @BindView(R.id.ll_old_mns)//下面
            LinearLayout llOldMns;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    private String myMnemonics, address;
    private RemembBIUT remembBIUT;
    private List<String> list_my_mns;
    private List<MnemonicsBean> listMNs;
    private Dialog dialogTips, twoButtonTipsDialog;
    private List<RemembBIUT> list;

    @Override
    protected void getIntentForBundle() {
        super.getIntentForBundle();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            myMnemonics = bundle.getString("myMnemonics");
            address = bundle.getString("address");
        }
    }

    @Override
    protected void getIntentForSavedInstanceState(Bundle savedInstanceState) {
        super.getIntentForSavedInstanceState(savedInstanceState);
        myMnemonics = savedInstanceState.getString("myMnemonics");
        address = savedInstanceState.getString("address");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankup_mn3);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        tvRight.setVisibility(View.GONE);
        tvTheme.setText("Backup Phrase");
        setFontStyle();
        list = BIUTApplication.dao_remeb.loadAll();
        for (RemembBIUT remembBIUT : list) {
            if (address.equals(remembBIUT.getAddress())) {
                this.remembBIUT = remembBIUT;
                break;
            }
        }

        dialogTips = DialogUtil.TipsDialog(this, R.drawable.error_icon, "Backup Failed",
                0, "Please check your phrase", (v, d) -> d.dismiss());
        twoButtonTipsDialog = DialogUtil.twoButtonTipsDialog(this, R.drawable.icon_confire_blue,
                "Are you sure",
                getString(R.string.are_you_sure), (v, d) -> {
                    remembBIUT.setMnemonics("");
                    remembBIUT.setIsBackup(true);//删除了就是已经备份了
                    BIUTApplication.dao_remeb.update(remembBIUT);
                    twoButtonTipsDialog.dismiss();
                    UiHelper.startHomaPageAcB(BackupMnemonicsThreeActy.this, address, 1000);
                });
        list_my_mns = new ArrayList<>();
        listMNs = new ArrayList<>();
        String myString[] = myMnemonics.split(" ");
        for (int i = 0; i < myString.length; i++) {
            MnemonicsBean bean = new MnemonicsBean();
            bean.setMnemonicsItem(myString[i]);
            bean.setSelected(false);
            listMNs.add(bean);
        }
        shulf(listMNs);
        initAutoLL(llOldMns);
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTheme, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvOne, 1, LATO_BOLD_TTF);
        Util.setFontType(this, tvTwo, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvSure, 1, LATO_BOLD_TTF);
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                String now = getHYString();
                if (now.equals(myMnemonics)) {
                    twoButtonTipsDialog.show();
                } else {
                    dialogTips.show();
                }
                break;
        }
    }

    //选中的
    private void initAutoLLMy(final LinearLayout ll_parent) {
        if (list_my_mns.size() > 0) {
            ll_parent.setVisibility(View.VISIBLE);
            if (list_my_mns.size() >= 12) {
                tvSure.setEnabled(true);
                tvSure.setBackground(getResources().getDrawable(R.drawable.bg_btn));
            } else {
                tvSure.setEnabled(false);
                tvSure.setBackground(getResources().getDrawable(R.drawable.bg_btn_cannot));
            }
        }
        ll_parent.removeAllViews();
//        每一行的布局，初始化第一行布局
        LinearLayout rowLL = new LinearLayout(this);
        LinearLayout.LayoutParams rowLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        float rowMargin = dipToPx(12);
        float rowMargin = 24;
        rowLP.setMargins((int) rowMargin, (int) rowMargin, (int) rowMargin, 0);
        rowLL.setLayoutParams(rowLP);
        boolean isNewLayout = false;
        float maxWidth = getScreenWidth() - 148;
//        剩下的宽度
        float elseWidth = maxWidth;
        LinearLayout.LayoutParams ll_LP =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
//        ll_LP.setMargins((int) dipToPx(8), 0, 0, 0);
        ll_LP.setMargins(16, 0, 0, 0);
        for (int i = 0; i < list_my_mns.size(); i++) {
//            若当前为新起的一行，先添加旧的那行
//            然后重新创建布局对象，设置参数，将isNewLayout判断重置为false
            if (isNewLayout) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(this);
                rowLL.setLayoutParams(rowLP);
                rowLL.setLayoutParams(rowLP);
                maxWidth = getScreenWidth() - 148;
//        剩下的宽度
                elseWidth = maxWidth;
                ll_LP.setMargins(16, 0, 0, 0);
                isNewLayout = false;
            }
//            计算是否需要换行
            TextView tv_Items = (TextView) getLayoutInflater().inflate(R.layout.layout_selected_mns, null);
            Util.setFontType(this, tv_Items, 1, LATO_REGULAR_WOFF_TTF);
            tv_Items.setText(list_my_mns.get(i).trim());
            tv_Items.measure(0, 0);
//            若是一整行都放不下这个文本框，添加旧的那行，新起一行添加这个文本框
            if (maxWidth < tv_Items.getMeasuredWidth()) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(this);
                rowLL.setLayoutParams(rowLP);
                rowLL.addView(tv_Items);
                isNewLayout = true;
                continue;
            }
//            若是剩下的宽度小于文本框的宽度（放不下了）
//            添加旧的那行，新起一行，但是i要-1，因为当前的文本框还未添加
            if (elseWidth < tv_Items.getMeasuredWidth()) {
                isNewLayout = true;
                i--;
//                重置剩余宽度
                elseWidth = maxWidth;
                continue;
            } else {
//                剩余宽度减去文本框的宽度+间隔=新的剩余宽度
                elseWidth = elseWidth - tv_Items.getMeasuredWidth() - 16;
                if (rowLL.getChildCount() == 0) {
                    rowLL.addView(tv_Items);
                } else {
                    if (elseWidth > 0) {
                        tv_Items.setLayoutParams(ll_LP);
                        rowLL.addView(tv_Items);
                    } else {
                        isNewLayout = true;
                        i--;
                    }
                }
            }
            final int finalI = i;
            tv_Items.setOnLongClickListener(v -> {
                for (MnemonicsBean bean : listMNs) {
                    if (bean.getMnemonicsItem().equals(list_my_mns.get(finalI))) {
                        bean.setSelected(false);
                        break;
                    }
                }
                list_my_mns.remove(finalI);
                shulf(listMNs);
                if (null == list_my_mns || list_my_mns.size() == 0) {
                    llMyMns.removeAllViews();
                    llMyMns.setVisibility(View.GONE);
                } else {
                    llMyMns.setVisibility(View.VISIBLE);
                    initAutoLLMy(llMyMns);
                }
                initAutoLL(llOldMns);
                return false;
            });
        }
//        添加最后一行，但要防止重复添加
        ll_parent.removeView(rowLL);
        ll_parent.addView(rowLL);
    }

    //下面的
    private void initAutoLL(final LinearLayout ll_parent) {
        ll_parent.removeAllViews();
        LinearLayout rowLL = new LinearLayout(this);
        LinearLayout.LayoutParams rowLP =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        float rowMargin = 24;
        rowLP.setMargins(0, (int) rowMargin, 0, 0);
        rowLL.setLayoutParams(rowLP);
        boolean isNewLayout = false;
        float maxWidth = getScreenWidth() - 100;
//        剩下的宽度
        float elseWidth = maxWidth;
        LinearLayout.LayoutParams llLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llLP.setMargins(16, 0, 0, 0);
        for (int i = 0; i < listMNs.size(); i++) {
//            若当前为新起的一行，先添加旧的那行
//            然后重新创建布局对象，设置参数，将isNewLayout判断重置为false
            if (isNewLayout) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(this);
                rowLL.setLayoutParams(rowLP);
                rowLL.setLayoutParams(rowLP);
                maxWidth = getScreenWidth() - 100;
//        剩下的宽度
                elseWidth = maxWidth;
                llLP.setMargins(16, 0, 0, 0);
                isNewLayout = false;
            }

//            计算是否需要换行
            TextView tv_items = (TextView) getLayoutInflater().inflate(R.layout.layout_mn_bottom, null);
            Util.setFontType(this, tv_items, 1, LATO_REGULAR_WOFF_TTF);
            tv_items.setText(listMNs.get(i).getMnemonicsItem().trim());
            tv_items.measure(0, 0);
//            若是一整行都放不下这个文本框，添加旧的那行，新起一行添加这个文本框
            if (maxWidth < tv_items.getMeasuredWidth()) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(this);
                rowLL.setLayoutParams(rowLP);
                rowLL.addView(tv_items);
                isNewLayout = true;
                continue;
            }
//            若是剩下的宽度小于文本框的宽度（放不下了）
//            添加旧的那行，新起一行，但是i要-1，因为当前的文本框还未添加
            if (elseWidth < tv_items.getMeasuredWidth()) {
                isNewLayout = true;
                i--;
//                重置剩余宽度
                elseWidth = maxWidth;
                continue;
            } else {
//                剩余宽度减去文本框的宽度+间隔=新的剩余宽度
                elseWidth = elseWidth - tv_items.getMeasuredWidth() - 16;
                if (rowLL.getChildCount() == 0) {
                    rowLL.addView(tv_items);
                } else {
                    if (elseWidth > 0) {
                        tv_items.setLayoutParams(llLP);
                        rowLL.addView(tv_items);
                    } else {
                        isNewLayout = true;
                        i--;
                    }
                }
            }
            if (listMNs.get(i).isSelected()) {
                tv_items.setBackground(getResources().getDrawable(R.drawable.bg_blue_30));
                tv_items.setTextColor(getResources().getColor(R.color.white));
                tv_items.setEnabled(false);
            } else {
                tv_items.setBackground(getResources().getDrawable(R.drawable.bg_btn_importfromcreat));
                tv_items.setTextColor(getResources().getColor(R.color.text_selected_green));
                tv_items.setEnabled(true);
            }
            final int finalI = i;
            tv_items.setOnClickListener(v -> {
                if (!listMNs.get(finalI).isSelected()) {
                    list_my_mns.add(listMNs.get(finalI).getMnemonicsItem().trim());
                    listMNs.get(finalI).setSelected(true);
                    shulf(listMNs);
                    initAutoLL(ll_parent);
                    initAutoLLMy(llMyMns);
                }
            });
        }
//        添加最后一行，但要防止重复添加
        ll_parent.removeView(rowLL);
        ll_parent.addView(rowLL);
    }

    private String getHYString() {

        String nowString = "";
        for (int i = 0; i < list_my_mns.size(); i++) {
            if (StringUtils.isNotEmpty(nowString)) {
                nowString = nowString + " " + list_my_mns.get(i);
            } else {
                nowString = list_my_mns.get(i);
            }
        }
        return nowString;
    }

    //  获得屏幕宽度
    private float getScreenWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

    private void shulf(List<MnemonicsBean> list) {
        Collections.shuffle(list);
    }
}
