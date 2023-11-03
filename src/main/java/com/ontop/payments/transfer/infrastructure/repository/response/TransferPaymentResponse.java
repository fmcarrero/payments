package com.ontop.payments.transfer.infrastructure.repository.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferPaymentResponse {
    @JsonProperty("requestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("paymentInfo")
    private PaymentInfo paymentInfo;

    @Builder
    @Data
    public static class PaymentInfo{
        @JsonProperty("amount")
        private long amount;

        @JsonProperty("id")
        private String id;
    }
    @Data
    @Builder
    public static class RequestInfo{
            @JsonProperty("status")
            private String status;
    }
}
