package cn.buli_home.utils.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public class AsyncSendCallback implements Callback {

    public boolean isLog = true;

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            log.error(e.getMessage());
        }

        if (null != recordMetadata && isLog) {
            log.info("返回结果: topic->{}, partition->{}, offset->{}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        }
    }
}
