package com.Ecommerce.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repo;

    @Autowired
    private WebClient webClient;

    public void createCustomer(Customer customer) {
        Customer newCustomer = new Customer();
        newCustomer.setFirstname(customer.getFirstname());
        newCustomer.setLastname(customer.getLastname());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAddress(customer.getAddress());
        repo.save(newCustomer);
    }

    public void updateCustomer(long id, Customer customer) {
        Optional<Customer> existingCustomer = repo.findById(id);
        if (existingCustomer.isPresent()) {
            customer.setId(id);
            repo.save(customer);
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found.");
        }
    }

    public void deleteCustomer(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Customer with ID " + id + " not found.");
        }
    }

    public Customer getCustomerById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer with ID " + id + " not found."));
    }

    public List<Order> getCustomerOrderHistory(long customerId) {
        return webClient.get()
                               .uri("http://localhost:8083/api/order/getOrdersByCustomerId/{customerId}", customerId)
                               .retrieve()
                               .bodyToFlux(Order.class)
                               .collectList()
                               .block();
    }
}
