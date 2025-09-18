package com.inventory.service;

import com.inventory.entity.Supplier;
import com.inventory.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }
    
    public Optional<Supplier> getSupplierByCode(String suppCode) {
        return supplierRepository.findBySuppCode(suppCode);
    }
    
    public Supplier saveSupplier(Supplier supplier) {
        if (supplierRepository.existsByFullNameAndLocationAndMobile(
                supplier.getFullName(), supplier.getLocation(), supplier.getMobile())) {
            throw new RuntimeException("Supplier already exists with the same details");
        }
        return supplierRepository.save(supplier);
    }
    
    public Supplier updateSupplier(Supplier supplier) {
        if (!supplierRepository.existsById(supplier.getSuppID())) {
            throw new RuntimeException("Supplier not found");
        }
        return supplierRepository.save(supplier);
    }
    
    public void deleteSupplier(Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }
        supplierRepository.deleteById(id);
    }
    
    public void deleteSupplierByCode(String suppCode) {
        Supplier supplier = supplierRepository.findBySuppCode(suppCode)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        supplierRepository.delete(supplier);
    }
    
    public List<Supplier> searchSuppliers(String searchText) {
        return supplierRepository.searchSuppliers(searchText);
    }
    
    public boolean existsByCode(String suppCode) {
        return supplierRepository.existsBySuppCode(suppCode);
    }
}
