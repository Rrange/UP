package com.example.springmodels.controllers;

import com.example.springmodels.models.SubscriptionPlan;
import com.example.springmodels.repos.SubscriptionPlanRepository;
import com.example.springmodels.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subscription-plans")
public class SubscriptionPlanController {

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String getAllPlans(Model model) {
        model.addAttribute("subscriptionPlans", subscriptionPlanRepository.findAll());
        return "subscription_plan_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("subscriptionPlan", new SubscriptionPlan());
        model.addAttribute("customers", customerRepository.findAll());
        return "subscription_plan_form";
    }

    @PostMapping
    public String addPlan(@ModelAttribute SubscriptionPlan subscriptionPlan) {
        subscriptionPlanRepository.save(subscriptionPlan);
        return "redirect:/subscription-plans";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        subscriptionPlanRepository.findById(id).ifPresent(plan -> model.addAttribute("subscriptionPlan", plan));
        model.addAttribute("customers", customerRepository.findAll());
        return "subscription_plan_form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updatePlan(@PathVariable Long id, @ModelAttribute SubscriptionPlan subscriptionPlan) {
        subscriptionPlan.setId(id);
        subscriptionPlanRepository.save(subscriptionPlan);
        return "redirect:/subscription-plans";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deletePlan(@PathVariable Long id) {
        subscriptionPlanRepository.deleteById(id);
        return "redirect:/subscription-plans";
    }
}
