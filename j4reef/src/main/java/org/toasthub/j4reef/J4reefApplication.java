package org.toasthub.j4reef;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class J4reefApplication {

	public static void main(String[] args) {
		SpringApplication.run(J4reefApplication.class, args);
	}

}
