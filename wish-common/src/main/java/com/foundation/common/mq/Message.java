package com.foundation.common.mq;

import com.foundation.cache.redis.JedisTemplate;
import com.foundation.common.bean.ObjectUtil;
import com.foundation.common.cache.RedisUtils;

import java.io.Serializable;
 
/**
 * 定义消息类接收消息内容和设置消息的下标
 * Created by fqh on 2016/1/27.
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 7792729L;

    public Message(String messageKey, int id, String content) {
        this.messageKey = messageKey;
        this.id = id;
        this.content = content;
    }

    private String messageKey;//消息key
    private int id;//消息id
    private String content;//消息内容
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageKey='" + messageKey + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}

/**
 * reids 利用redis做的 简单消息队列。消息生产者rpush插入消息数据，消息消费者lpop取出消息
 * @author fqh
 */
class TestRedisQuene {
    public static String redisKey = "key";
    public static JedisTemplate template=RedisUtils.getTemplate();
    static{
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception{
        pop();
    }

    private static void pop() throws Exception{
        byte[] bytes =template.rpop(redisKey.getBytes());
        Message msg = (Message) ObjectUtil.bytesToObject(bytes);
        if(msg != null){
            System.out.println(msg.getId()+"   "+msg.getContent());
        }
    }

    private static void init() throws Exception {
        Message msg1 = new Message(redisKey,1, "内容1");
        template.lpush(redisKey.getBytes(), ObjectUtil.objectToBytes(msg1));
        Message msg2 = new Message(redisKey,2, "内容2");
        template.lpush(redisKey.getBytes(), ObjectUtil.objectToBytes(msg2));
        Message msg3 = new Message(redisKey,3, "内容3");
        template.lpush(redisKey.getBytes(), ObjectUtil.objectToBytes(msg3));
    }

}