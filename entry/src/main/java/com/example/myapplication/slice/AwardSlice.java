package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.pojo.Award;
import com.example.myapplication.pojo.QueryParameter;
import com.example.myapplication.pojo.User;
import com.example.myapplication.provider.AwardProvider;
import com.example.myapplication.utils.ApiUtil;
import com.example.myapplication.utils.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.TextAlignment;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.utils.zson.ZSONArray;
import ohos.utils.zson.ZSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ITApeDeHao
 * @date 2022/10/2 15 42
 * discription
 */
public class AwardSlice extends AbilitySlice {
    User user = null;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_slice_award);
        String username = intent.getStringParam("UserName");
        String PassWord = intent.getStringParam("PassWord");
        user = new User(username, PassWord);
        Context context = this;
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(()->{
            String url = "http://123.56.152.169:8111/reward";
            try {
                QueryParameter queryParameter = new QueryParameter();
                queryParameter.add("phone",user.getName());
                ZSONObject res = ApiUtil.api(url, "get", queryParameter, new ZSONObject());
                ZSONArray result = res.getZSONArray("data"); // 获取用户个人账户信息，如：奖学券
                ArrayList<Award> list = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    list.add(new Award());
                }
                context.getUITaskDispatcher().asyncDispatch(() -> {
                    DirectionalLayout directionalLayout = (DirectionalLayout) findComponentById(ResourceTable.Id_myawardlayout);
                    if (!list.isEmpty()){ // 当用户含有奖学券时
                        ListContainer listContainer = new ListContainer(getContext());
                        listContainer.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
                        listContainer.setHeight(AttrHelper.fp2px(500,AttrHelper.getDensity(this)));
                        AwardProvider awardProvider = new AwardProvider(list, this);
                        listContainer.setItemProvider(awardProvider);
                        directionalLayout.addComponent(listContainer);
                    }else { // 当用户不含有奖学券时
                        Text text = new Text(getContext());
                        text.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
                        text.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);
                        text.setText("当前用户没有奖学券");
                        text.setTextSize(AttrHelper.fp2px(30,AttrHelper.getDensity(this)));
                        text.setTextAlignment(TextAlignment.CENTER);
                        directionalLayout.addComponent(text);
                    }
                });
            } catch (IOException e) {
                LogUtil.error("我网络出错啦请链接网络");
            }
        });
        Image image = (Image) findComponentById(ResourceTable.Id_backarrow);
        image.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
