package com.sr.core;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;




/**
 * 阿里云OSS上传文件工具
 *
 * 支持普通文件上传，限制大小文件上传,限制大小图片上传
 * Created by KingZS on 2018/5/26
 */
public class AliyunOssUpload {

    private Logger logger = LoggerFactory.getLogger(AliyunOssUpload.class);

    /**阿里云API的内或外网域名*/
    public static String ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    /**OSS签名key*/
    public static String ACCESS_KEY_ID = "LTAIhlNjwcLW2C4d";

    /**OSS签名密钥*/
    public static String ACCESS_KEY_SECRET = "2EHrR2Hgql4pqWvarcFYAbz9f1o2gw";

    /**存储空间名称*/
    public static String BUCKETNAME = "miaomiao-image";

    private OSSClient ossClient = null;


   Map<String, String> returnfile = new HashMap<String,String>();
    Result result=new Result();

    /**
     *  构造器，初始化参数。并实例化ossClient对象
     */
    public AliyunOssUpload(){
        // 初始化一个OSSClient
        ossClient = new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }



    /**
     *  判断bucket存储空间是否已建立
     *  若未建立，先创建一个Bucket
     */
    public void ensureBucket() throws OSSException, ClientException {

        try{


            //判断bucket是否存在
            boolean exists = ossClient.doesBucketExist(AliyunOssUpload.BUCKETNAME);
            if(!exists){

                logger.error("bucket不存在，新建当前bucket:{}",AliyunOssUpload.BUCKETNAME);

                ossClient.createBucket(AliyunOssUpload.BUCKETNAME);
            }

        }catch(ServiceException e){
            logger.error(e.getErrorCode() + "  " + e.getErrorMessage());
            throw e;
        }

    }



