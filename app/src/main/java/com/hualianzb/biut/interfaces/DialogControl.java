package com.hualianzb.biut.interfaces;

import com.hualianzb.biut.views.WaitDialog;

/**
 * Created by wty on 2017/8/22.
 */

public interface DialogControl {
    void hideWaitDialog();
    WaitDialog showWaitDialog();
    WaitDialog showWaitDialog(int resid);
    WaitDialog showWaitDialog(String text);
}
