package com.Ecommerce.Order;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody Order order) {
        service.placeOrder(order);
    }
    @GetMapping("/getOrderById/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
    }
    @GetMapping("/getOrdersByCustomerId/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
        return service.getOrdersByCustomerId(customerId);
    }
}
