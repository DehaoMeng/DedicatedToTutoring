package com.example.myapplication.provider;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.pojo.Teacher;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.ArrayList;

public class TeacherProvider extends BaseItemProvider {
    ArrayList<Teacher> list;
    AbilitySlice as;

    public TeacherProvider(ArrayList<Teacher> list, AbilitySlice as) {
        this.list = list;
        this.as = as;
    }

    public ArrayList<Teacher> getList() {
        return list;
    }

    public void setList(ArrayList<Teacher> list) {
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
        if (list != null && i >= 0 && i < list.size()){
            return list.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        DirectionalLayout directionalLayout;
        if (component != null){
            directionalLayout = (DirectionalLayout) component;
        }else {
            directionalLayout = (DirectionalLayout)
                    LayoutScatter.getInstance(as).parse(ResourceTable.Layout_list_teacher, null, false);
        }
        Teacher teacher = list.get(i);
        Image tico = (Image) directionalLayout.findComponentById(ResourceTable.Id_teacherico);
        Text tname = (Text) directionalLayout.findComponentById(ResourceTable.Id_teachername);
        Text tlocate = (Text) directionalLayout.findComponentById(ResourceTable.Id_teacherlocate);
        Text tsubject = (Text) directionalLayout.findComponentById(ResourceTable.Id_teachersubject);
        Text tgrade = (Text) directionalLayout.findComponentById(ResourceTable.Id_teachergrade);
        tico.setPixelMap(teacher.getImg());
        tname.setText(teacher.getName());
        tlocate.setText(teacher.getLocate());
        tsubject.setText(teacher.getSubject());
        tgrade.setText(teacher.getGrade());
        return directionalLayout;
    }
}
