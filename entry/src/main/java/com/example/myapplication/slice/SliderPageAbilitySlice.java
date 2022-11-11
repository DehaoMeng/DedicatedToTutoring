package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.dialog.PromptDialog;
import com.example.myapplication.pojo.IndexImg;
import com.example.myapplication.pojo.QueryParameter;
import com.example.myapplication.pojo.Teacher;
import com.example.myapplication.pojo.User;
import com.example.myapplication.provider.PageProvider;
import com.example.myapplication.provider.TeacherProvider;
import com.example.myapplication.provider.indexImgProvider;
import com.example.myapplication.utils.ApiUtil;
import com.example.myapplication.utils.LocateDataUtil;
import com.example.myapplication.utils.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.location.Locator;
import ohos.location.LocatorCallback;
import ohos.media.image.PixelMap;
import ohos.utils.zson.ZSONArray;
import ohos.utils.zson.ZSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SliderPageAbilitySlice extends AbilitySlice implements Component.ClickedListener {
    PageSlider pageSlider, imgSlider;
    DirectionalLayout messimg, me, ft, home;
    Text locat;
    User user = null;
    Locator locator;
    int PageNum,ID;
    Context context = this;
    private boolean first = true;
    Map<String,String> map;
    private LocatorCallback locatorCallback;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_slider);
        GetMap();
        initPageSlider();
        messimg = (DirectionalLayout) findComponentById(ResourceTable.Id_MessagePage);
        messimg.setClickedListener(this);
        ft = (DirectionalLayout) findComponentById(ResourceTable.Id_findteacher);
        ft.setClickedListener(this);
        home = (DirectionalLayout) findComponentById(ResourceTable.Id_frontpage);
        home.setClickedListener(this);
        me = (DirectionalLayout) findComponentById(ResourceTable.Id_me);
        me.setClickedListener(this);
    }
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locator.stopLocating(locatorCallback);
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
        GetMap();
        initMySlice(pageSlider);
    }
    private void GetMap(){
        user = null;
        map = new HashMap<>();
        map.put("Status","");
        map = LocateDataUtil.readData(getApplicationContext(), map);
        if (map.get("Status").equals("OK")){
            map.put("ID","");
            map.put("UserName","");
            map.put("PassWord","");
            LocateDataUtil.readData(getApplicationContext(), map);
            user = new User(map.get("ID"),map.get("UserName"), map.get("PassWord"));
        }
    }
    // 初始化PageSlider界面
    private void initPageSlider() {
        pageSlider = (PageSlider) findComponentById(ResourceTable.Id_page_slider);
        PageProvider pageProvider = new PageProvider(getData(), this);
        pageSlider.setProvider(pageProvider);
        pageSlider.setSlidingPossible(false);
        ChoosePage();
    }
    // 点击跳转pageslider页面
    @Override
    public void onClick(Component component) {
        if (imgSlider != null)
            imgSlider = null;
        if (component == home && pageSlider.getCurrentPage() != 0) {
            pageSlider.setCurrentPage(0, false);
            initHomeSlice(pageSlider);
        } else if (component == ft && pageSlider.getCurrentPage() != 1) {
            pageSlider.setCurrentPage(1, false);
            initFindTeacherSlice(pageSlider);
        } else if (component == messimg && pageSlider.getCurrentPage() != 2) {
            pageSlider.setCurrentPage(2, false);
            initMessageSlice(pageSlider);
        } else if (component == me && pageSlider.getCurrentPage() != 3) {
            pageSlider.setCurrentPage(3, false);
            initMySlice(pageSlider);
        }
    }
    // 选择页面显示信息
    private void ChoosePage(){
        if (PageNum == 0){
            initHomeSlice(pageSlider);
        }else if(PageNum == 1){
            initFindTeacherSlice(pageSlider);
        }else if(PageNum == 2){
            initMessageSlice(pageSlider);
        }else {
            initMySlice(pageSlider);
        }
    }
    // 初始化主界面
    private void initHomeSlice(PageSlider pageSlider) {
        imgSlider = (PageSlider) pageSlider.findComponentById(ResourceTable.Id_advertrsement2);
        indexImgProvider imgProvider = new indexImgProvider(getImgData(), this);
        imgSlider.setProvider(imgProvider);
        imgSlider.setSlidingPossible(true);
        imgSlider.setPageSwitchTime(2000);
        /*
         * 定位功能无法实现
         */
//        LocateUtil locateUtil = new LocateUtil();
//        locateUtil.getLocator(this);
//        GeoAddress geoAddress = locateUtil.getGeoAddress();
//        LogUtil.info("111111"+geoAddress.getPlaceName());
    }
    // 初始化查找老师界面
    private void initFindTeacherSlice(PageSlider pageSlider) {
        Context context = this;
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> { // 异步开启一个线程
            String url = "http://123.56.152.169:8111/teachers";
            try {
                ZSONObject res = ApiUtil.api(url, "get", new QueryParameter(), new ZSONObject()); // 发送网络请求
                ZSONArray result = res.getZSONArray("data");        // 获取返回的数据
                ArrayList<Teacher> list = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    ZSONObject teacher = result.getZSONObject(i);
                    PixelMap pixelMap = ApiUtil.get_pic(teacher.getString("teach_icon"));
                    list.add(new Teacher(pixelMap, teacher.getString("teach_name"), teacher.getString("teach_sub")
                            , teacher.getString("teach_address"), teacher.getString("grade")));
                }
                this.getUITaskDispatcher().asyncDispatch(() -> { // 回到主线程，将数据展示到页面之上
                    ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_teachercontainer);
                    TeacherProvider teacherProvider = new TeacherProvider(list, this);
                    listContainer.setItemProvider(teacherProvider);
                });
            } catch (IOException e) {
                LogUtil.error(e.getMessage());
                PromptDialog.showDialog(this,"我网络出错啦请链接网络",PromptDialog.Danger);
            }catch (Exception | Error e){
                LogUtil.error(e.getMessage());
            }
        });
        DirectionalLayout gradeLayout = (DirectionalLayout) pageSlider.findComponentById(ResourceTable.Id_gradeLayout);
        DirectionalLayout subjectLayout = (DirectionalLayout) pageSlider.findComponentById(ResourceTable.Id_subjectLayout);
        gradeLayout.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                /*
                 * TODO dialog插入
                 */
            }
        });
    }
    // 初始化信息界面
    private void initMessageSlice(PageSlider pageSlider) {
    }
    // 初始化个人界面
    private void initMySlice(PageSlider pageSlider) {
        Context context = this;
        DirectionalLayout perinfor = (DirectionalLayout) pageSlider.findComponentById(ResourceTable.Id_perinfor);
        perinfor.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAbilityName("com.example.myapplication.LoginAbility")
                        .build();
                i.setOperation(operation);
                startAbility(i);
            }
        });
        DirectionalLayout myaward = (DirectionalLayout) pageSlider.findComponentById(ResourceTable.Id_myaward);
        myaward.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (user == null){
                   PromptDialog.showDialog(context,"请先进行登录!");
                    return;
                }
                Intent i = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAction("award")
                        .build();
                i.setParam("UserName","3201303014");
                i.setParam("PassWord","123456");
                i.setParam("ID",1);
                i.setOperation(operation);
                present(new AwardSlice(),i);
            }
        });
        if (user != null) {
            Text username = (Text) pageSlider.findComponentById(ResourceTable.Id_usernametext);
            Text ID = (Text) pageSlider.findComponentById(ResourceTable.Id_idtext);
            username.setText("用户名: " + user.getName());
            ID.setText("ID: " + user.getID());
            perinfor.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    PromptDialog.showDialog(pageSlider.getContext(),"用户" + user.getName() + "已登录",PromptDialog.Success);
                }
            });
        }
    }
    // 获取PageSlider数据信息
    private List<Integer> getData() {
        List<Integer> list = new ArrayList<>();
        list.add(ResourceTable.Layout_ability_user);
        list.add(ResourceTable.Layout_ability_findteacher);
        list.add(ResourceTable.Layout_ability_message);
        list.add(ResourceTable.Layout_ability_me);
        return list;
    }
    // 构建CommanDialog 中的ListContainer组件中的数据。
    private ArrayList<String> getGradeData() {
        ArrayList<String> list = new ArrayList<>();
        // 添加年级数据
//        list.add("一年级");
//        list.add("二年级");
//        list.add("三年级");
//        list.add("四年级");
//        list.add("五年级");
//        list.add("六年级");
//        list.add("初一");
//        list.add("初二");
//        list.add("初三");
//        list.add("高一");
//        list.add("高二");
//        list.add("高三");
//        list.add("大一");
//        list.add("大二");
//        list.add("大三");
//        list.add("大四");
        return list;
    }

    private ArrayList<String> getsubData() {
        ArrayList<String> list = new ArrayList<>();
        // 添加科目数据
        return list;
    }

    // 获取广告图片数据
    private ArrayList<IndexImg> getImgData() {
        ArrayList<IndexImg> list = new ArrayList<>();
        list.add(new IndexImg(ResourceTable.Media_teacher01));
        list.add(new IndexImg(ResourceTable.Media_teacher02));
        list.add(new IndexImg(ResourceTable.Media_teacher03));
        return list;
    }

}
