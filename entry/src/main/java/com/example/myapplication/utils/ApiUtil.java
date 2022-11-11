package com.example.myapplication.utils;

import com.example.myapplication.pojo.QueryParameter;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.utils.zson.ZSONObject;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
/*
*        上传图片构造的RequestBody
         RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "file",
                        file.getName(),
                        MultipartBody.create(MediaType.parse("multipart/form-data"), file)
                ).build();
* */
public abstract class ApiUtil {
    // 请求方式
    public static final String post="post";
    public static final String get="get";


    static Request request;
    static OkHttpClient client;
    // 设置信息状态
    private static final MediaType JSON = MediaType
            .parse("application/json;charset=utf-8");
    // 构造函数
    private ApiUtil() {

    }

    static {
        client = new OkHttpClient();
    }

    // 获取响应数据。。
    private static ZSONObject _get_response() throws IOException {
        // 信息
        Response response = client.newCall(request).execute();
        String message = response.body().string(); // 服务器返回给我们的数据
        response.close();
        ZSONObject res = ZSONObject.stringToZSON(message);
        return res;
    }
    /*
     * 返回获取到的数据内容
     * params: url 请求的网址： method:请求方式; body:post请求的数据体。 queryParameter:自定义结构体 当使用get方法时可以使用该参数传递。
     * */
    public static ZSONObject api(String url, String method, QueryParameter queryParameter, ZSONObject body) throws IOException {
        if (client == null) {
            client = new OkHttpClient();
        }
        if ("get".equals(method)) {
            Request.Builder reqBuild = new Request.Builder();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            if (!queryParameter.isempty()) {
                Set<String> paramskeys = queryParameter.getkeys();
                for (String paramskey : paramskeys)
                    urlBuilder.addQueryParameter(paramskey, queryParameter.getvalue(paramskey));
            }
            reqBuild.url(urlBuilder.build());
            request = reqBuild.build();
            return _get_response();
        } else if ("post".equals(method)) {
            RequestBody requestBody = RequestBody.create(JSON,String.valueOf(body));
            request = new Request.Builder().url(url).post(requestBody).build();
            return _get_response();
        }
        return null;
    }


    /*
     * 返回的数据类型是pixelMap
     * params:  url 请求的网址
     * */
    public static PixelMap get_pic(String url) throws IOException{
        Request request = new Request.Builder().url(url).get().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        //异步网络请求
        Response execute = okHttpClient.newCall(request).execute();
        //获取流
        InputStream inputStream = null;
        if (execute.body() != null) {
            inputStream = execute.body().byteStream();
        }
        //利用鸿蒙api将流解码为图片源
        ImageSource imageSource = ImageSource.create(inputStream, null);
        //根据图片源创建位图
        return imageSource.createPixelmap(null);
    }
}
