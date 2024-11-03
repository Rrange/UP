package com.example.springmodels.controllers;

import com.example.springmodels.models.Feedback;
import com.example.springmodels.repos.FeedbackRepository;
import com.example.springmodels.repos.CustomerRepository;
import com.example.springmodels.repos.SportEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SportEquipmentRepository sportEquipmentRepository;

    @GetMapping
    public String getAllFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedbackRepository.findAll());
        return "feedback_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public String showAddForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("equipmentList", sportEquipmentRepository.findAll());
        return "feedback_form";
    }

    @PostMapping
    public String addFeedback(@ModelAttribute Feedback feedback) {
        feedbackRepository.save(feedback);
        return "redirect:/feedbacks";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        feedbackRepository.findById(id).ifPresent(fb -> model.addAttribute("feedback", fb));
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("equipmentList", sportEquipmentRepository.findAll());
        return "feedback/form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String updateFeedback(@PathVariable Long id, @ModelAttribute Feedback feedback) {
        feedback.setId(id);
        feedbackRepository.save(feedback);
        return "redirect:/feedbacks";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackRepository.deleteById(id);
        return "redirect:/feedbacks";
    }
}
