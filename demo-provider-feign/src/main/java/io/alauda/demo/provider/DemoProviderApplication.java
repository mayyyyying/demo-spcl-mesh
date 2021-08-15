package io.alauda.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemoProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoProviderApplication.class, args);
    }

}
