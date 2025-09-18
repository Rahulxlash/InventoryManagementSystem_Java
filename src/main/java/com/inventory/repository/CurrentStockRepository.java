package com.inventory.repository;

import com.inventory.entity.CurrentStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentStockRepository extends JpaRepository<CurrentStock, String> {
    
    Optional<CurrentStock> findByProductCode(String productCode);
    
    boolean existsByProductCode(String productCode);
    
    @Modifying
    @Query("UPDATE CurrentStock cs SET cs.quantity = cs.quantity + :quantity WHERE cs.productCode = :productCode")
    void increaseStock(@Param("productCode") String productCode, @Param("quantity") Integer quantity);
    
    @Modifying
    @Query("UPDATE CurrentStock cs SET cs.quantity = cs.quantity - :quantity WHERE cs.productCode = :productCode")
    void decreaseStock(@Param("productCode") String productCode, @Param("quantity") Integer quantity);
    
    @Query("SELECT cs FROM CurrentStock cs JOIN FETCH cs.product p ORDER BY p.prodName")
    List<CurrentStock> findAllWithProduct();
}
