package io.alauda.demo.consumer.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RibbonClient(name = "demo-provider-ribbon")
public interface ProviderRibbonClient {

    @RequestMapping(method = RequestMethod.GET, path = "demo/ribbon/ping")
    String ping();

    @RequestMapping(method = RequestMethod.POST, path = "demo/ribbon/ping")
    String postPing();

    @RequestMapping(method = RequestMethod.GET, path = "demo/ribbon/erro")
    String error();
}