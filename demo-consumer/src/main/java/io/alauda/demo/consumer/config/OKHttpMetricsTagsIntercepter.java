//package io.alauda.demo.consumer.config;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.Tag;
//import io.micrometer.core.instrument.Timer;
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
//import org.springframework.boot.actuate.metrics.AutoTimer;
//import org.springframework.boot.actuate.metrics.http.Outcome;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//import static io.alauda.demo.consumer.config.FeignClientIntercepter.*;
//
//@Component
//public class OKHttpMetricsTagsIntercepter implements Interceptor {
//
//    private static final Log logger = LogFactory.getLog(OKHttpMetricsTagsIntercepter.class);
//    private final MeterRegistry meterRegistry;
//    private final String metricName;
//    private final AutoTimer autoTimer;
//
//    @Autowired
//    private OperationRulesParser parser;
//
//    public OKHttpMetricsTagsIntercepter(MeterRegistry meterRegistry, MetricsProperties metricsProperties){
//        this.meterRegistry = meterRegistry;
//        this.metricName = metricsProperties.getWeb().getClient().getRequest().getMetricName();
//        this.autoTimer = metricsProperties.getWeb().getClient().getRequest().getAutotime();
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        if (!this.autoTimer.isEnabled()) {
//            return chain.proceed(chain.request());
//        } else {
//            long startTime = System.nanoTime();
//            Response response = null;
//
//            try {
//                response = chain.proceed(chain.request());
//            } finally {
//                try {
//
//                    this.getTimeBuilder(chain.request(), response)
//                            .register(this.meterRegistry)
//                            .record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
//
//                } catch (Exception var14) {
//                    logger.info("Failed to record metrics.", var14);
//                }
//            }
//
//            return response;
//        }
//    }
//
//    private Timer.Builder getTimeBuilder(Request request, Response response) {
//        return this.autoTimer.builder(this.metricName).tags(this.getTags(request, response)).description("Timer of OKHttpClient operation");
//    }
//
//    private Iterable<Tag> getTags(Request request, Response response) {
//        String statusCode = response !=null ? String.valueOf(response.code()): "CLIENT_ERROR";
//        String host = request.url().host();
//        if (host == null) {
//            host = "none";
//        }
//
//        String destinationService;
//        String destinationNamespace;
//
//        if(host.contains(".")){
//            String[] hosts = host.split("\\.");
//            destinationService = hosts[0];
//            destinationNamespace = hosts[1];
//        }
//        else {
//            destinationService = "localhost".equalsIgnoreCase(host) ? System.getenv("MICRO_SERVICE_NAME") : host;
//            destinationNamespace = System.getenv("KUBERNETES_NAMESPACE");
//        }
//
//        Tag uriTag = Tag.of("uri",request.url().uri().getPath());
//        Tag methodTag = Tag.of("method",request.method());
//        Tag status = Tag.of("status",statusCode);
//
//        Tag clientTag = Tag.of(CLIENT_NAME_KEY, request.header(X_CLIENT_NAME));
//        Tag clientNamespaceTag = Tag.of(CLIENT_NAMESPACE_KEY, request.header(X_CLIENT_NAMESPACE));
//
//        Tag destTag = Tag.of(DESTINATION_SERVICE,destinationService);
//        Tag destNamespaceTag = Tag.of(DESTINATION_NAMESPACE,destinationNamespace);
//
//        Tag operationTag = Tag.of(REQUEST_OPERATION, parser.parseClassification(request));
//        Tag outcomeTag = Outcome.UNKNOWN.asTag();
//
//        if(response == null){
//            outcomeTag = Tag.of("outcome", "CLIENT_ERROR");
//        }
//
//        return Arrays.asList(
//                methodTag,
//                uriTag,
//                status,
//                clientTag,
//                clientNamespaceTag,
//                destTag,
//                destNamespaceTag,
//                operationTag,
//                outcomeTag);
//    }
//}
