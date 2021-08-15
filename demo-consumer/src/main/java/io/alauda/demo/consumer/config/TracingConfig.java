//package io.alauda.demo.consumer.config;
//
//import brave.http.HttpRequest;
//import brave.http.HttpRequestParser;
//import brave.http.HttpResponseParser;
//import lombok.SneakyThrows;
//import org.springframework.cloud.sleuth.instrument.web.HttpClientRequestParser;
//import org.springframework.cloud.sleuth.instrument.web.HttpClientResponseParser;
//import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser;
//import org.springframework.cloud.sleuth.instrument.web.HttpServerResponseParser;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.URL;
//
//@Configuration
//public class TracingConfig {
//
//    @Bean(name = { HttpClientRequestParser.NAME, HttpServerRequestParser.NAME })
//    HttpRequestParser sleuthHttpServerRequestParser() {
//        return (req, context, span) -> {
//            HttpRequestParser.DEFAULT.parse(req, context, span);
//            span.tag("upstream_cluster", getUpstreamCluster(req));
//            span.name(getOperationName(req));
//        };
//    }
//    @Bean(name = { HttpClientResponseParser.NAME, HttpServerResponseParser.NAME })
//    HttpResponseParser sleuthHttpServerResponseParser(){
//        return (resp, context, span) -> {
//            HttpResponseParser.DEFAULT.parse(resp, context, span);
//            span.tag("http.status_code", Integer.toString(resp.statusCode()));
//            span.name(getOperationName(resp.request()));
//        };
//    }
//    @SneakyThrows
//    private String getOperationName(HttpRequest req){
//        URL url = new URL(req.url());
//        return url.getHost() + ":" + url.getPort() + url.getPath();
//    }
//
//    @SneakyThrows
//    private String getUpstreamCluster(HttpRequest req){
//        URL url = new URL(req.url());
//        return "outbound|" + url.getPort() + "|" + url.getHost();
//    }
//
//}
