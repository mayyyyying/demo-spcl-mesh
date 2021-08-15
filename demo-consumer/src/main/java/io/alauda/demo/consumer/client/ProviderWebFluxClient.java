package io.alauda.demo.consumer.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ProviderWebFluxClient {
    @RequestMapping(method = RequestMethod.GET,path = "/demo/webclient/ping")
    String ping();//

    @RequestMapping(method = RequestMethod.POST,path = "/demo/webclient/ping")
    String postPing();

    @RequestMapping(method = RequestMethod.GET,path = "/demo/webclient/error")
    String error();
}
