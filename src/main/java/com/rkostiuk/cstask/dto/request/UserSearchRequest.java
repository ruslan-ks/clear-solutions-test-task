package com.rkostiuk.cstask.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserSearchRequest(@NotNull LocalDate from, @NotNull LocalDate to) {
}
