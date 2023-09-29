package com.rkostiuk.cstask.dto.response;

public record ApiValidationError(String field, Object rejectedValue, UIMessage uiMessage) {
}
