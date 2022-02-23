package com.bankapplication.bankapplication;

import com.bankapplication.bankapplication.Model.Customer;
import com.bankapplication.bankapplication.Model.Role;
import com.bankapplication.bankapplication.Service.CustomerService;
import com.bankapplication.bankapplication.Types.RoleType;
import com.bankapplication.bankapplication.Util.Util;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class BankApplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplicationApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(CustomerService customerService) {
		return args -> {

			customerService.saveRole(new Role(1L, RoleType.USER));
			customerService.saveRole(new Role(2L, RoleType.ADMIN));

			Util util = new Util();
			customerService.saveCustomer(new Customer(1L, "Oakley", "Burns", "oakleyburns@gmail.com",
					"1234", BigDecimal.ZERO, util.currentDate()));
			customerService.saveCustomer(new Customer(2L, "Chris", "May", "chrismay@gmail.com",
					"1234", BigDecimal.ZERO, util.currentDate()));

		};
	}


}
