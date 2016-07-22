package com.foundation.common.utils;

import com.foundation.common.io.IoUtils;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by fqh on 2015/12/11.
 */
public class Security {

    /**
     * byte数组转换层base64
     * @param bytes
     * @return
     */
    public static String byte2base64(byte[] bytes){
        BASE64Encoder base64Encoder=new BASE64Encoder();
        return base64Encoder.encode(bytes);
    }
    /**
     * base64转换成byte
     * @param dueStr
     * @return
     */
    public static byte[] base642byte(String base64) throws IOException{
        BASE64Decoder base64Decoder=new BASE64Decoder();
        return base64Decoder.decodeBuffer(base64);
    }

    /**
     * 使用guava的MD5实现现
     * @param dueStr
     * @return
     */
    public static String md5(String dueStr){
        byte[] md5= Hashing.md5().hashString(dueStr, Charsets.UTF_8).asBytes();
        return IoUtils.byte2hex(md5);
    }

//-----------------------des--------------------
    /**
     * 获取desKey
     * des step1
     * @param source
     * @param key
     * @return String
     * @throws Exception
     */
    public static String genKeyDES(String source,SecretKeySpec key) throws Exception{
        KeyGenerator keyGenerator=KeyGenerator.getInstance("DES");
        keyGenerator.init(56);//设置算法密钥为56位
        SecretKey secretKey=keyGenerator.generateKey();
        return IoUtils.byte2hex(secretKey.getEncoded());
    }

    /**
     * 根据密钥生成desKey
     * des step2
     * @param base64Key
     * @return SecretKeySpec
     * @throws Exception
     */
    public static SecretKeySpec loadDesKey(String base64Key) throws Exception{
        byte[] bytes=base642byte(base64Key);
        return new SecretKeySpec(bytes,"DES");
    }

    /**
     *
     * @param source
     * @param key
     * @return
     * @throws Exception
     */
   /* public static String encryptDes(String source,SecretKeySpec key) throws Exception{

    }*/


    public static String decryptDes(String source,SecretKeySpec key) throws Exception{
        KeyGenerator keyGenerator=KeyGenerator.getInstance("DES");
        keyGenerator.init(56);//设置算法密钥为56位
        SecretKey secretKey=keyGenerator.generateKey();
        return IoUtils.byte2hex(secretKey.getEncoded());
    }
    //------------------des end---------------



    /**
     * 获取uuid
     * @return
     */
    public static String getUuid(){
       return UUID.randomUUID().toString();
    }

    public static void main(String[] arugs) throws Exception{
        System.out.println(md5("3332"));
    }
}
