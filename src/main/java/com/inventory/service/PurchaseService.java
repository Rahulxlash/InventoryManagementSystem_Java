package com.inventory.service;

import com.inventory.entity.CurrentStock;
import com.inventory.entity.PurchaseInfo;
import com.inventory.repository.CurrentStockRepository;
import com.inventory.repository.PurchaseInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseService {
    
    @Autowired
    private PurchaseInfoRepository purchaseInfoRepository;
    
    @Autowired
    private CurrentStockRepository currentStockRepository;
    
    public List<PurchaseInfo> getAllPurchases() {
        return purchaseInfoRepository.findAllWithDetails();
    }
    
    public Optional<PurchaseInfo> getPurchaseById(Integer id) {
        return purchaseInfoRepository.findById(id);
    }
    
    public List<PurchaseInfo> getPurchasesByProductCode(String productCode) {
        return purchaseInfoRepository.findByProductCode(productCode);
    }
    
    public List<PurchaseInfo> getPurchasesBySupplierCode(String supplierCode) {
        return purchaseInfoRepository.findBySupplierCode(supplierCode);
    }
    
    public PurchaseInfo addPurchase(PurchaseInfo purchaseInfo) {
        PurchaseInfo savedPurchase = purchaseInfoRepository.save(purchaseInfo);
        
        String productCode = purchaseInfo.getProductCode();
        Integer quantity = purchaseInfo.getQuantity();
        
        Optional<CurrentStock> existingStock = currentStockRepository.findByProductCode(productCode);
        
        if (existingStock.isPresent()) {
            CurrentStock stock = existingStock.get();
            stock.setQuantity(stock.getQuantity() + quantity);
            currentStockRepository.save(stock);
        } else {
            CurrentStock newStock = new CurrentStock(productCode, quantity);
            currentStockRepository.save(newStock);
        }
        
        return savedPurchase;
    }
    
    public void deletePurchase(Integer id) {
        if (!purchaseInfoRepository.existsById(id)) {
            throw new RuntimeException("Purchase transaction not found");
        }
        purchaseInfoRepository.deleteById(id);
    }
    
    public List<PurchaseInfo> searchPurchases(String searchText) {
        return purchaseInfoRepository.searchPurchases(searchText);
    }
}
