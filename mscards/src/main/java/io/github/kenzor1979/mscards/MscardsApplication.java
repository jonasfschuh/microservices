package io.github.kenzor1979.mscards;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
public class MscardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscardsApplication.class, args);
	}

}
