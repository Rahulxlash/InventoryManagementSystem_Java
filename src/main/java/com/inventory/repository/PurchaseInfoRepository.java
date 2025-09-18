package com.inventory.repository;

import com.inventory.entity.PurchaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseInfoRepository extends JpaRepository<PurchaseInfo, Integer> {
    
    List<PurchaseInfo> findByProductCode(String productCode);
    
    List<PurchaseInfo> findBySupplierCode(String supplierCode);
    
    @Query("SELECT p FROM PurchaseInfo p JOIN FETCH p.product pr JOIN FETCH p.supplier s ORDER BY p.purchaseId")
    List<PurchaseInfo> findAllWithDetails();
    
    @Query("SELECT p FROM PurchaseInfo p JOIN p.product pr JOIN p.supplier s WHERE " +
           "p.purchaseId LIKE %:searchText% OR " +
           "p.productCode LIKE %:searchText% OR " +
           "pr.prodName LIKE %:searchText% OR " +
           "s.fullName LIKE %:searchText% OR " +
           "p.supplierCode LIKE %:searchText% OR " +
           "p.date LIKE %:searchText% " +
           "ORDER BY p.purchaseId")
    List<PurchaseInfo> searchPurchases(@Param("searchText") String searchText);
}
