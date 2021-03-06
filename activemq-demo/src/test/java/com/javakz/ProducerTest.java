package com.javakz;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * @Author: yandq
 * @Description: 生产者
 * @Date: Create in 16:17 2018/3/20
 * @Modified By:
 */
public class ProducerTest {

    /**
     * 测试Queue
     * 生产者：发送消息
     * @throws JMSException
     */
    @Test
    public void testQueueProducer() throws JMSException {
        // 1）创建 ConnectionFactory 对象，需要指定服务端 ip 及端口号。
        // brokerURL 服务器的IP及端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.113:61616");
        // 2）使用 ConnectionFactory 对象创建一个 Connection 对象。
        Connection connection = connectionFactory.createConnection();
        // 3）开启连接，调用 Connection 对象的 start 方法。
        connection.start();
        // 4）使用 Connection 对象创建一个 Session 对象。
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 5）使用 Session 对象创建一个 Destination 对象（topic、queue），此处创建一个 Queue对象。
        Queue queue = session.createQueue("test-queue");
        // 6）使用 Session 对象创建一个 Producer 对象。
        MessageProducer producer = session.createProducer(queue);
        // 7）创建一个 Message 对象，创建一个 TextMessage 对象。
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("hello activeMq,this is my first test-queue.");
//        TextMessage textMessage = session.createTextMessage("hello activeMq,this is my first test.");
        // 8）使用 Producer 对象发送消息。
        producer.send(textMessage);
        // 9）关闭资源。
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 测试Topic
     * 生产者：发送消息
     * @throws JMSException
     */
    @Test
    public void testTopicProducer() throws JMSException {
        // 1）创建 ConnectionFactory 对象，需要指定服务端 ip 及端口号。
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.113:61616");
        // 2）使用 ConnectionFactory 对象创建一个 Connection 对象。 参数用户名/密码
        Connection connection = connectionFactory.createConnection("yan","yan");
        // 3）开启连接，调用 Connection 对象的 start 方法。
        connection.start();
        // 4）使用 Connection 对象创建一个 Session 对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5）使用 Session 对象创建一个 Destination 对象（topic、queue），此处创建一个 Topic对象。
        Topic topic = session.createTopic("test-topic");
        // 6）使用 Session 对象创建一个 Producer 对象。
        MessageProducer producer = session.createProducer(topic);
        // 7）创建一个 Message 对象，创建一个 TextMessage 对象。
        TextMessage textMessage = session.createTextMessage("hello activeMq,this is my first test-topic.");
        // 8）使用 Producer 对象发送消息。
        producer.send(textMessage);
        // 9）关闭资源。
        producer.close();
        session.close();
        connection.close();
    }
}
