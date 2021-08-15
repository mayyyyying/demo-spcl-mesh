package io.alauda.demo.consumer.controller;

import io.alauda.demo.consumer.client.ProviderFeignClient;
import io.alauda.demo.consumer.client.ProviderRibbonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/demo")
@RestController
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    ProviderFeignClient providerFeignClient;

    ProviderRibbonClient providerRibbonClient;

    @Value("${spring.application.name}")
    String appName;

    //feign
    @GetMapping("/feign/ping")
    public String feign(){
        return providerFeignClient.ping();
    }

    //webclient
    @GetMapping("/webclient/ping")
    public Mono<String> webclient(){
        System.out.println("web client being called");
        return WebClient.create().get().uri("http://demo-provider-webclient.ym-kingdom:8080/demo/webclient/ping")
                .retrieve().bodyToMono(String.class);
    }

    //rest template
    @Autowired
    RestTemplateBuilder pureRestTemplateBuilder;

    @GetMapping("/resttemplate/ping")
    public String rest(){
        RestTemplate restTemplate = pureRestTemplateBuilder
                .build();
        System.out.println("rest template being called");
        return restTemplate.execute("http://demo-provider-resttemplate.ym-kingdom:8080/demo/resttemplate/ping", HttpMethod.GET, null, null);
    }

    //ribbon rest template
    @Bean
    @LoadBalanced // this annotation will indicate that the resttemplate will go through ribbon
    public RestTemplate RestTemplate(){
        return new RestTemplate();
    }
    @Autowired
    private RestTemplate ribbonRestTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/ribbon/ping")
    public String ribbonRest() {
        System.out.println("ribbon rest template being called");

        //Load Balancer Client find the target service by svc name
        ServiceInstance serviceInstance = loadBalancerClient.choose("demo-provider-ribbon");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/demo/ribbon/ping";
        logger.info("url ------- " + url);
        // Attention: The following code will not work for this ribbon rest template
        //URI uri = URI.create(String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()));
        URI uri = URI.create(String.format("http://%s", serviceInstance.getServiceId()));
        logger.info("uri ------- " + uri);
        return ribbonRestTemplate.getForObject(uri + "/demo/ribbon/ping", String.class);
    }

    //helper method to proof the load balancer client can get service info by svc name
    @GetMapping("/ribbon/logInstance")
    public void logInstance() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("demo-provider-ribbon");
        logger.info(serviceInstance.getServiceId());
        logger.info(serviceInstance.getHost());
        logger.info(String.valueOf(serviceInstance.getPort()));
    }

    @PostMapping("/ping")
    public String consumerPost(){
        return providerFeignClient.postPing();
    }

    @GetMapping("/hello")
    public String hello(){
        return appName;
    }

    @GetMapping("/error")
    public String error(){
        return providerFeignClient.error();
    }
}
