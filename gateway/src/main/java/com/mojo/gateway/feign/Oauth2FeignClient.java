package com.mojo.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author mojo
 * @description:
 * @date 2023/7/22 0022 16:55
 */
@FeignClient("oauth2-service")
public interface Oauth2FeignClient {

    @RequestMapping("/oauth/check_token")
    Map<String, Object> checkToken(String oauth);
}
