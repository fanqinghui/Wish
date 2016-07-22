package com.foundation.dao.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来解密配置中的密文(重点配置，在这里扩展用户名的解密)
 * setUsername(name) 方法对应xml中的一个property属性，password默认加密不需要重写，
 * 还可以加密url 重写setUrl(url)
 * @author  fanqinghui
 */
public class DecryptDruidSource extends DruidDataSource {

    Logger logger= LoggerFactory.getLogger(DecryptDruidSource.class);

    @Override
    public void setUsername(String username) {
        try {
            username = ConfigTools.decrypt(username);
        } catch (Exception e) {
            logger.error("数据库密码解密错误",e);
        }
        super.setUsername(username);
    }
}