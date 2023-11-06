package tech.chickies.notificationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class NotificationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServerApplication.class, args);
	}

}
