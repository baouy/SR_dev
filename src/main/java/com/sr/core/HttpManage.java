package com.sr.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KingZS on 2018/5/28
 */
public class HttpManage {

    public static String postUrl(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }

    public static String postCreateGroup(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
    /**
     * 将http的响应信息流，转换为字符串
     * @param inputStream
     * @return
     */
    public static String converInputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        int flag = 0;
        byte[] buffer = new byte[1024];
        try {
            while (-1 != (flag = inputStream.read(buffer))) {
                arrayOutputStream.write(buffer, 0, flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = arrayOutputStream.toByteArray();
        try {
            inputStream.close();
            arrayOutputStream.flush();
            arrayOutputStream.close();
            return new String(bytes,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(bytes);
    }

//    public static Long ResultJSON(Long CustSalesID,String group_name,List<Long> list){
//        JSONObject obj = new JSONObject();
//        obj.put("req_user_id",CustSalesID);
//        obj.put("group_name",group_name);
//        obj.put("group_type",2);
//        obj.put("group_avatar","");
//        obj.put("user_id_list",list);
//        String result = HttpManage.postCreateGroup(obj.toString(),Constants.IM_URL);
//        if (result.length() > 0){
//            JSONObject jsonObject = JSON.parseObject(result);
//            int error_code = jsonObject.getInteger("error_code");
//            if (error_code == 0){
//                String group_id = jsonObject.getString("group_id");
//                return Long.valueOf(group_id);
//            }
//        }
//        return -1L;
//    }
//
//    public static Long sendMessage(int req_peer_id,String message){
//
//        JSONObject obj = new JSONObject();
//        obj.put("req_user_id",Constants.MXM_ID);
//        obj.put("req_peer_id",req_peer_id);
//        obj.put("message",message);
//
//        String result = HttpManage.postCreateGroup(obj.toString(),Constants.IM_URL);
//        if (result.length() > 0){
//            JSONObject jsonObject = JSON.parseObject(result);
//
//        }
//        return -1L;
//
//    }

}
