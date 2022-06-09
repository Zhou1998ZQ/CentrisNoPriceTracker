package com.zzq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CentrisApplicaiton {

    public static void main(String[] args) {

        SpringApplication.run(CentrisApplicaiton.class, args);

    }
}
