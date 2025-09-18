package com.inventory.controller;

import com.inventory.entity.CurrentStock;
import com.inventory.entity.Product;
import com.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String listProducts(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Product> products;
        if (search != null && !search.trim().isEmpty()) {
            products = productService.searchProducts(search);
            model.addAttribute("search", search);
        } else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
        return "products/list";
    }
    
    @GetMapping("/stock")
    public String listCurrentStock(Model model) {
        List<CurrentStock> stockList = productService.getAllCurrentStock();
        model.addAttribute("stockList", stockList);
        return "products/stock";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("quantity", 0);
        return "products/form";
    }
    
    @PostMapping
    public String createProduct(@Valid @ModelAttribute Product product, 
                              @RequestParam Integer quantity,
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "products/form";
        }
        
        try {
            productService.saveProduct(product, quantity);
            redirectAttributes.addFlashAttribute("success", "Product added successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/products";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(id)
                .orElse(null);
        
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Product not found");
            return "redirect:/products";
        }
        
        Integer currentQuantity = productService.getCurrentStock(product.getProdCode())
                .map(CurrentStock::getQuantity)
                .orElse(0);
        
        model.addAttribute("product", product);
        model.addAttribute("quantity", currentQuantity);
        return "products/form";
    }
    
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Integer id, 
                              @Valid @ModelAttribute Product product, 
                              @RequestParam Integer quantity,
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "products/form";
        }
        
        try {
            product.setProdID(id);
            productService.updateProduct(product, quantity);
            redirectAttributes.addFlashAttribute("success", "Product updated successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/products";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/products";
    }
}
