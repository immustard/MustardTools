package cn.buli_home.utils.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaUtils {

    private static final AsyncSendCallback callback = new AsyncSendCallback();

    public static KafkaProducer<String, String> getStringProducer(String servers) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", servers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(properties);
    }

    /**
     * 发送异步消息 (打印日志)
     * @param producer KafkaProducer<String, String>
     * @param topic 主题
     * @param value 消息内容
     */
    public static void sendAsyncMessage(KafkaProducer<String, String> producer, String topic, String value) {
        sendAsyncMessage(producer, topic, value, true);
    }

    /**
     * 发送异步消息 (打印日志)
     * @param producer KafkaProducer<String, String>
     * @param topic 主题
     * @param value 消息内容
     * @param isLog 是否打印日志
     */
    public static void sendAsyncMessage(KafkaProducer<String, String> producer, String topic, String value, boolean isLog) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);

        callback.isLog = isLog;

        producer.send(record, callback);
    }

    /**
     * 获取所有主题列表
     */
    public static Set<String> getTopics(String servers) throws ExecutionException, InterruptedException {
        AdminClient adminClient = p_getAdminClient(servers);
        ListTopicsResult result = adminClient.listTopics();
        adminClient.close();

        Set<String> set = result.names().get();

        return set;
    }

    /**
     * 创建主题
     * @param servers Kafka服务地址
     * @param topic 主题名
     * @param numPartitions 分区数
     * @param replicationFactor 副本数
     */
    public static void createTopic(String servers, String topic, int numPartitions, int replicationFactor) {
        AdminClient adminClient = p_getAdminClient(servers);

        NewTopic newTopic = new NewTopic(topic, numPartitions, (short) replicationFactor);
        adminClient.createTopics(Collections.singletonList(newTopic));
        adminClient.close();
    }

    public static AdminClient p_getAdminClient(String servers) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", servers);

        return KafkaAdminClient.create(properties);
    }

}
