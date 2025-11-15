package com.oasys.OrderService;

import com.oasys.common_module.clients.external.PaymentService;
import com.oasys.common_module.clients.external.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableFeignClients(basePackages = "com.oasys.common.clients.external")
@ComponentScan(basePackages = {"com.oasys.OrderService", "com.oasys.common_module"})
@EnableFeignClients(clients = {ProductService.class, PaymentService.class})
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
