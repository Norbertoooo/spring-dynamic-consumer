package com.vitu.spring.dynamic.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "investmentClient", url = "${app.wiremock.url}")
public interface InvestmentClient {

    @GetMapping("/investimentos/{document}")
    ResponseEntity<?> getInvestmentsByDocument(@PathVariable String document);

}
