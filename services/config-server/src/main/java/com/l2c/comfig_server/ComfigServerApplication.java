package com.l2c.comfig_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ComfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComfigServerApplication.class, args);
	}

}
