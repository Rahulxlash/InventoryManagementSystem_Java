package com.inventory.repository;

import com.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    boolean existsByFullNameAndLocationAndPhoneAndUserType(String fullName, String location, String phone, String userType);
    
    Optional<User> findByUsernameAndPassword(String username, String password);
    
    @Query("SELECT u FROM User u WHERE " +
           "u.username LIKE %:searchText% OR " +
           "u.fullName LIKE %:searchText% OR " +
           "u.location LIKE %:searchText% OR " +
           "u.userType LIKE %:searchText%")
    List<User> searchUsers(@Param("searchText") String searchText);
}
