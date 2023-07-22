package com.mojo.gateway.filter;

import com.mojo.gateway.feign.Oauth2FeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

/**
 * @author mojo
 * @description: Oauth 2过滤器
 * @date 2023/7/22 0022 16:48
 */
@Order(0)
@Component
public class Oauth2Filter implements GlobalFilter, Ordered {

    /**
     * check token 使用rest访问
     * 启动时资源竞争，容易死锁，加上懒加载注解
     */
    @Lazy
    @Autowired
    private Oauth2FeignClient oauth2FeignClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //path url校验
        String path = request.getURI().getPath();
        if ( path.contains("/register")) {
            return chain.filter(exchange);
        }
        //校验token  不要oauth的token的bearer
        String token = request.getHeaders().getFirst("Authorization");
        Map<String, Object> checkedToken = oauth2FeignClient.checkToken(token);
        if (!Boolean.parseBoolean(String.valueOf(checkedToken.get("active")))) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //添加做log的tracingId，每次打印日志就带上tracingId，日志在kinbna上做查询，根据tracingId进行日志查询
        String tracingId = UUID.randomUUID().toString().replaceAll("-", "");
        ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
            httpHeaders.set("tracingId", tracingId);
        }).build();

        exchange.mutate().request(serverHttpRequest);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
