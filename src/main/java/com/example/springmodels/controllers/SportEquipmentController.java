package com.example.springmodels.controllers;

import com.example.springmodels.models.SportEquipment;
import com.example.springmodels.repos.SportEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.springmodels.repos.EquipmentCategoryRepository;


@Controller
@RequestMapping("/sport-equipment")
public class SportEquipmentController {

    @Autowired
    private SportEquipmentRepository sportEquipmentRepository;
    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;


    @GetMapping
    public String getAllEquipment(Model model) {
        model.addAttribute("equipmentList", sportEquipmentRepository.findAll());
        return "sport_equipment_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("sportEquipment", new SportEquipment());
        model.addAttribute("categories", equipmentCategoryRepository.findAll());
        return "sport_equipment_form";
    }

    @PostMapping
    public String addEquipment(@ModelAttribute SportEquipment sportEquipment) {
        sportEquipmentRepository.save(sportEquipment);
        return "redirect:/sport-equipment";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        sportEquipmentRepository.findById(id).ifPresent(equipment -> model.addAttribute("sportEquipment", equipment));
        model.addAttribute("categories", equipmentCategoryRepository.findAll());
        return "sport_equipment_form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updateEquipment(@PathVariable Long id, @ModelAttribute SportEquipment sportEquipment) {
        sportEquipment.setId(id);
        sportEquipmentRepository.save(sportEquipment);
        return "redirect:/sport-equipment";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deleteEquipment(@PathVariable Long id) {
        sportEquipmentRepository.deleteById(id);
        return "redirect:/sport-equipment";
    }
}
