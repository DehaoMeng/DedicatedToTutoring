package com.example.myapplication.dialog;


import com.example.myapplication.ResourceTable;
import com.example.myapplication.utils.ResourceUtil;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.service.WindowManager;
import ohos.app.Context;

import java.util.Optional;

public class ListChooseDialog {
    private static final int MAX_BUTTON_NUMBER = 3;

    private static final int DIALOG_WIDTH_VP = 328;

    private final String[] mButtonTexts = new String[MAX_BUTTON_NUMBER];

    private final int[] mButtonTypes = new int[MAX_BUTTON_NUMBER];

    private final ClickedListener[] mButtonListeners = new ClickedListener[MAX_BUTTON_NUMBER];

    private String mTitle;

    private String mContent;

    private int mIconId;

    private int buttonNum = 0;

    private float dim = -1f;

    private boolean mOutsideTouchClosable = false;

    private CommonDialog mCommonDialog;

    public void show() {
        if (mCommonDialog != null) {
            mCommonDialog.show();
            if (dim >= 0) {
                changeDialogDim(mCommonDialog, dim);
            }
        }
    }

    public void remove(){
        if (mCommonDialog != null) {
            mCommonDialog.destroy();
        }
    }

    private void changeDialogDim(CommonDialog dialog, float dim) {
        Optional<WindowManager.LayoutConfig> configOpt = dialog.getWindow().getLayoutConfig();
        configOpt.ifPresent(config -> {
            config.dim = dim;
            dialog.getWindow().setLayoutConfig(config);
        });
    }

    public interface ClickedListener{
        void onClick(ListChooseDialog dialog);
    }

    public static class Builder {
        private final ListChooseDialog mGeneralDialog;

        private final Context mContext;

        public Builder(Context context) {
            mContext = context;
            mGeneralDialog = new ListChooseDialog();
        }

        public Builder setTitle(String title) {
            mGeneralDialog.mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            mGeneralDialog.mContent = content;
            return this;
        }

        public Builder setIcon(int id) {
            mGeneralDialog.mIconId = id;
            return this;
        }

        public Builder setDim(float dim){
            if (dim > 1) {
                mGeneralDialog.dim = 1;
            } else if (dim < 0) {
                mGeneralDialog.dim = 0;
            } else {
                mGeneralDialog.dim = dim;
            }
            return this;
        }

        public Builder setOutsideTouchClosable(boolean closable){
            mGeneralDialog.mOutsideTouchClosable = closable;
            return this;
        }

        public ListChooseDialog create() {
            CommonDialog sDialog = new CommonDialog(mContext);
            sDialog.setSize(AttrHelper.fp2px(DIALOG_WIDTH_VP, mContext), ComponentContainer.LayoutConfig.MATCH_CONTENT);
            sDialog.setAlignment(LayoutAlignment.BOTTOM);
            sDialog.setOffset(0, (int) ResourceUtil.getFloat(mContext, ResourceTable.Float_spacing_huge, 0));
            sDialog.setTransparent(true);
            sDialog.setContentCustomComponent(initDialog());
            sDialog.setAutoClosable(mGeneralDialog.mOutsideTouchClosable);
            mGeneralDialog.mCommonDialog = sDialog;
            return mGeneralDialog;
        }

        private Component initDialog() {
            return null;
        }
    }
}