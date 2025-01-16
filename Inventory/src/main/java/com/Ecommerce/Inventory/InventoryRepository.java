// package com.Ecommerce.Inventory;

// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.List;

// public interface InventoryRepository extends JpaRepository<Inventory,Long>{
//     List<Inventory> findBySkuCodeIn(List<String> skuCode);

// }
package com.Ecommerce.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductId(String productId);
}
