package io.alauda.demo.consumer.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ProviderRestTemplateClient {
    @RequestMapping(method = RequestMethod.GET,path = "/demo/resttemplate/ping")
    String ping();

    @RequestMapping(method = RequestMethod.POST,path = "/demo/resttemplate/ping")
    String postPing();

    @RequestMapping(method = RequestMethod.GET,path = "/demo/resttemplate/error")
    String error();
}