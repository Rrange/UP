package com.example.springmodels.controllers;

import com.example.springmodels.models.InsurancePolicy;
import com.example.springmodels.repos.InsurancePolicyRepository;
import com.example.springmodels.repos.RentalOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/insurance-policies")
public class InsurancePolicyController {

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @Autowired
    private RentalOrderRepository rentalOrderRepository;

    @GetMapping
    public String getAllPolicies(Model model) {
        model.addAttribute("insurancePolicies", insurancePolicyRepository.findAll());
        return "insurance_policy_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("insurancePolicy", new InsurancePolicy());
        model.addAttribute("rentalOrders", rentalOrderRepository.findAll());
        return "insurance_policy_form";
    }

    @PostMapping
    public String addPolicy(@ModelAttribute InsurancePolicy insurancePolicy) {
        insurancePolicyRepository.save(insurancePolicy);
        return "redirect:/insurance-policies";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        insurancePolicyRepository.findById(id).ifPresent(policy -> model.addAttribute("insurancePolicy", policy));
        model.addAttribute("rentalOrders", rentalOrderRepository.findAll());
        return "insurance_policy_form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updatePolicy(@PathVariable Long id, @ModelAttribute InsurancePolicy insurancePolicy) {
        insurancePolicy.setId(id);
        insurancePolicyRepository.save(insurancePolicy);
        return "redirect:/insurance-policies";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deletePolicy(@PathVariable Long id) {
        insurancePolicyRepository.deleteById(id);
        return "redirect:/insurance-policies";
    }
}
