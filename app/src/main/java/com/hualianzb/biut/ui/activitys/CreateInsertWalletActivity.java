package com.hualianzb.biut.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hualianzb.biut.R;
import com.hualianzb.biut.application.BIUTApplication;
import com.hualianzb.biut.ui.basic.BasicActivity;
import com.hualianzb.biut.utils.UiHelper;
import com.hualianzb.biut.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_BLACK_PFB_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.LATO_REGULAR_WOFF_TTF;
import static com.hualianzb.biut.commons.interfaces.GlobalMessageType.TextFontType.MONTSERRAT_BOLD_OTF;

public class CreateInsertWalletActivity extends BasicActivity {
    @BindView(R.id.ll_create)
    LinearLayout llCreate;
    @BindView(R.id.ll_import)
    LinearLayout llImport;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_c_top)
    TextView tvCTop;
    @BindView(R.id.tv_c_bottom)
    TextView tvCBottom;
    @BindView(R.id.tv_i_top)
    TextView tvITop;
    @BindView(R.id.tv_i_bottom)
    TextView tvIBottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_insert);
        ButterKnife.bind(this);
        BIUTApplication.getInstance().addActivity(this);
        initView();
        setFontStyle();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.gray_background).init();
    }

    private void setFontStyle() {
        Util.setFontType(this, tvTitle, 1, MONTSERRAT_BOLD_OTF);
        Util.setFontType(this, tvCTop, 1, LATO_BLACK_PFB_TTF);
        Util.setFontType(this, tvCBottom, 1, LATO_REGULAR_WOFF_TTF);
        Util.setFontType(this, tvITop, 1, LATO_BLACK_PFB_TTF);
        Util.setFontType(this, tvIBottom, 1, LATO_REGULAR_WOFF_TTF);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick({R.id.ll_create, R.id.ll_import})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_create:
                UiHelper.startActyCreateWalletActivity(this);
                break;
            case R.id.ll_import:
                UiHelper.startActyImportWalletActivity(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
