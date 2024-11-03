package com.example.springmodels.controllers;

import com.example.springmodels.models.RepairLog;
import com.example.springmodels.models.SportEquipment;
import com.example.springmodels.repos.RepairLogRepository;
import com.example.springmodels.repos.SportEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/repair-logs")
public class RepairLogController {

    @Autowired
    private RepairLogRepository repairLogRepository;

    @Autowired
    private SportEquipmentRepository sportEquipmentRepository;

    @GetMapping
    public String getAllLogs(Model model) {
        model.addAttribute("repairLogs", repairLogRepository.findAll());
        return "repair_log_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showAddForm(Model model) {
        model.addAttribute("repairLog", new RepairLog());
        model.addAttribute("equipmentList", sportEquipmentRepository.findAll());
        return "repair_log_form";
    }

    @PostMapping
    public String addLog(@ModelAttribute RepairLog repairLog, @RequestParam Long sportEquipmentId) {
        SportEquipment equipment = sportEquipmentRepository.findById(sportEquipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        repairLog.setSportEquipment(equipment);
        repairLogRepository.save(repairLog);
        return "redirect:/repair-logs";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        repairLogRepository.findById(id).ifPresent(log -> model.addAttribute("repairLog", log));
        model.addAttribute("equipmentList", sportEquipmentRepository.findAll());
        return "repair_log_form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updateLog(@PathVariable Long id, @ModelAttribute RepairLog repairLog, @RequestParam Long sportEquipmentId) {
        SportEquipment equipment = sportEquipmentRepository.findById(sportEquipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        repairLog.setId(id);
        repairLog.setSportEquipment(equipment);
        repairLogRepository.save(repairLog);
        return "redirect:/repair-logs";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deleteLog(@PathVariable Long id) {
        repairLogRepository.deleteById(id);
        return "redirect:/repair-logs";
    }
}