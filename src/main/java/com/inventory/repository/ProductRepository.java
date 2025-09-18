package com.inventory.repository;

import com.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    Optional<Product> findByProdCode(String prodCode);
    
    boolean existsByProdCode(String prodCode);
    
    boolean existsByProdNameAndCostPriceAndSellPriceAndBrand(String prodName, Double costPrice, Double sellPrice, String brand);
    
    @Query("SELECT p FROM Product p WHERE " +
           "p.prodCode LIKE %:searchText% OR " +
           "p.prodName LIKE %:searchText% OR " +
           "p.brand LIKE %:searchText%")
    List<Product> searchProducts(@Param("searchText") String searchText);
    
    @Query("SELECT p.prodName FROM Product p WHERE p.prodCode = :prodCode")
    Optional<String> findProductNameByCode(@Param("prodCode") String prodCode);
}
