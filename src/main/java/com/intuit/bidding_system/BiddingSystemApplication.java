package com.intuit.bidding_system;

import com.intuit.bidding_system.model.request.MyOptionalClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class BiddingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiddingSystemApplication.class, args);

//		Intuit coding round was to implement Optionals from scratch
		String value1 = "check";
		MyOptionalClass<String> mayBeValue1 = new MyOptionalClass<String>(value1);

		if (mayBeValue1.isPresent()) {
			System.out.println(mayBeValue1.getValue().toString());
		}

		String value2 = null;

		MyOptionalClass<String> mayBeValue2 = new MyOptionalClass<String>(value2);

		if (mayBeValue2.isPresent()) {
			System.out.println(mayBeValue2.getValue().toString());
		}
	}

}
