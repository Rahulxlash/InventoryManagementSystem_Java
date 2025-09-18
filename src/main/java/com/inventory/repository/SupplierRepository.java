package com.inventory.repository;

import com.inventory.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    
    Optional<Supplier> findBySuppCode(String suppCode);
    
    boolean existsBySuppCode(String suppCode);
    
    boolean existsByFullNameAndLocationAndMobile(String fullName, String location, String mobile);
    
    @Query("SELECT s FROM Supplier s WHERE " +
           "s.suppCode LIKE %:searchText% OR " +
           "s.fullName LIKE %:searchText% OR " +
           "s.location LIKE %:searchText% OR " +
           "s.mobile LIKE %:searchText%")
    List<Supplier> searchSuppliers(@Param("searchText") String searchText);
}
