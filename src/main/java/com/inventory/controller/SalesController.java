package com.inventory.controller;

import com.inventory.entity.Customer;
import com.inventory.entity.Product;
import com.inventory.entity.SalesInfo;
import com.inventory.service.CustomerService;
import com.inventory.service.ProductService;
import com.inventory.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/sales")
public class SalesController {
    
    @Autowired
    private SalesService salesService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String listSales(@RequestParam(value = "search", required = false) String search, Model model) {
        List<SalesInfo> sales;
        if (search != null && !search.trim().isEmpty()) {
            sales = salesService.searchSales(search);
            model.addAttribute("search", search);
        } else {
            sales = salesService.getAllSales();
        }
        model.addAttribute("sales", sales);
        return "sales/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("salesInfo", new SalesInfo());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("products", productService.getAllProducts());
        return "sales/form";
    }
    
    @PostMapping
    public String createSale(@Valid @ModelAttribute SalesInfo salesInfo, 
                           BindingResult result, 
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("products", productService.getAllProducts());
            return "sales/form";
        }
        
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            
            salesInfo.setSoldBy(username);
            salesInfo.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")));
            
            Double sellPrice = productService.getProductSellPrice(salesInfo.getProductCode());
            if (sellPrice != null) {
                salesInfo.setRevenue(sellPrice * salesInfo.getQuantity());
            }
            
            salesService.sellProduct(salesInfo);
            redirectAttributes.addFlashAttribute("success", "Product sold successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/sales";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteSale(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            salesService.deleteSale(id);
            redirectAttributes.addFlashAttribute("success", "Sale transaction deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/sales";
    }
}
