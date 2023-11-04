package com.ontop.payments.shared.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PageResponse<T>(
        @JsonProperty("data")
        T content,
        @JsonProperty("total")
        Long total

) {

}