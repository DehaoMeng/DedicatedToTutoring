package com.example.myapplication.dialog;

import com.example.myapplication.ResourceTable;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

public class PromptDialog {
    private static final int duration = 2000; // 消失时间·
    public static final int Success = ResourceTable.Media_success; // 成功的吐司弹窗的图片显示
    public static final int Danger = ResourceTable.Media_danger;   // 失败的吐司弹窗的图片显示
    public static final int promt = ResourceTable.Media_promt;      // 警告的吐司弹窗的图片显示

    public static void showDialog(Context context){
        DirectionalLayout d1= (DirectionalLayout) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_prompt_dialog,null,false);
        ToastDialog td = new ToastDialog(context);
        td.setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT,DirectionalLayout.LayoutConfig.MATCH_CONTENT);
        td.setDuration(duration);
        td.setAlignment(LayoutAlignment.CENTER);
        td.setContentCustomComponent(d1);
        td.show();
    }
    public static void showDialog(Context context, String message){
        DirectionalLayout d1= (DirectionalLayout) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_prompt_dialog,null,false);
        Text msg = (Text) d1.findComponentById(ResourceTable.Id_promtmsg);
        msg.setText(message);
        ToastDialog td = new ToastDialog(context);
        td.setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT,DirectionalLayout.LayoutConfig.MATCH_CONTENT);
        td.setDuration(duration);
        td.setAlignment(LayoutAlignment.CENTER);
        td.setContentCustomComponent(d1);
        td.show();
    }
    public static void showDialog(Context context,String message,int image){
        DirectionalLayout d1= (DirectionalLayout) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_prompt_dialog,null,false);
        Text msg = (Text) d1.findComponentById(ResourceTable.Id_promtmsg);
        Image img = (Image) d1.findComponentById(ResourceTable.Id_promtimg);
        msg.setText(message);
        img.setPixelMap(image);
        ToastDialog td = new ToastDialog(context);
        td.setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT,DirectionalLayout.LayoutConfig.MATCH_CONTENT);
        td.setDuration(duration);
        td.setAlignment(LayoutAlignment.CENTER);
        td.setContentCustomComponent(d1);
        td.show();
    }
}
