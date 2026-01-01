package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public String personForm(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("persons", personService.getAllPersons());
        return "person-form";
    }

    @GetMapping("/persons")
    public String listPersons(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        return "person-list";
    }

    @PostMapping("/person")
    public String personSubmit(@ModelAttribute Person person, Model model) {
        try {
            personService.savePerson(person);
        } catch (Exception e) {
            // In a real app, handle this more gracefully, maybe add an error message to the
            // model
            e.printStackTrace();
        }
        model.addAttribute("person", person);
        model.addAttribute("persons", personService.getAllPersons());
        return "person-result";
    }
}
