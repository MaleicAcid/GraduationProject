package com.sqlist.clientweb.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.sqlist.clientweb.domain.Temperature;
import com.sqlist.clientweb.service.TemperatureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author SqList
 * @date 2019/5/12 15:31
 * @description
 **/
@Slf4j
@Component
public class TemperatureReceiver {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TemperatureService temperatureService;

    @KafkaListener(topics = {"warehouse-temperature"})
    public void listen(ConsumerRecord<String, String> record) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            String message = kafkaMessage.get();

            log.info("receive message: {}", message);
            Temperature temperature = JSON.parseObject(message, Temperature.class);
            temperatureService.add(temperature);
        }
    }
}
