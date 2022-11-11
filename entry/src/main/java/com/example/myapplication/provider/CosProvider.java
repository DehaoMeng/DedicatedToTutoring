package com.example.myapplication.provider;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.ArrayList;

public class CosProvider extends BaseItemProvider {
    ArrayList<String> list;
    AbilitySlice as;

    public CosProvider(ArrayList<String> list, AbilitySlice as) {
        this.list = list;
        this.as = as;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public AbilitySlice getAs() {
        return as;
    }

    public void setAs(AbilitySlice as) {
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
                    LayoutScatter.getInstance(as).parse(ResourceTable.Layout_choose_list, null, false);
        }
        String text = list.get(i);
        Text coslist = (Text) directionalLayout.findComponentById(ResourceTable.Id_chooselist);
        coslist.setText(text);
        return directionalLayout;
    }
}
