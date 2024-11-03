package com.example.springmodels.controllers;

import com.example.springmodels.models.RentalOrder;
import com.example.springmodels.repos.RentalOrderRepository;
import com.example.springmodels.repos.SportEquipmentRepository;
import com.example.springmodels.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rental-orders")
public class RentalOrderController {

    @Autowired
    private RentalOrderRepository rentalOrderRepository;

    @Autowired
    private SportEquipmentRepository sportEquipmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("rentalOrders", rentalOrderRepository.findAll());
        return "rental_order_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("rentalOrder", new RentalOrder());
        model.addAttribute("equipmentList", sportEquipmentRepository.findAll());
        model.addAttribute("customerList", customerRepository.findAll());
        return "rental_order_form";
    }

    @PostMapping
    public String addOrder(@ModelAttribute RentalOrder rentalOrder) {
        rentalOrderRepository.save(rentalOrder);
        return "redirect:/rental-orders";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        rentalOrderRepository.findById(id).ifPresent(order -> model.addAttribute("rentalOrder", order));
        model.addAttribute("equipmentList", sportEquipmentRepository.findAll());
        model.addAttribute("customerList", customerRepository.findAll());
        return "rental_order_form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updateOrder(@PathVariable Long id, @ModelAttribute RentalOrder rentalOrder) {
        rentalOrder.setId(id);
        rentalOrderRepository.save(rentalOrder);
        return "redirect:/rental-orders";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deleteOrder(@PathVariable Long id) {
        rentalOrderRepository.deleteById(id);
        return "redirect:/rental-orders";
    }
}