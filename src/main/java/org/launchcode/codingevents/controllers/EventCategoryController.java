package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("eventCategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("categories", eventCategoryRepository.findAll());
        model.addAttribute("title", "All Categories");
        return "eventCategories/index";
    }

    @GetMapping("create")
    public String renderCreateEventCategoryForm(Model model) {
        model.addAttribute("title", "Create Category");
        model.addAttribute(new EventCategory());
        return "eventCategories/create";
    }

    @PostMapping("create")
    public String processCreateEventCategoryForm(@ModelAttribute @Valid EventCategory eventCategory, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Category");
            model.addAttribute(new EventCategory());
            return "eventCategories/create";
        }
        eventCategoryRepository.save(eventCategory);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventCategoryForm(Model model) {
        model.addAttribute("title", "Delete Event Categories");
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "eventCategories/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required = false) int[] categoryIds) {
        if (categoryIds != null) {
            for (int id : categoryIds) {
                eventCategoryRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

}
