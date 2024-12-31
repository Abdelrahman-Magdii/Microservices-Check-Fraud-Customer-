package com.microservices.customer.Dto;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {


}
