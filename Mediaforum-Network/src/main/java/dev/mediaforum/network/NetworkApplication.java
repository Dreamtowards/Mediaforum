package dev.mediaforum.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetworkApplication {

    public static final String API_BASE = "http://192.168.1.110:8080";

    public static void main(String[] args) {
        SpringApplication.run(NetworkApplication.class, args);
    }

}
