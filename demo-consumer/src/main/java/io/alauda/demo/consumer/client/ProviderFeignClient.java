package io.alauda.demo.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient("feign-provider.ym-test") // 这个不行
@FeignClient("demo-provider-feign")
public interface ProviderFeignClient {
    @RequestMapping(method = RequestMethod.GET,path = "/demo/feign/ping")
    String ping();

    @RequestMapping(method = RequestMethod.POST,path = "/demo/feign/ping")
    String postPing();

    @RequestMapping(method = RequestMethod.GET,path = "/demo/feign/error")
    String error();
}