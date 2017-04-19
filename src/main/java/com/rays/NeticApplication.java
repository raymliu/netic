package com.rays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class NeticApplication {
	private static final Logger log = LoggerFactory.getLogger(NeticApplication.class);

	@Inject
	private  Environment env;


	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(NeticApplication.class);
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		//SpringApplication.run(NeticApplication.class, args);
		Environment env = app.run(args).getEnvironment();
		log.info("Access URLs:\n-----------------------------------------------------\n\t" +
				"Local:\t\thttp://127.0.0.1:{}\n\t"+
				"External: \thttp://{}:{}\n-----------------------------------------\n\t",
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port")
				);

	}
}
