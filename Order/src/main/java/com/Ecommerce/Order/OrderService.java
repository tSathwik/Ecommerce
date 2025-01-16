package com.Ecommerce.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private WebClient webClient;

    public void placeOrder(Order order) {
        orderRepository.save(order);
        webClient.post()
                .uri("http://localhost:8081/api/customer/updateOrderCount/{productId}/{quantity}")
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with ID " + id + " not found."));
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    //correct logic for deleteOrder method
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            Order order = orderRepository.findById(id).get();
            webClient.delete()
                    .uri("http://localhost:8081/api/customer/updateOrderCount/{productId}/{quantity}")
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            orderRepository.delete(order);
        } else {
            throw new RuntimeException("Order with ID " + id + " not found.");
        }
    }
}
