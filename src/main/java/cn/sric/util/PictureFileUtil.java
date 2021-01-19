package cn.sric.util;


import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author sric
 * <p>
 * 由于某些文件使用RestTemplate会出现异常，所以这里使用了OkHttp
 * 首先导入okhttp的依赖
 * <!--okHttp连接-->
 * <dependency>
 * <groupId>com.squareup.okhttp3</groupId>
 * <artifactId>okhttp</artifactId>
 * <version>4.6.0</version>
 * </dependency>
 * @version V1.0
 * @date 2020/11/18
 * @package cn.sric.util
 * @description
 **/
@Slf4j
public class PictureFileUtil {


    /**
     * 下载网络图片
     *
     * @param url      文件URL
     * @param localUrl 下载到本地的路径 (加文件名
     * @return 返回下载的路径
     */
    public static String download(String url, String localUrl) {
        InputStream inputStream = null;
        FileOutputStream out = null;
        try {
            //将网络URL转换为流
            inputStream = sendGetForFile(url);
            if (inputStream != null) {
                out = new FileOutputStream(localUrl);
                int j;
                while ((j = inputStream.read()) != -1) {
                    out.write(j);
                }
                out.flush();
                return localUrl;
            }
        } catch (Exception e) {
            return "";
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 网络文件  由URL转换为流的形式
     *
     * @param url
     * @return
     */
    public static InputStream sendGetForFile(String url) {
        InputStream inputStream;
        Request req = (new Request.Builder()).url(url).get().build();
        Response response;
        try {
            response = new OkHttpClient().newCall(req).execute();
            if (!response.isSuccessful()) {
                log.error("【调用HTTP请求异常】 code:{},message:{},url:{}", response.code(), response.message(), url);
                return null;
            }
            inputStream = Objects.requireNonNull(response.body()).byteStream();
            return inputStream;
        } catch (IOException e) {
            return null;
        }
    }
}
