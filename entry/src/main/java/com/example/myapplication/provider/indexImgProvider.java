package com.example.myapplication.provider;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.pojo.IndexImg;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.ArrayList;

public class indexImgProvider extends PageSliderProvider {
    // 数据源，每个页面对应list中的一项
    private ArrayList<IndexImg> layoutFileIds;
    private AbilitySlice abilitySlice;

    public indexImgProvider(ArrayList<IndexImg> layoutFileIds, AbilitySlice abilitySlice) {
        this.layoutFileIds = layoutFileIds;
        this.abilitySlice = abilitySlice;
    }

    @Override
    public int getCount() {return layoutFileIds.size();}
    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        IndexImg indexImg = layoutFileIds.get(i);
        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(abilitySlice)
                .parse(ResourceTable.Layout_index_img,null,false);
        Image image = (Image) layout.findComponentById(ResourceTable.Id_index_img);
        image.setPixelMap(indexImg.getIndexResourceId());
        componentContainer.addComponent(layout);
        return layout;
    }
    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent((Component) o);
    }
    @Override
    public boolean isPageMatchToObject(Component component, Object o) {return true;}    //数据实体类
}
