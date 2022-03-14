package com.istl.elasticsearch.controller;

import com.istl.elasticsearch.model.Person;
import com.istl.elasticsearch.service.PersonService;
import com.istl.elasticsearch.service.PersonServiceRepoImpl;
import com.istl.elasticsearch.service.PersonServiceRestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController
{

    private final PersonService service;

    @Autowired
    public PersonController(PersonServiceRestImpl service) {
        this.service = service;
    }

    @PostMapping
    public void save(@RequestBody final Person person){
        service.save(person);
    }

    @GetMapping
    public List<Person> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable final String id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public boolean updatePerson(@PathVariable("id") final String id,@RequestBody final Person person){
        return service.update(id,person);
    }
    @DeleteMapping("/{id}")
    public boolean deletePerson(@PathVariable("id") final String id){
        return service.delete(id);
    }

}
