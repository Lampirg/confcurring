package dev.lampirg.confcurring;

import dev.lampirg.confcurring.confprop.ConcurrentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConcurrentProperties.class)
public class ConfcurringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfcurringApplication.class, args);
	}

}
