//package io.alauda.demo.consumer.config;
//
//import io.micrometer.core.instrument.Tag;
//import okhttp3.OkHttpClient;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTags;
//import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTagsProvider;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//
//import static io.alauda.demo.consumer.config.FeignClientIntercepter.*;
//
//@EnableConfigurationProperties(OperationRules.class)
//@Configuration
//public class WebClientConfig {
//
//    private static final Log logger = LogFactory.getLog(WebClientConfig.class);
//
//    @Autowired
//    private OperationRulesParser parser;
//
//    @Bean
//    public OkHttpClient.Builder okHttpBuilder(OKHttpMetricsTagsIntercepter okHttpMetricsTagsIntercepter){
//        logger.info("OkHttpClient intercepter injected");
//        return new OkHttpClient.Builder().addInterceptor(okHttpMetricsTagsIntercepter);
//    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
//        logger.info("Rest Template creation header injected");
//        return templateBuilder
//                .requestFactory(OkHttp3ClientHttpRequestFactory::new)
//                .defaultHeader(X_CLIENT_NAME, System.getenv("MICRO_SERVICE_NAME"))
//                .defaultHeader(X_CLIENT_NAMESPACE, System.getenv("KUBERNETES_NAMESPACE"))
//                .build();
//    }
//
//    @Bean
//    public RestTemplateExchangeTagsProvider restTemplateExchangeTagsProvider() {
//        return (urlTemplate, request, response) -> {
//            String destinationService;
//            String destinationNamespace;
//            String host = request.getURI().getHost();
//            if(host.contains(".")){
//                String[] hosts = host.split("\\.");
//                destinationService = hosts[0];
//                destinationNamespace = hosts[1];
//            }
//            else {
//                destinationService = "localhost".equalsIgnoreCase(host) ? System.getenv("MICRO_SERVICE_NAME") : host;
//                destinationNamespace = System.getenv("KUBERNETES_NAMESPACE");
//            }
//            Tag dsTag = Tag.of(DESTINATION_SERVICE, destinationService);
//            Tag dnsTag = Tag.of(DESTINATION_NAMESPACE, destinationNamespace);
//            logger.info("Adding metrics tag of destination service in rest template exchange tags provider === " + dsTag);
//            logger.info("Adding metrics tag of destination service namespace in rest template exchange tags provider === " + dnsTag);
//
//            return Arrays.asList(RestTemplateExchangeTags.method(request), dsTag, dnsTag,
//                    RestTemplateExchangeTags.status(response), RestTemplateExchangeTags.clientName(request),
//                    Tag.of(REQUEST_OPERATION, parser.parseClassification(request)));
//        };
//    }
//}
//
//
