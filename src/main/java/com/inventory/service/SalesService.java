package com.inventory.service;

import com.inventory.entity.CurrentStock;
import com.inventory.entity.SalesInfo;
import com.inventory.repository.CurrentStockRepository;
import com.inventory.repository.SalesInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalesService {
    
    @Autowired
    private SalesInfoRepository salesInfoRepository;
    
    @Autowired
    private CurrentStockRepository currentStockRepository;
    
    public List<SalesInfo> getAllSales() {
        return salesInfoRepository.findAllWithDetails();
    }
    
    public Optional<SalesInfo> getSaleById(Integer id) {
        return salesInfoRepository.findById(id);
    }
    
    public List<SalesInfo> getSalesByProductCode(String productCode) {
        return salesInfoRepository.findByProductCode(productCode);
    }
    
    public List<SalesInfo> getSalesByCustomerCode(String customerCode) {
        return salesInfoRepository.findByCustomerCode(customerCode);
    }
    
    public List<SalesInfo> getSalesBySoldBy(String soldBy) {
        return salesInfoRepository.findBySoldBy(soldBy);
    }
    
    public SalesInfo sellProduct(SalesInfo salesInfo) {
        String productCode = salesInfo.getProductCode();
        Integer quantityToSell = salesInfo.getQuantity();
        
        CurrentStock currentStock = currentStockRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Product not found in stock"));
        
        if (quantityToSell > currentStock.getQuantity()) {
            throw new RuntimeException("Insufficient stock for this product");
        }
        
        if (quantityToSell <= 0) {
            throw new RuntimeException("Please enter a valid quantity");
        }
        
        currentStock.setQuantity(currentStock.getQuantity() - quantityToSell);
        currentStockRepository.save(currentStock);
        
        return salesInfoRepository.save(salesInfo);
    }
    
    public void deleteSale(Integer id) {
        if (!salesInfoRepository.existsById(id)) {
            throw new RuntimeException("Sale transaction not found");
        }
        salesInfoRepository.deleteById(id);
    }
    
    public List<SalesInfo> searchSales(String searchText) {
        return salesInfoRepository.searchSales(searchText);
    }
}
