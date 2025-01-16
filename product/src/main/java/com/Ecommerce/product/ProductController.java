package com.Ecommerce.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.Ecommerce.product.DTO.ProductRequest;
import com.Ecommerce.product.DTO.ProductResponse;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        
    }

    @GetMapping("/getAllProducts")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProductById{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/CheckProductAvailability/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean CheckProductAvailability(@PathVariable Long id) {
        return productService.CheckProductAvailability(id);
    }


    // @PostMapping("/updateProduct/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public void updateProduct(@PathVariable Long id, @RequestBody int quantity) {
    //     productService.updateProduct(id, quantity);
    // }

    
}
