package com.foundation.common.mq;

import com.foundation.cache.redis.JedisTemplate;
import com.foundation.common.bean.ObjectUtil;
import com.foundation.common.cache.RedisUtils;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * 利用redis实现消息队列（如果消息体少于10K，建议用redis，消息push与pop比MQ（rabbitMq，ActivityMq，kafaka）快很多）
 * Redis比较适合在Web场景下作为队列服务使用，但当数据比较大的时候，入队性能有些问题
 * *=================================================
 * 利用redis做的 简单消息队列思路如下：
 * 消息生产者lpush插入消息数据，消息消费者brpop取出消息，而redis中list 里要push或pop的对象仅需要转换成byte[]即可
 * Created by fqh on 2016/1/22.
 */
public class RedisMqUtils {

    private static JedisTemplate template = null;

    static {
        template = RedisUtils.getTemplate();
    }

    /**
     * redis lpush插入数据
     *
     * @throws Exception
     */
    public static void sendMessage(Message message) throws Exception {
        if (message != null && StringUtils.isNotBlank(message.getMessageKey())) {
            sendMessage(message.getMessageKey(), message);
        }
    }

    /**
     * redis lpush插入数据
     *
     * @throws Exception
     */
    public static void sendMessage(String key, Message message) throws Exception {
        if (message != null && StringUtils.isNotBlank(message.getMessageKey())) {
            template.lpush(key.getBytes(), ObjectUtil.objectToBytes(message.getContent()));
        }
    }


    /**
     * 从消息队列,读取利用 brpop
     *
     * @param topic
     */
    public static void getQueueMessage(final String topic) {

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                final String messageContent = template.brpop(300, topic);
                //多线程操作--
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doHandler(messageContent);
                    }
                }).start();
            }
        });
    }

    /**
     * @param messageContent
     */
    private static void doHandler(String messageContent){
        System.out.println("RQ doHandler 处理"+messageContent);
    }

}
