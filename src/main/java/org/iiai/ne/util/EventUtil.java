package org.iiai.ne.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class EventUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventUtil.class);

    public static void sendEventAsync(KafkaTemplate kafkaTemplate, String topic, String msg) {
        kafkaTemplate.send(topic, msg).addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("Send event fail, msg: {}, exception: {}", msg, ex);
            }

            @Override
            public void onSuccess(Object result) {
                LOGGER.info("event sent successful, topic: {}, msg: {}", topic, msg);
            }
        });
    }
}
