package com.zzq.amqp;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.zzq.config.AmqpConfig;
import com.zzq.entity.Centris;
import com.zzq.mapper.CentrisMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CentrisWorker {

    private final AmqpTemplate amqpTemplate;
    private final CentrisMapper centrisMapper;

    @RabbitListener(queues = AmqpConfig.QUEUE_CENTRIS)
    public void receiver(Long centrisNo, Message message) {

        try {
            String url = "https://www.centris.ca/fr/maison~a-vendre~gatineau-hull/" + centrisNo;
            Document document = Jsoup.connect(url).get();
            String price = document.selectFirst("span[class=text-nowrap]").text();
            centrisMapper.insert(new Centris().setCentrisNo(centrisNo)
                    .setPrice(price)
            );

        } catch (Exception e) {

            Integer retry = Convert.toInt(message.getMessageProperties().getHeader("retry"), 0);

            if (retry < 3) {
                log.info(StrUtil.format("CentrisWorker info  {} ,retry times :", centrisNo, retry), e);
                amqpTemplate.convertAndSend(AmqpConfig.EXCHANGE_CENTRIS, AmqpConfig.ROUTING_KEY_CENTRIS, centrisNo, msg -> {
                    msg.getMessageProperties().setHeader("retry", retry + 1);
                    return msg;
                });
            }

        }
    }
}
