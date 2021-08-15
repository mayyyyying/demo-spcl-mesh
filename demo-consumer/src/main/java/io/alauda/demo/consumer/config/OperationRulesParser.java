//package io.alauda.demo.consumer.config;
//
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.Request;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.expression.Expression;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.http.HttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Objects;
//
//@Slf4j
//@Component
//public class OperationRulesParser {
//
//    @Autowired
//    private OperationRules rules;
//
//    public String parseClassification(HttpServletRequest request) {
//        if(Objects.isNull(rules) || Objects.isNull(rules.getClassification())) {
//            return OperationRule.UNKNOWN.getValue();
//        }
//        OperationRequest req = new OperationRequest();
//        req.setMethod(request.getMethod());
//        req.setUrl_path(request.getRequestURI());
//        return getRuleValue(req);
//    }
//
//    public String parseClassification(ServerWebExchange exchange) {
//        if(Objects.isNull(rules) || Objects.isNull(rules.getClassification())) {
//            return OperationRule.UNKNOWN.getValue();
//        }
//        OperationRequest req = new OperationRequest();
//        req.setMethod(exchange.getRequest().getMethod().name().toUpperCase());
//        req.setUrl_path(exchange.getRequest().getPath().value());
//        return getRuleValue(req);
//    }
//
//    public String parseClassification(HttpRequest request) {
//        if(Objects.isNull(rules) || Objects.isNull(rules.getClassification())) {
//            return OperationRule.UNKNOWN.getValue();
//        }
//        OperationRequest req = new OperationRequest();
//        req.setMethod(request.getMethod().name());
//        req.setUrl_path(request.getURI().getPath());
//        return getRuleValue(req);
//    }
//
//    public String parseClassification(Request request) {
//        if(Objects.isNull(rules) || Objects.isNull(rules.getClassification())) {
//            return OperationRule.UNKNOWN.getValue();
//        }
//        OperationRequest req = new OperationRequest();
//        req.setMethod(request.method());
//        req.setUrl_path(request.url().uri().getPath());
//        return getRuleValue(req);
//    }
//
//    private String getRuleValue(OperationRequest req) {
//        OperationRequestWrapper model = new OperationRequestWrapper();
//        model.setRequest(req);
//        OperationRule rule = rules.getClassification().stream()
//                .filter(r -> {
//                    ExpressionParser parser = new SpelExpressionParser();
//                    Expression exp = parser.parseExpression(r.getCondition());
//                    return (Boolean) exp.getValue(model);
//                })
//                .findFirst()
//                .orElse(OperationRule.UNKNOWN);
//        log.info("Got value {}", rule.getValue());
//        return rule.getValue();
//    }
//}