    /**
     *  上传文件到OSS服务器
     *  如果同名文件会覆盖服务器上的
     *
     * @param  file 上传文件
     * @param  dirFileName 上传后文件名
     * @param  dirName 文件目录名称  （在oss中不存在目录一说，只是用于形象的区分文件种类）
     * @return Map<String, Object> map
     *  stauts :true 上传成功   。 false  上传失败
     *  msg :成功，返回文件路径。失败，返回失败信息
     */
    public  Result uploadFileOSS(CommonsMultipartFile file, String dirFileName,String dirName) {
        String fileName = file.getFileItem().getName();//文件名
        String ret = "";//上传完成返回签名
        String uploadDir = "";//目录名
        String uploadPath = "";//保存文件路径名称

        InputStream uploadInputStrem = null;


        if(!"".equals(dirName) && dirName!=null){
            uploadDir = dirName.substring(0, dirName.length()).replaceAll("\\\\","/")+"/";
        }

        try {

            //判断bucket是否存在
            ensureBucket();

            //获取上传文件后缀名
            String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            uploadPath = uploadDir + fileName;

            //创建上传Object的Metadata。ObjectMetaData是用户对该object的描述
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setContentEncoding("utf-8");
            objectMetadata.setContentType(getcontentType(file,fileSuffix));//获取文件类型
            objectMetadata.setContentDisposition("attachment;filename=" + fileName+fileSuffix);

            uploadInputStrem = file.getInputStream();   //文件输入流


            //上传文件
            logger.debug("正在上传文件到OSS...");
            PutObjectResult putResult = ossClient.putObject(AliyunOssUpload.BUCKETNAME, uploadPath, uploadInputStrem, objectMetadata);
            logger.debug("上传文件完成...");

            ret = putResult.getETag();
            //logger.debug("上传后的文件MD5数字唯一签名:{}",ret);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setCodeAndMsg(ResultEnum.UPFILE_FAILURE.getCode(),ResultEnum.UPFILE_FAILURE.getMsg());
            return result;
        }  finally {

            try {
                if (uploadInputStrem != null) {
                    uploadInputStrem.close();//关闭文件流
                }
                if(ossClient != null){
                    this.destory();//销毁oss对象
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!"".equals(ret) && ret!=null){

            logger.debug(fileName);
            //获得图片地址,公共读不需要获得签名
           // String url=getUrl(ossClient,ret);
            returnfile.put("enclosure","https://"+BUCKETNAME+"."+ENDPOINT+"/"+uploadPath);
            returnfile.put("enclosurename",fileName);
            result.setModel(returnfile);
            result.setData(returnfile);
            result.defaultSuccess();
            return result;
        }else{
            result.setCodeAndMsg(ResultEnum.UPFILE_FAILURE.getCode(),ResultEnum.UPFILE_FAILURE.getMsg());
            return result;
        }
    }


    public static String getUrl(OSSClient ossClient, String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BUCKETNAME, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }



    /**
     *
     *  上传文件到OSS服务器
     *  如果同名文件会覆盖服务器上已存在文件
     *
     * @param  file  上传文件
     * @param  dirName 文件目录名称  （在oss中不存在目录一说，只是用于形象的区分文件种类）
     * @param  fileName   文件名
     * @param  limitSize     上传大小
     * @return Map<String, Object> map
     *  stauts :true 上传成功   。 false  上传失败
     *  msg :成功，返回文件路径。失败，返回失败信息
     *
     */
    public Result UploadLimitSizeOSS(CommonsMultipartFile file,
                                                  String dirName, String fileName, int limitSize){
        Result result=new Result();
        if (file == null) {
            result.setCodeAndMsg(ResultEnum.FILE_NOT_NULL.getCode(),ResultEnum.FILE_NOT_NULL.getMsg());
            this.destory();
            return result;
        }

        long limitSizeBytes = limitSize * 1024 * 1024;// 把单位M转换为bytes

        if (file.getSize() > limitSizeBytes) {
           result.setCodeAndMsg(ResultEnum.FILE_BEYOND_LIMITSIZE.getCode(),ResultEnum.FILE_BEYOND_LIMITSIZE.getMsg());
            this.destory();
            return result;
        }

        return this.uploadFileOSS(file,fileName,dirName);
    }


    /**
     *
     *  上传图片到到OSS服务器
     *  如果同名文件会覆盖服务器上已存在文件
     *
     * @param  file  上传文件
     * @param  dirName 文件目录名称  （在oss中不存在目录一说，只是用于形象的区分文件种类）
     * @param  fileName   文件名
     * @param  limitSize     上传大小
     * @return Map<String, Object> map
     *  stauts :true 上传成功   。 false  上传失败
     *  msg :成功，返回文件路径。失败，返回失败信息
     */
   /* public Map<String, Object> ImgUploadLimitSizeOSS(CommonsMultipartFile file,
                                                     String dirName, String fileName, int limitSize){

        if (file == null) {
            resultMap.put("status", false);
            resultMap.put("msg", "未选择文件!");
            this.destory();
            return resultMap;
        }

        if (!ImageUtil.fileIsImage(file)) {
            resultMap.put("status", false);
            resultMap.put("msg", "请选择上传图片文件!");
            this.destory();
            return resultMap;
        }

        long limitSizeBytes = limitSize * 1024 * 1024;// 把单位M转换为bytes

        if (file.getSize() > limitSizeBytes) {
            resultMap.put("status", false);
            resultMap.put("msg", "您上传的文件超出限制大小" + limitSize + "M");
            this.destory();
            return resultMap;
        }

        return this.uploadFileOSS(file, dirName, fileName);
    }
*/

    /**
     * 判断OSS服务文件上传时文件的contentType
     *
     * @param  file 上传文件
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(CommonsMultipartFile file,String FilenameExtension) {

        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png") ||
                FilenameExtension.equalsIgnoreCase(".jpz") ) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html") ||
                FilenameExtension.equalsIgnoreCase(".htm")  ||
                FilenameExtension.equalsIgnoreCase(".hts")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".xls")) {
            return "application/vnd.ms-excel";
        }
        if (FilenameExtension.equalsIgnoreCase(".xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        return file.getContentType();
    }


    /**
     *  销毁阿里云OSS客户端对象
     */
    public void destory() {
        ossClient.shutdown();
    }


    /**
     * 获取阿里云远程OSS指定文件.并转为输出流
     *
     * @param  ossPrefix  访问阿里云oss文件路径 （http://ygzb.oss-cn-beijing.aliyuncs.com/）
     * @param  fileUrl 文件保存路径 （如：upload/a.png）
     * @param  oputstream  输出流
     * @throws IOException
     */
    public static void downFile(String ossPrefix,String fileUrl,OutputStream oputstream) throws IOException{

        InputStream iputstream = null;

        try{

            String ossFilePath = ossPrefix + fileUrl;//阿里云网络文件地址

            URL url = new URL(ossFilePath);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
            uc.connect();

            iputstream = uc.getInputStream();

            byte[] buffer = new byte[4*1024];
            int byteRead = -1;

            while((byteRead=(iputstream.read(buffer)))!= -1){
                oputstream.write(buffer, 0, byteRead);
            }
            oputstream.flush();


        } catch (Exception e) {
            System.out.println("读取失败！");
            e.printStackTrace();
        } finally{
            if(iputstream != null){
                iputstream.close();
            }
        }
    }

}
