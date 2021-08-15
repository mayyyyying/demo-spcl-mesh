//package io.alauda.demo.consumer.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignClientIntercepter implements RequestInterceptor {
//
//    private static final Log logger = LogFactory.getLog(FeignClientIntercepter.class);
//
//    public static final String X_CLIENT_NAME = "X-Client-Name";
//    public static final String X_CLIENT_NAMESPACE = "X-Client-Namespace";
//    public static final String DESTINATION_SERVICE = "destination_service";
//    public static final String DESTINATION_NAMESPACE = "destination_namespace";
//    public static final String CLIENT_NAME_KEY = "client_name";
//    public static final String CLIENT_NAMESPACE_KEY = "client_namespace";
//    public static final String REQUEST_OPERATION = "request_operation";
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        logger.info("FeignClient creation header injected");
//        requestTemplate.header(X_CLIENT_NAME,System.getenv("MICRO_SERVICE_NAME"));
//        requestTemplate.header(X_CLIENT_NAMESPACE, System.getenv("KUBERNETES_NAMESPACE"));
//    }
//}
