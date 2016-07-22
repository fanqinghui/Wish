package com.foundation.dao.security;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * 用来解密配置中的密文(重点配置，在这里扩展用户名的解密)
 * setUsername(name) 方法对应xml中的一个property属性，password默认加密不需要重写，
 * 还可以加密url 重写setUrl(url)
 *
 * @fqh
 */
public class DecryptDruidSource {

    public static void main(String[] arugs) {
        //jdbc.username=bNVOqb7WKLX5Bjnw+LMv92taj25KOxDimXxILPQjw42wgv+1lHzOH8kr97xDwWdhpY67QuYCS7sWN4W46YbkFA==
        // jdbc.password=Biyu5YzU+6sxDRbmWEa3B2uUcImzDo0BuXjTlL505+/pTb+/0Oqd3ou1R6J8+9Fy3CYrM18nBDqf6wAaPgUGOg==
        try {
            String userName = ConfigTools.encrypt("root");
            String password = ConfigTools.encrypt("root");
            System.out.println(userName);
            System.out.println(password);
            //解密
            String JuserName = ConfigTools.decrypt("f0PSl0Lzxh6CxzuFIdEg+wVx045fSE2VtUP45G9HH2cjVQnmGGgcK5CLzNUJoR6tGwRO44h74OxrBWuDzWC8jg==");
            String Jpassword = ConfigTools.decrypt("bNVOqb7WKLX5Bjnw+LMv92taj25KOxDimXxILPQjw42wgv+1lHzOH8kr97xDwWdhpY67QuYCS7sWN4W46YbkFA==");
            System.out.println(JuserName);
            System.out.println(Jpassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}