package com.rkostiuk.cstask.dto;

import java.time.LocalDate;

public record UserSearchRequest(LocalDate fromIncluding, LocalDate toExcluding) {
}
