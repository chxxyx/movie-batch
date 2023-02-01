package com.chxxyx0.moviebatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling   // 스케쥴러 기능 활성화
@EnableBatchProcessing // 배치기능 활성화
@SpringBootApplication
public class MovieBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieBatchApplication.class, args);
	}

}
