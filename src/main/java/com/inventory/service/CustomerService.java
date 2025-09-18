package com.inventory.service;

import com.inventory.entity.Customer;
import com.inventory.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }
    
    public Optional<Customer> getCustomerByCode(String custCode) {
        return customerRepository.findByCustCode(custCode);
    }
    
    public Customer saveCustomer(Customer customer) {
        if (customerRepository.existsByFullNameAndLocationAndPhone(
                customer.getFullName(), customer.getLocation(), customer.getPhone())) {
            throw new RuntimeException("Customer already exists with the same details");
        }
        return customerRepository.save(customer);
    }
    
    public Customer updateCustomer(Customer customer) {
        if (!customerRepository.existsById(customer.getCustID())) {
            throw new RuntimeException("Customer not found");
        }
        return customerRepository.save(customer);
    }
    
    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
    
    public void deleteCustomerByCode(String custCode) {
        Customer customer = customerRepository.findByCustCode(custCode)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }
    
    public List<Customer> searchCustomers(String searchText) {
        return customerRepository.searchCustomers(searchText);
    }
    
    public boolean existsByCode(String custCode) {
        return customerRepository.existsByCustCode(custCode);
    }
}
