package cn.buli_home.utils.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AsyncSendCallback implements Callback {

    private final static Logger log = LogManager.getLogger(AsyncSendCallback.class);

    public boolean isLog = true;

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            e.printStackTrace();
        }

        if (null != recordMetadata && isLog) {
            log.debug("返回结果: topic->" + recordMetadata.topic() + ", partition->" + recordMetadata.partition() +  ", offset->" + recordMetadata.offset());
        }
    }
}
