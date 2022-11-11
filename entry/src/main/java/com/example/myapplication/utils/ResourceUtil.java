package com.example.myapplication.utils;


import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import java.io.IOException;

public abstract class ResourceUtil {
    public static int getColor(Context context, int id, int defaultValue) {
        int color = defaultValue;
        try {
            color = context.getResourceManager().getElement(id).getColor();
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }
        return color;
    }

    public static float getFloat(Context context, int id, float defaultValue) {
        float value = defaultValue;
        try {
            value = context.getResourceManager().getElement(id).getFloat();
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }
        return value;
    }
}