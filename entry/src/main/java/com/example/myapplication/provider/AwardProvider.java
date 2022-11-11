package com.example.myapplication.provider;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.pojo.Award;
import com.example.myapplication.utils.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.ArrayList;

/**
 * @author ITApeDeHao
 * @date 2022/10/2 16 17
 * discription
 */
public class AwardProvider  extends BaseItemProvider {
    ArrayList<Award> list;
    AbilitySlice as;

    public AwardProvider(ArrayList<Award> list, AbilitySlice as) {
        this.list = list;
        this.as = as;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        DirectionalLayout directionalLayout;
        if (component != null){
            directionalLayout = (DirectionalLayout) component;
        }else {
            directionalLayout = (DirectionalLayout)
                    LayoutScatter.getInstance(as).parse(ResourceTable.Layout_list_award, null, false);
        }
        LogUtil.info("11111");

        Text text = (Text) directionalLayout.findComponentById(ResourceTable.Id_awardaccount);
        Award award = list.get(i);
        text.setText(award.getAccount());
        return directionalLayout;
    }
}
