package com.example.server;

import com.example.server.enumeration.Status;
import com.example.server.model.Server;
import com.example.server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.server.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16 GB",
					"Personal PC", "http://localhost:8080/server/image/server_1.png", SERVER_UP ));
			serverRepository.save(new Server(null, "192.168.1.10", "Red Hat Enterprise Linux", "64 GB",
					"Mail Server", "http://localhost:8080/server/image/server_1.png", SERVER_UP ));
			serverRepository.save(new Server(null, "192.168.1.16", "Ubuntu Linux", "32 GB",
					"Web Server", "http://localhost:8080/server/image/server_1.png", SERVER_UP ));
			serverRepository.save(new Server(null, "192.168.1.71", "Fedora Linux", "16 GB",
					"Personal PC", "http://localhost:8080/server/image/server_1.png", SERVER_UP ));
		};
	}

}
