package com.example.myapplication.utils;

import ohos.app.Context;
import ohos.location.*;

import java.io.IOException;
import java.util.List;

public abstract class LocateUtil {
    private static Locator locator;
    private static GeoAddress address;
    LocatorCallback locatorCallback = new LocatorCallback(){
        @Override
        public void onLocationReport(Location location)  {
            LogUtil.info("位置: " + location.getLatitude() + "-" + location.getAltitude());
            try {
                getLocation(location);
            }catch (IOException e){
                LogUtil.error("我错了！");
            }
        }

        @Override
        public void onStatusChanged(int i) {
            LogUtil.info( "我误： "+i);
        }

        @Override
        public void onErrorReport(int i) {
            LogUtil.info( "我错误： "+i);
        }
    };

    public LocateUtil(){
    }
    // 初始化值
    public void getLocator(Context context){
        LogUtil.info("正在获取地址");
        // 去除上传申请的address的信息
        address.clearLatitude();
        address.clearLongitude();
        locator = new Locator(context);
        // 进行定位
        RequestParam requestParam = new RequestParam(RequestParam.PRIORITY_ACCURACY, 0, 0);
        locator.startLocating(requestParam, locatorCallback);
    }
    // 逆地理编码转化
    private void getLocation(Location location) throws IOException{
        GeoConvert geoConvert = new GeoConvert();
        List<GeoAddress> geoAddress= geoConvert.getAddressFromLocation(location.getLatitude(), location.getLongitude(), 1);
        LogUtil.info("地理位置"+geoAddress.get(0));
        address = geoAddress.get(0);
    }
    // 获取上一次申请的结果
    private Location getLastLacation(){
        return locator.getCachedLocation();
    }
    // 返回值
    public GeoAddress getGeoAddress(){
        return address;
    }
    /*
     *  结束定位
     *  */
    public void stopLocator(){
        locator.stopLocating(locatorCallback);
    }
}