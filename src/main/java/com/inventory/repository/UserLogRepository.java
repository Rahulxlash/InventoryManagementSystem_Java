package com.inventory.repository;

import com.inventory.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    
    List<UserLog> findByUsername(String username);
    
    @Query("SELECT ul FROM UserLog ul JOIN FETCH ul.user u ORDER BY ul.inTime DESC")
    List<UserLog> findAllWithUserDetails();
}
