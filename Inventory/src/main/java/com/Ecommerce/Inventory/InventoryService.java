package com.Ecommerce.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryRepository repo;

    @Transactional(readOnly = true)
    public boolean isInStock(String productId) {
        Inventory inventory = repo.findByProductId(productId);
        if (inventory == null) {
            log.warn("ProductId: {} not found in inventory", productId);
            return false;
        }
        return inventory.getQuantity() > 0;
    }

    @Transactional
    public void updateStock(String productId, int quantity) {
        Inventory inventory = repo.findByProductId(productId);
        if (inventory == null) {
            log.info("ProductId: {} not found in inventory. Creating new entry.", productId);
            inventory = new Inventory();
            inventory.setProductId(productId);
            inventory.setQuantity(quantity);
        }
        inventory.setQuantity(quantity);
        repo.save(inventory);
        log.info("Stock updated for ProductId: {}, Quantity: {}", productId, quantity);
    }

    @Transactional
    public void reduceStock(String productId, int quantity) {
        Inventory inventory = repo.findByProductId(productId);
        if (inventory == null || inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for ProductId: " + productId);
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        repo.save(inventory);
        log.info("Stock reduced for ProductId: {}, Quantity: {}", productId, quantity);
    }

    @Transactional(readOnly = true)
    public int getStock(String productId) {
        Inventory inventory = repo.findByProductId(productId);
        if (inventory == null) {
            log.warn("ProductId: {} not found in inventory", productId);
            return 0;
        }
        return inventory.getQuantity();
    }
}
