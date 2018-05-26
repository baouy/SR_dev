package com.sr.core;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;

/**
 * 七牛云上传
 * Created by KingZS on 2018/5/26
 */
public class QiNiuUpLoad {

	public static String ACCESS_KEY = "JnTGy1bmHO8w3XWt4tLfU08jKRwvSMAUhwzELAG1";
	public static String SECRET_KEY = "Ocvgzpy5iz7S2hpTqyqK2FM2EEwOXFhgUK9iRPQl";

	//要上传的空间
	private static String bucketname = "zspackage";

	//密钥配置
	private static  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	//创建上传对象
	private static Configuration cfg = new Configuration(Zone.zone0());
	private static UploadManager uploadManager = new UploadManager(cfg);

	//简单上传，使用默认策略，只需要设置上传的空间名就可以了
	public  static String getUpToken(){
		return auth.uploadToken(bucketname);
	}

	public static boolean upload(byte[] file,String key) throws IOException {
		try {
			//调用put方法上传
			Response res = uploadManager.put(file, key, getUpToken());
			//打印返回的信息
			if (res.statusCode == 200){
				return true;
			}else{
				return false;
			}
		} catch (QiniuException e) {
			Response r = e.response;
			return false;
		}
	}

}
