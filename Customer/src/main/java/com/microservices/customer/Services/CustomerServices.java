package com.microservices.customer.Services;

import com.microservices.customer.Dto.CustomerRegistrationRequest;
import com.microservices.customer.Entity.Customer;
import com.microservices.customer.Repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerServices {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;


    public void registerCustomer(final CustomerRegistrationRequest request) {
        final Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        this.customerRepository.saveAndFlush(customer);

        this.restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                Customer.class, customer.getId());
        
        if (this.customerRepository.existsById(customer.getId())) {
            throw new IllegalStateException("Customer with id " + customer.getFirstName() + " " + customer.getLastName() + " already exists");
        }
    }
}