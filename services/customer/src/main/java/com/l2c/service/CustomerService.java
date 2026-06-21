package com.l2c.service;

import com.l2c.customer.Customer;
import com.l2c.customer.CustomerMapper;
import com.l2c.dto.CustomerRequest;
import com.l2c.dto.CustomerResponse;
import com.l2c.repository.CustomerRepository;
import com.l2c.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(@Valid CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Can not update customer. Nu customer with the provided ID: %s", request.id())
                ));

        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }

        if(StringUtils.isNotBlank(request.lastname())) {
            customer.setFirstname(request.lastname());
        }

        if(StringUtils.isNotBlank(request.email())) {
            customer.setFirstname(request.email());
        }

        if(request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public @Nullable List<CustomerResponse> findAllCustomers() {
         List<Customer> customers = repository.findAll();

         List<CustomerResponse> customerResponses = customers.stream()
                 .map(mapper::fromCustomer)
                 .toList();
         return customerResponses;
    }

    public Boolean existsById(String id) {
        return repository.findById(id)
                .isPresent();
    }


    public CustomerResponse findCustomerById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("No customer found with the provided ID: %s", customerId)));

    }

    public void deleteById(String customerId) {
        repository.deleteById(customerId);
    }
}
