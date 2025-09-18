package com.inventory.controller;

import com.inventory.entity.Customer;
import com.inventory.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public String listCustomers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Customer> customers;
        if (search != null && !search.trim().isEmpty()) {
            customers = customerService.searchCustomers(search);
            model.addAttribute("search", search);
        } else {
            customers = customerService.getAllCustomers();
        }
        model.addAttribute("customers", customers);
        return "customers/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }
    
    @PostMapping
    public String createCustomer(@Valid @ModelAttribute Customer customer, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customers/form";
        }
        
        try {
            customerService.saveCustomer(customer);
            redirectAttributes.addFlashAttribute("success", "Customer added successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/customers";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = customerService.getCustomerById(id)
                .orElse(null);
        
        if (customer == null) {
            redirectAttributes.addFlashAttribute("error", "Customer not found");
            return "redirect:/customers";
        }
        
        model.addAttribute("customer", customer);
        return "customers/form";
    }
    
    @PostMapping("/{id}")
    public String updateCustomer(@PathVariable Integer id, 
                               @Valid @ModelAttribute Customer customer, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customers/form";
        }
        
        try {
            customer.setCustID(id);
            customerService.updateCustomer(customer);
            redirectAttributes.addFlashAttribute("success", "Customer updated successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/customers";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("success", "Customer deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/customers";
    }
}
