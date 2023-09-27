package com.rkostiuk.cstask.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public interface ValidUser {

    @Email
    @NotEmpty
    String getEmail();

    @NotEmpty
    String getFirstName();

    @NotEmpty
    String getLastName();

    @NotNull
    @Past
    LocalDate getBirthDate();
}
