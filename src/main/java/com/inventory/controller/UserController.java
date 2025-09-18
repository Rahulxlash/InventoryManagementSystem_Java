package com.inventory.controller;

import com.inventory.entity.User;
import com.inventory.entity.UserLog;
import com.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listUsers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<User> users;
        if (search != null && !search.trim().isEmpty()) {
            users = userService.searchUsers(search);
            model.addAttribute("search", search);
        } else {
            users = userService.getAllUsers();
        }
        model.addAttribute("users", users);
        return "users/list";
    }
    
    @GetMapping("/logs")
    public String listUserLogs(Model model) {
        List<UserLog> userLogs = userService.getAllUserLogs();
        model.addAttribute("userLogs", userLogs);
        return "users/logs";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }
    
    @PostMapping
    public String createUser(@Valid @ModelAttribute User user, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }
        
        try {
            userService.saveUser(user);
            String message = "ADMINISTRATOR".equals(user.getUserType()) ? 
                           "New administrator added" : "New employee added";
            redirectAttributes.addFlashAttribute("success", message);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/users";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(id)
                .orElse(null);
        
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/users";
        }
        
        model.addAttribute("user", user);
        return "users/form";
    }
    
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Integer id, 
                           @Valid @ModelAttribute User user, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }
        
        try {
            user.setId(id);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/users";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/users";
    }
}
