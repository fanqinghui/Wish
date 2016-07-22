package com.foundation.common.io;

import com.foundation.common.utils.Security;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by fqh on 2015/3/3.
 */
public class IoUtils {


    /**
     * Nio 文件复制
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyFile(String in, String out) throws IOException {
        FileInputStream inputStream = new FileInputStream(in);
        FileOutputStream outputStream = new FileOutputStream(out);
        FileChannel readChannel = inputStream.getChannel();
        FileChannel writeChannel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            int len=readChannel.read(buffer);
            if(len == -1){
                break;
            }
            buffer.flip();
            writeChannel.write(buffer);
        }
        readChannel.close();
        writeChannel.close();
    }


    /**
     * Nio 文件保存
     * @param data
     * @param out
     * @throws IOException
     */
    public static void saveFile(byte[] data, String out) throws IOException {
        if(StringUtils.isNotBlank(out)){
            out="C:\\"+ Security.getUuid()+".jpg";
        }
        FileOutputStream fos=new FileOutputStream(out);
        fos.write(data);
        fos.close();
    }


    /**
     *
     * 二行制转字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }


    public static void main(String[] argsu){
        String in="E:\\360Downloads\\Apk";
        String out="E:\\360Downloads\\Apk\\1";
        try {
            copyFile(in,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
