package com.rkostiuk.cstask.validation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public interface ValidAddress {

    @NotEmpty
    String getCity();

    @NotEmpty
    String getStreet();

    @Positive
    int getBuilding();

    @Positive
    Integer getApartment();
}
