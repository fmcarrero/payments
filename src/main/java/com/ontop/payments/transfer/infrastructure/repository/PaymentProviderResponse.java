package com.ontop.payments.transfer.infrastructure.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentProviderResponse {
    @JsonProperty("requestInfo")
    private RequestInfo requestInfo;
    @JsonProperty("paymentInfo")
    private PaymentInfo paymentInfo;
    @Data
    public static class RequestInfo{
        @JsonProperty("status")
        private String status;
    }

    @Data
    public static class PaymentInfo{
        @JsonProperty("amount")
        private long amount;

        @JsonProperty("id")
        private String id;
    }
}
