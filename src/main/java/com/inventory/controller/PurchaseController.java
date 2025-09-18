package com.inventory.controller;

import com.inventory.entity.Product;
import com.inventory.entity.PurchaseInfo;
import com.inventory.entity.Supplier;
import com.inventory.service.ProductService;
import com.inventory.service.PurchaseService;
import com.inventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {
    
    @Autowired
    private PurchaseService purchaseService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String listPurchases(@RequestParam(value = "search", required = false) String search, Model model) {
        List<PurchaseInfo> purchases;
        if (search != null && !search.trim().isEmpty()) {
            purchases = purchaseService.searchPurchases(search);
            model.addAttribute("search", search);
        } else {
            purchases = purchaseService.getAllPurchases();
        }
        model.addAttribute("purchases", purchases);
        return "purchases/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("purchaseInfo", new PurchaseInfo());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("products", productService.getAllProducts());
        return "purchases/form";
    }
    
    @PostMapping
    public String createPurchase(@Valid @ModelAttribute PurchaseInfo purchaseInfo, 
                               BindingResult result, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("suppliers", supplierService.getAllSuppliers());
            model.addAttribute("products", productService.getAllProducts());
            return "purchases/form";
        }
        
        try {
            purchaseInfo.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")));
            
            Double costPrice = productService.getProductCostPrice(purchaseInfo.getProductCode());
            if (costPrice != null) {
                purchaseInfo.setTotalCost(costPrice * purchaseInfo.getQuantity());
            }
            
            purchaseService.addPurchase(purchaseInfo);
            redirectAttributes.addFlashAttribute("success", "Purchase added successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/purchases";
    }
    
    @PostMapping("/{id}/delete")
    public String deletePurchase(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            purchaseService.deletePurchase(id);
            redirectAttributes.addFlashAttribute("success", "Purchase transaction deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/purchases";
    }
}
