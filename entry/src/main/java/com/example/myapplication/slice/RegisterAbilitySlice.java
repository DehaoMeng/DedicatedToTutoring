package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.dialog.PromptDialog;
import com.example.myapplication.pojo.QueryParameter;
import com.example.myapplication.pojo.User;
import com.example.myapplication.utils.ApiUtil;
import com.example.myapplication.utils.LocateDataUtil;
import com.example.myapplication.utils.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.utils.zson.ZSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterAbilitySlice extends AbilitySlice implements Component.ClickedListener {
    private TextField usernametext;
    private TextField passwordtext;
    private TextField againpasswordtext;
    private Button registerbutton;
    private String flag;
    private String id;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_register);
        usernametext = (TextField) findComponentById(ResourceTable.Id_RegisterUserName);
        passwordtext = (TextField) findComponentById(ResourceTable.Id_RegisterPassWord);
        againpasswordtext = (TextField) findComponentById(ResourceTable.Id_AgainPassWord);
        registerbutton = (Button) findComponentById(ResourceTable.Id_Register);
        registerbutton.setClickedListener(this);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public void onClick(Component component) {
        if (component == registerbutton){
            String username = usernametext.getText();
            String password = passwordtext.getText();
            String againpassword = againpasswordtext.getText();
            if (username.length() < 5 || username.length() > 11){
                 /*
                 　用户名为空弹窗提醒
                  */
                PromptDialog.showDialog(this,"用户名不满足6~11位!",PromptDialog.Danger);
                return;
            }
            if (password.equals("") || password.length() > 16 || password.length() < 6){
                /*
                 第一次密码为空弹窗提醒
                 */
                PromptDialog.showDialog(this,"密码不满足6~16位!",PromptDialog.Danger);
                return;
            }
            if (againpassword.equals("") || !againpassword.equals(password)){
                /*
                 第二次密码为空 或与第一次密码不同  弹窗提醒
                 */
                PromptDialog.showDialog(this,"密码与第一次不同请重新输入!",PromptDialog.Danger);
                return;
            }
            User user = new User(username,password);
            Context context = this;
            getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(()->{
                String url = "http://123.56.152.169:8111/register";
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("phone",user.getName());
                paramsMap.put("paswd",user.getPassword());
                paramsMap.put("icon","孟德昊");
                paramsMap.put("address","镇江市京口区");
                ZSONObject zsonObject = new ZSONObject();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    zsonObject.put(key, paramsMap.get(key));
                }
                try {
                    LogUtil.info( "我发送了" +  zsonObject.toString());
                    ZSONObject result = ApiUtil.api(url,ApiUtil.post,new QueryParameter(),zsonObject);
                    context.getUITaskDispatcher().asyncDispatch(()->{
                        try {
                            id = result.getZSONObject("data").getString("id");
                            flag = result.getString("message");
                        }catch (Exception e){
                            /*
                            *  注册失败 ToastDialog
                            * */
                            PromptDialog.showDialog(this,"您的网络似乎存在一些问题，请稍后再试!",PromptDialog.Danger);
                        }
                    });
                } catch (IOException e) {
                    PromptDialog.showDialog(this,"您的网络似乎存在一些问题，请稍后再试!",PromptDialog.Danger);
                }
            });
            if("注册成功".equals(flag)){
                PromptDialog.showDialog(this,flag + "!",PromptDialog.Success);
                Map<String, String> map = new HashMap<>();
                map.put("ID",id);
                map.put("UserName",username);
                map.put("PassWord",password);
                LocateDataUtil.writeData(context,map);
                onBackPressed();
            }else {
                PromptDialog.showDialog(this,flag + "!",PromptDialog.promt);
            }
        }

    }
}
