package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.dialog.PromptDialog;
import com.example.myapplication.pojo.QueryParameter;
import com.example.myapplication.pojo.User;
import com.example.myapplication.utils.ApiUtil;
import com.example.myapplication.utils.LocateDataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.multimodalinput.event.TouchEvent;
import ohos.utils.zson.ZSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 写逻辑功能 也可以写页面显示
public class LoginAbilitySlice extends AbilitySlice implements Component.TouchEventListener, Component.ClickedListener{

    private Text registerText; // 注册用户
    private Text forgetPassword; // 忘记密码
    private TextField usernameField;
    private TextField passwordField;
    private Button LoginBut;
    private User user;
    private String ID;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        registerText = (Text) findComponentById(ResourceTable.Id_RegisterPage);
        LoginBut = (Button) findComponentById(ResourceTable.Id_Login);
        usernameField = (TextField) findComponentById(ResourceTable.Id_LoginUserName);
        passwordField = (TextField) findComponentById(ResourceTable.Id_LoginPassWord);
        forgetPassword = (Text) findComponentById(ResourceTable.Id_ForgetPass);
        registerText.setTouchEventListener(this);
        LoginBut.setClickedListener(this);
        forgetPassword.setTouchEventListener(this);
        Map<String,String> map = new HashMap<>();
        map.put("ID","");
        map.put("UserName","");
        map.put("PassWord","");
        map = LocateDataUtil.readData(getApplicationContext(),map);
        if (!(map.get("ID").equals("") || map.get("UserName").equals("") || map.get("PassWord").equals(""))){
            ID = map.get("ID");
            usernameField.setText(map.get("UserName"));
            passwordField.setText(map.get("PassWord"));
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    // 第一个参数 是监听的组件
    // 第二个参数是组件状态
    // 即 按下  滑动 松开  1 0  2
    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent){
        if(component == registerText){
            int action = touchEvent.getAction();
            if(action == TouchEvent.PRIMARY_POINT_UP){
                registerText.setTextColor(Color.GRAY);
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAbilityName("com.example.myapplication.RegisterAbility")
                        .build();
                i.setOperation(operation);
                startAbility(i);
            }
        }
        if(component == forgetPassword){
            int action = touchEvent.getAction();
            if(action == TouchEvent.PRIMARY_POINT_UP){
                forgetPassword.setTextColor(Color.GRAY);
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAbilityName("com.example.myapplication.ForgetPawdAbility")
                        .build();
                i.setOperation(operation);
                startAbility(i);
            }
        }
        return true;
    }

    @Override
    public void onClick(Component component) {
        if (component == LoginBut){
            String ur = usernameField.getText();
            String pw = passwordField.getText();
            if (ur != null && pw != null) {
                user = new User(ur, pw);
            }
            Context context = this;
            getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(()->{
                String url = "http://123.56.152.169:8111/login";
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("phone",user.getName());
                paramsMap.put("paswd",user.getPassword());
                ZSONObject builder = new ZSONObject();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.put(key, paramsMap.get(key));
                }
                try {
                    ZSONObject result = ApiUtil.api(url,"post",new QueryParameter(),builder);
                    this.getUITaskDispatcher().asyncDispatch(()->{
                        String code = result.getString("data");
                        if(code.equals("1")){
                            PromptDialog.showDialog(this,"登录成功!",PromptDialog.Success);
                            Map<String, String> map = new HashMap<>();
                            map.put("Status","OK");
                            map.put("ID",code);
                            map.put("UserName",user.getName());
                            map.put("PassWord",user.getPassword());
                            LocateDataUtil.writeData(getApplicationContext(),map);
                            onBackPressed();
                        }
                    });
                } catch (IOException e) {
                    /*
                    *  网络请求失败 弹窗提示   请检查网络
                    * */
                    PromptDialog.showDialog(this,"请检查网络配置!",PromptDialog.Danger);
                }
            });
        }
    }

}
