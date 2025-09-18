package com.inventory.repository;

import com.inventory.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Optional<Customer> findByCustCode(String custCode);
    
    boolean existsByCustCode(String custCode);
    
    boolean existsByFullNameAndLocationAndPhone(String fullName, String location, String phone);
    
    @Query("SELECT c FROM Customer c WHERE " +
           "c.custCode LIKE %:searchText% OR " +
           "c.fullName LIKE %:searchText% OR " +
           "c.location LIKE %:searchText% OR " +
           "c.phone LIKE %:searchText%")
    List<Customer> searchCustomers(@Param("searchText") String searchText);
}
