package com.zzq.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class CoreConfig {

    @Bean
    MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("deleted", false, metaObject);
                this.setFieldValByName("createdAt", LocalDateTime.now(), metaObject);
                this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
            }
        };
    }
}
