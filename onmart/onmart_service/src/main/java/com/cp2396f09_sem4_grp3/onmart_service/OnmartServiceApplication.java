package com.cp2396f09_sem4_grp3.onmart_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cp2396f09_sem4_grp3.onmart_service.config.ServiceUrlConfig;

@SpringBootApplication
@EnableConfigurationProperties(ServiceUrlConfig.class)
@EnableScheduling
public class OnmartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnmartServiceApplication.class, args);
	}

}
