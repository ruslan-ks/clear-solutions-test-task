package com.rkostiuk.cstask.dto.response;

public record FieldValidationError(String field, Object rejectedValue, UIMessage uiMessage) {
}
