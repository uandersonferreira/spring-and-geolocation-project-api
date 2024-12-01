package br.com.uanderson.simple_driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringSimpleDriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSimpleDriverApplication.class, args);
	}

}
