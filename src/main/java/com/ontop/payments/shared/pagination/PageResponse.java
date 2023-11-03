package com.ontop.payments.shared.pagination;

public record PageResponse<T>(
        T content,
        String previousPageCursor,
        String nextPageCursor
) { }