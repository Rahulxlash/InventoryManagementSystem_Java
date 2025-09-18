package com.inventory.repository;

import com.inventory.entity.SalesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesInfoRepository extends JpaRepository<SalesInfo, Integer> {
    
    List<SalesInfo> findByProductCode(String productCode);
    
    List<SalesInfo> findByCustomerCode(String customerCode);
    
    List<SalesInfo> findBySoldBy(String soldBy);
    
    @Query("SELECT s FROM SalesInfo s JOIN FETCH s.product p JOIN FETCH s.customer c JOIN FETCH s.user u ORDER BY s.salesId")
    List<SalesInfo> findAllWithDetails();
    
    @Query("SELECT s FROM SalesInfo s JOIN s.product p JOIN s.customer c JOIN s.user u WHERE " +
           "s.productCode LIKE %:searchText% OR " +
           "p.prodName LIKE %:searchText% OR " +
           "u.fullName LIKE %:searchText% OR " +
           "c.fullName LIKE %:searchText% " +
           "ORDER BY s.salesId")
    List<SalesInfo> searchSales(@Param("searchText") String searchText);
}
