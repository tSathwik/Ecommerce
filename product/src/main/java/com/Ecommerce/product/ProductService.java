package com.Ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.Ecommerce.product.DTO.InventoryUpdateRequest;
import com.Ecommerce.product.DTO.ProductRequest;
import com.Ecommerce.product.DTO.ProductResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    private WebClient webClient;

    public List<ProductResponse> getAllProducts() {
        
        return repo.findAll().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public void createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
                repo.save(product);
        //update the inventory
        InventoryUpdateRequest inventoryRequest = InventoryUpdateRequest.builder()
                .productId(product.getId())
                .quantity(productRequest.getQuantity())
                .build();

        // Make the WebClient call to update inventory
        webClient.post()
                .uri("http://8083/api/inventory/updateStock")
                .bodyValue(inventoryRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


    public ProductResponse getProductById(Long id) {
        return repo.findById(id)
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
        
    }

        public boolean CheckProductAvailability(Long id) {
                boolean availability = webClient.get()
                        .uri("http://8083/api/inventory/inStock?productId=" + id)
                        .retrieve()
                        .bodyToMono(boolean.class)
                        .block();
                return availability;
        }

}

    // public void updateStock(Long id, Integer quantity) {
    //     Product product = getProductById(id);
    //     if (product.getStock() < quantity) {
    //         throw new RuntimeException("Insufficient stock");
    //     }
    //     product.setStock(product.getStock() - quantity);
    //     repo.save(product);
    // }


