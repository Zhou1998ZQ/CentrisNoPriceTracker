package com.zzq.job;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzq.config.AmqpConfig;
import com.zzq.entity.Centris;
import com.zzq.mapper.CentrisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CentrisJob {

    private final CentrisMapper centrisMapper;
    private final AmqpTemplate amqpTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 10 * * ?")
    public void printTime() {
        List<Centris> centrises = centrisMapper.selectList(new LambdaQueryWrapper<Centris>().eq(Centris::getIsActive, Boolean.TRUE));

        if (centrises.isEmpty())
            return;

        centrises.stream().map(Centris::getCentrisNo).collect(Collectors.toSet()).forEach(
                centrisNo -> {
                    amqpTemplate.convertAndSend(AmqpConfig.EXCHANGE_CENTRIS,AmqpConfig.ROUTING_KEY_CENTRIS,centrisNo);
                }
        );


    }
}
