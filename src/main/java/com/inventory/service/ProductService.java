package com.inventory.service;

import com.inventory.entity.CurrentStock;
import com.inventory.entity.Product;
import com.inventory.repository.CurrentStockRepository;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CurrentStockRepository currentStockRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }
    
    public Optional<Product> getProductByCode(String prodCode) {
        return productRepository.findByProdCode(prodCode);
    }
    
    public Product saveProduct(Product product, Integer initialQuantity) {
        if (productRepository.existsByProdNameAndCostPriceAndSellPriceAndBrand(
                product.getProdName(), product.getCostPrice(), product.getSellPrice(), product.getBrand())) {
            throw new RuntimeException("Product has already been added");
        }
        
        Product savedProduct = productRepository.save(product);
        
        CurrentStock stock = new CurrentStock(savedProduct.getProdCode(), initialQuantity);
        stock.setProduct(savedProduct);
        currentStockRepository.save(stock);
        
        return savedProduct;
    }
    
    public Product updateProduct(Product product, Integer quantity) {
        if (!productRepository.existsById(product.getProdID())) {
            throw new RuntimeException("Product not found");
        }
        
        Product savedProduct = productRepository.save(product);
        
        CurrentStock stock = currentStockRepository.findByProductCode(product.getProdCode())
                .orElse(new CurrentStock(product.getProdCode(), 0));
        stock.setQuantity(quantity);
        stock.setProduct(savedProduct);
        currentStockRepository.save(stock);
        
        return savedProduct;
    }
    
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        currentStockRepository.deleteById(product.getProdCode());
        productRepository.deleteById(id);
    }
    
    public void deleteProductByCode(String prodCode) {
        Product product = productRepository.findByProdCode(prodCode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        currentStockRepository.deleteById(prodCode);
        productRepository.delete(product);
    }
    
    public List<Product> searchProducts(String searchText) {
        return productRepository.searchProducts(searchText);
    }
    
    public boolean existsByCode(String prodCode) {
        return productRepository.existsByProdCode(prodCode);
    }
    
    public Double getProductCostPrice(String prodCode) {
        return productRepository.findByProdCode(prodCode)
                .map(Product::getCostPrice)
                .orElse(null);
    }
    
    public Double getProductSellPrice(String prodCode) {
        return productRepository.findByProdCode(prodCode)
                .map(Product::getSellPrice)
                .orElse(null);
    }
    
    public Optional<String> getProductName(String prodCode) {
        return productRepository.findProductNameByCode(prodCode);
    }
    
    public boolean checkStock(String prodCode) {
        return currentStockRepository.existsByProductCode(prodCode);
    }
    
    public List<CurrentStock> getAllCurrentStock() {
        return currentStockRepository.findAllWithProduct();
    }
    
    public Optional<CurrentStock> getCurrentStock(String prodCode) {
        return currentStockRepository.findByProductCode(prodCode);
    }
    
    public void increaseStock(String prodCode, Integer quantity) {
        currentStockRepository.increaseStock(prodCode, quantity);
    }
    
    public void decreaseStock(String prodCode, Integer quantity) {
        currentStockRepository.decreaseStock(prodCode, quantity);
    }
}
