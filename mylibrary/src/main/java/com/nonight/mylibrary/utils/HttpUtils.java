package com.nonight.mylibrary.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by 祈愿星痕 on 2016/11/8.
 *
 */

public class HttpUtils {
    public final static int SUCCEED = 1;
    public final  static  int FAILED = 2;



    //inputstring 转 string
    public static String readMyInputStream(InputStream is) {
        byte[] result;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer))!=-1) {
                baos.write(buffer,0,len);
            }
            is.close();
            baos.close();
            result = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            String errorStr = "获取数据失败。";
            return errorStr;
        }
        return new String(result);
    }


    //服务器错误代码转为String
    public static String ConnectErrorCodetoString(int code){
        switch (code){
            case 404:
                return "链接错误!";
            case 400:
                return "参数错误!";
            case 403:
                return "权限不足!";
            case 405:
                return "请求方式错误!";
            default:
                return "服务器繁忙!";

        }
    }



    public static void sendGETRequest(final String jsessionid, final String address,
                                      final HttpStringCallbackListener listener) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;

                        try {
                            URL url = new URL(address);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            connection.setRequestProperty("Cookie",jsessionid);
                            int code = connection.getResponseCode();
                            if (code == 200) {
                                InputStream in = connection.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                                StringBuilder response = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    response.append(line);
                                }
                                if (listener != null) {
                                    listener.onFinish(SUCCEED, response.toString());
                                }
                            }else {
                                if (listener != null) {
                                    listener.onFinish(FAILED, ConnectErrorCodetoString(code).toString());
                                }
                            }
                        } catch (Exception e) {
                            if (listener != null) {
                                // 回调方法 onError()
                                listener.onError(e);
                            }
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                }).start();

    }

    public static void sendPOSTRequest(final String jsessionid, final String address,
                                       final HttpStringCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept","*/*");
                    connection.setRequestProperty("connection", "keep-alive");
                    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
                    connection.setRequestProperty("Cookie",jsessionid);
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                    connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestProperty("Origin","http://api.xsili.com");

                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (listener != null) {
                            listener.onFinish(SUCCEED, response.toString());
                        }
                    }else {
                        if (listener != null) {
                            listener.onFinish(FAILED, ConnectErrorCodetoString(code).toString());
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调方法 onError()
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendPOSTRequest(final String jsessionid, final String address,
                                       final String datastr, final HttpStringCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept","*/*");
                    connection.setRequestProperty("connection", "keep-alive");
                    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
                    connection.setRequestProperty("Cookie",jsessionid);
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                    connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);        // 允许输入
                    connection.setDoOutput(true);        // 允许输出
                    connection.setUseCaches(false);    // 不使用Cache
                    if (null !=datastr&&!datastr.isEmpty()) {
                        DataOutputStream data = new DataOutputStream(connection.getOutputStream());
                        data.writeBytes(datastr);
                        data.flush();
                        data.close();
                    }

                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (listener != null) {
                            listener.onFinish(SUCCEED, response.toString());
                        }
                    }else {
                        if (listener != null) {
                            listener.onFinish(FAILED, ConnectErrorCodetoString(code).toString());
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调方法 onError()
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    ///用uri为参数
    public static void sendPictureRequest(final String jsessionid, final String address, final Activity context,
                                          final Uri uri, final HttpStringCallbackListener listener){
        final String CONTENT_TYPE  = "multipart/form-data";
        final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        final String PREFIX = "--", LINE_END = "\r\n";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept","application/json");
                    connection.setRequestProperty("connection", "keep-alive");
                    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
                    connection.setRequestProperty("Cookie",jsessionid);
                    connection.setRequestProperty("Content-Type",CONTENT_TYPE+"; boundary="+BOUNDARY);
                    connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(15000);
                    connection.setRequestProperty("Origin","http://api.xsili.com");
                    connection.setDoInput(true);        // 允许输入
                    connection.setDoOutput(true);        // 允许输出
                    connection.setUseCaches(false);    // 不使用Cache
                    File file = uri2File(uri,context);
                    if (null !=file){
                        OutputStream outputSteam = connection.getOutputStream();
                        DataOutputStream dos = new DataOutputStream(outputSteam);
                        StringBuffer sb = new StringBuffer();
                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
                        sb.append("Content-Disposition: form-data; name=\"upfile\"; filename=\""
                                + file.getName() + "\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset="
                                + "UTF-8" + LINE_END);
                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());

                        InputStream is = new FileInputStream(file);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        is.close();
                        dos.write(LINE_END.getBytes());
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                                .getBytes();
                        dos.write(end_data);
                        dos.flush();
                        dos.close();
                    }
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (listener != null) {
                            listener.onFinish(SUCCEED, response.toString());
                        }
                    }else {
                        if (listener != null) {
                            listener.onFinish(FAILED, ConnectErrorCodetoString(code).toString());
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调方法 onError()
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //用文件路径
    public static void sendPictureRequest(final String jsessionid, final String address, final Activity context,
                                          final String filepath, final HttpStringCallbackListener listener){
        final String CONTENT_TYPE  = "multipart/form-data";
        final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        final String PREFIX = "--", LINE_END = "\r\n";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept","application/json");
                    connection.setRequestProperty("connection", "keep-alive");
                    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
                    connection.setRequestProperty("Cookie",jsessionid);
                    connection.setRequestProperty("Content-Type",CONTENT_TYPE+"; boundary="+BOUNDARY);
                    connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(15000);
                    connection.setRequestProperty("Origin","http://api.xsili.com");
                    connection.setDoInput(true);        // 允许输入
                    connection.setDoOutput(true);        // 允许输出
                    connection.setUseCaches(false);    // 不使用Cache
                    File file = new File(filepath);
                    if (file.exists()){
                        //文件存在 开始传输
                        OutputStream outputSteam = connection.getOutputStream();
                        DataOutputStream dos = new DataOutputStream(outputSteam);
                        StringBuffer sb = new StringBuffer();
                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
                        sb.append("Content-Disposition: form-data; name=\"upfile\"; filename=\""
                                + file.getName() + "\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset="
                                + "UTF-8" + LINE_END);
                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());

                        InputStream is = new FileInputStream(file);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        is.close();
                        dos.write(LINE_END.getBytes());
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                                .getBytes();
                        dos.write(end_data);
                        dos.flush();
                        dos.close();
                    }else {
                        if (listener != null) {
                            listener.onFinish(FAILED, "文件不存在");
                        }
                    }
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (listener != null) {
                            listener.onFinish(SUCCEED, response.toString());
                        }
                    }else {
                        if (listener != null) {
                            listener.onFinish(FAILED, ConnectErrorCodetoString(code).toString());
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调方法 onError()
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //测试检查请求头用
    private static void printResponseHeader(HttpURLConnection http) throws UnsupportedEncodingException {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
        }
    }
    private static Map<String, String> getHttpResponseHeader(
            HttpURLConnection http) throws UnsupportedEncodingException {
        Map<String, String> header = new LinkedHashMap<String, String>();
        for (int i = 0;; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null)
                break;
            header.put(http.getHeaderFieldKey(i), mine);
        }
        return header;
    }


    ///上传图片时，当参数为uri时，转为String 文件路径 用
    public static File uri2File(Uri uri, Activity context) {
        File file = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = context.managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor != null) {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            file = new File(img_path);
        }else if (actualimagecursor ==null) {
            String headImgFilePath = uri.getPath().replace("/storage/emulated/0", "/sdcard");
            file = new File(headImgFilePath);
        }
        return file;
    }
    public static String getcoverimgurl (String coverimgstr){
        coverimgstr = coverimgstr.replace("%3A", ":");
        coverimgstr = coverimgstr.replace("%2F", "/");
        return coverimgstr;
    }







    // 定义HttpCallbackListener接口
    // 包含两个方法，成功和失败的回调函数定义
    public interface HttpBitmapCallbackListener {
        void onFinish(int code, Bitmap bitmap);
        void onError(Exception e);
        void onFinish(int code, String response);
    }
    public interface HttpStringCallbackListener {
        void onFinish(int code, String response);
        void onError(Exception e);
    }



    public static void CatchE(Exception e){

    }





}
