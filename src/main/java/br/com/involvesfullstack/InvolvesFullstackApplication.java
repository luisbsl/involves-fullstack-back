package br.com.involvesfullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class InvolvesFullstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvolvesFullstackApplication.class, args);
	}

}
