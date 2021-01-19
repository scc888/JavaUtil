package cn.sric;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 导入okHttp依赖
 *  <!--okHttp连接-->
 *         <dependency>
 *             <groupId>com.squareup.okhttp3</groupId>
 *             <artifactId>okhttp</artifactId>
 *             <version>4.6.0</version>
 *         </dependency>
 * @author sric
 */
public class OkHttp {
    static OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS).build();

    /**
     * get 请求
     *
     * @param url url
     * @return 返回请求内容
     */
    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url).addHeader("application/json;", "charset=utf-8")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post 请求
     *
     * @param url         url
     * @param requestBody 请求体
     * @return 返回请求结果
     */
    private static String post(String url, RequestBody requestBody) {
        Request request = new Request.Builder()
                .url(url).post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 由于上面设置请求体有点麻烦，所以我重载了一下post方法
     *
     * @param url 请求路径
     * @param map 请求内容放入map中就可以
     * @return 返回请求内容
     */
    public static String post(String url, Map<String, Object> map) {
        List<String> name = new ArrayList<>();
        List<String> value = new ArrayList<>();
        map.keySet().forEach(key -> {
            name.add(key);
            value.add(map.get(key).toString());
        });
        FormBody formBody = new FormBody(name, value);
        return post(url, formBody);
    }

//    /**
//     * @param url  请求地址
//     * @param json 上报内容
//     * @return 返回值
//     */
//    public String okHttpPostMethod(String url, String json) {
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(JSON, json);
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String get(String url, JSONObject jsonObject) {
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        //1 . 拿到OkHttpClient对象
//        OkHttpClient client = new OkHttpClient();
//        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
//        //3 . 构建Request,将FormBody作为Post方法的参数传入
//        Request request = new Request.Builder()
//                .url(url).post(requestBody)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
}