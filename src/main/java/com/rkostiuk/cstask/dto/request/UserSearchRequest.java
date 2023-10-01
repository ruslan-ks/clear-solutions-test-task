package com.rkostiuk.cstask.dto.request;

import java.time.LocalDate;

public record UserSearchRequest(LocalDate from, LocalDate to) {
}
