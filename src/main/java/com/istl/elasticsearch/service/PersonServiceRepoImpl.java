package com.istl.elasticsearch.service;

import com.istl.elasticsearch.model.Person;
import com.istl.elasticsearch.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "personServiceRepoImpl")
public class PersonServiceRepoImpl implements PersonService
{
    private final PersonRepository repository;

    @Autowired
    public PersonServiceRepoImpl(PersonRepository repository) {
        this.repository = repository;
    }

    public void save(final Person person) {
        repository.save(person);

    }

    public List<Person> getAll(){
        return StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public Person findById(final String id) {
        return repository
                .findById(id)
                .orElse(null);
    }

    public boolean update(final String id,final Person person){
        Person person1 = repository.findById(id).orElse(null);

        if(person1 == null){
            return false;
        }

        person1.setName(person.getName());

        repository.save(person1);
        return true;
    }

    public boolean delete(final String id){
        Person person = repository.findById(id).orElse(null);

        if(person==null){
            return false;
        }

        repository.delete(person);
        return  true;
    }


}
