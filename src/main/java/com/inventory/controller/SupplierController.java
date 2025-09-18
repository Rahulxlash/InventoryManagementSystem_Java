package com.inventory.controller;

import com.inventory.entity.Supplier;
import com.inventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    
    @Autowired
    private SupplierService supplierService;
    
    @GetMapping
    public String listSuppliers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Supplier> suppliers;
        if (search != null && !search.trim().isEmpty()) {
            suppliers = supplierService.searchSuppliers(search);
            model.addAttribute("search", search);
        } else {
            suppliers = supplierService.getAllSuppliers();
        }
        model.addAttribute("suppliers", suppliers);
        return "suppliers/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/form";
    }
    
    @PostMapping
    public String createSupplier(@Valid @ModelAttribute Supplier supplier, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "suppliers/form";
        }
        
        try {
            supplierService.saveSupplier(supplier);
            redirectAttributes.addFlashAttribute("success", "Supplier added successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/suppliers";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Supplier supplier = supplierService.getSupplierById(id)
                .orElse(null);
        
        if (supplier == null) {
            redirectAttributes.addFlashAttribute("error", "Supplier not found");
            return "redirect:/suppliers";
        }
        
        model.addAttribute("supplier", supplier);
        return "suppliers/form";
    }
    
    @PostMapping("/{id}")
    public String updateSupplier(@PathVariable Integer id, 
                               @Valid @ModelAttribute Supplier supplier, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "suppliers/form";
        }
        
        try {
            supplier.setSuppID(id);
            supplierService.updateSupplier(supplier);
            redirectAttributes.addFlashAttribute("success", "Supplier updated successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/suppliers";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteSupplier(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            supplierService.deleteSupplier(id);
            redirectAttributes.addFlashAttribute("success", "Supplier deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/suppliers";
    }
}
