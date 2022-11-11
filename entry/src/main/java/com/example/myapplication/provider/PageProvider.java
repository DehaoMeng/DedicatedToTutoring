package com.example.myapplication.provider;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.PageSliderProvider;

import java.util.List;

public class PageProvider extends PageSliderProvider {
    // 数据源，每个页面对应list中的一项
    private List<Integer> layoutFileIds;
    private AbilitySlice abilitySlice;

    public PageProvider(List<Integer> layoutFileIds, AbilitySlice abilitySlice) {
        this.layoutFileIds = layoutFileIds;
        this.abilitySlice = abilitySlice;
    }

    @Override
    public int getCount() {return layoutFileIds.size();}
    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        Integer id = layoutFileIds.get(i);
        Component page = LayoutScatter.getInstance(abilitySlice).parse(id,null,false);
        componentContainer.addComponent(page);
        return page;
    }
    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent((Component) o);
    }
    @Override
    public boolean isPageMatchToObject(Component component, Object o) {return true;}    //数据实体类
}
