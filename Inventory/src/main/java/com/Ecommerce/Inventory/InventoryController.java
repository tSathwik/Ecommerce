package com.Ecommerce.Inventory;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {
    @Autowired
    private InventoryService service;

    @GetMapping("/inStock")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String productId) {
        log.info("Received inventory check request for ProductId: {}", productId);
        return service.isInStock(productId);
    }

    @PutMapping("/updateStock")
    @ResponseStatus(HttpStatus.OK)
    public void updateStock(@RequestParam String productId, @RequestParam int quantity) {
        log.info("Received stock update request for ProductId: {}, Quantity: {}", productId, quantity);
        service.updateStock(productId, quantity);
    }

    @PutMapping("/reduce-stock")
    @ResponseStatus(HttpStatus.OK)
    public void reduceStock(@RequestParam String productId, @RequestParam int quantity) {
        log.info("Received stock reduction request for ProductId: {}, Quantity: {}", productId, quantity);
        service.reduceStock(productId, quantity);
    }

    @GetMapping("/get-stock")
    @ResponseStatus(HttpStatus.OK)
    public int getStock(@RequestParam String productId) {
        log.info("Received request to get stock for ProductId: {}", productId);
        return service.getStock(productId);
    }
}
