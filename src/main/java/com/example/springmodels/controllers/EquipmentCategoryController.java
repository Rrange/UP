package com.example.springmodels.controllers;

import com.example.springmodels.models.EquipmentCategory;
import com.example.springmodels.repos.EquipmentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipment-categories")
public class EquipmentCategoryController {

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", equipmentCategoryRepository.findAll());
        return "equipment_category_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("equipmentCategory", new EquipmentCategory());
        return "equipment_category_form";
    }

    @PostMapping
    public String addCategory(@ModelAttribute EquipmentCategory equipmentCategory) {
        equipmentCategoryRepository.save(equipmentCategory);
        return "redirect:/equipment-categories";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        equipmentCategoryRepository.findById(id).ifPresent(category -> model.addAttribute("equipmentCategory", category));
        return "equipment_category_form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updateCategory(@PathVariable Long id, @ModelAttribute EquipmentCategory equipmentCategory) {
        equipmentCategory.setId(id);
        equipmentCategoryRepository.save(equipmentCategory);
        return "redirect:/equipment-categories";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deleteCategory(@PathVariable Long id) {
        equipmentCategoryRepository.deleteById(id);
        return "redirect:/equipment-categories";
    }
}