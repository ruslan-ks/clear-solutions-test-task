package com.rkostiuk.cstask.dto.request;

import com.rkostiuk.cstask.validation.ValidAddress;

import java.util.Objects;

public class NewAddressRequest implements ValidAddress {
    private String city;
    private String street;
    private int building;
    private Integer apartment;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewAddressRequest request)) {
            return false;
        }
        return building == request.building
                && Objects.equals(city, request.city)
                && Objects.equals(street, request.street)
                && Objects.equals(apartment, request.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, building, apartment);
    }

    @Override
    public String toString() {
        return "NewAddressRequest{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building=" + building +
                ", apartment=" + apartment +
                '}';
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    @Override
    public Integer getApartment() {
        return apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }
}
