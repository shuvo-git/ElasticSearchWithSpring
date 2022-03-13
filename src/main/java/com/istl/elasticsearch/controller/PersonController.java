package com.istl.elasticsearch.controller;

import com.istl.elasticsearch.model.Person;
import com.istl.elasticsearch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    public void save(@RequestBody final Person person){
        service.save(person);
    }

    @GetMapping("/{id}")
    public Person save(@RequestParam("id") final String id){
        return service.findById(id);
    }


}
