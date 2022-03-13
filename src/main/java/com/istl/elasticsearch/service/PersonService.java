package com.istl.elasticsearch.service;

import com.istl.elasticsearch.model.Person;

import java.util.List;

public interface PersonService {
    void save(final Person person);
    List<Person> getAll();
    Person findById(final String id);
    public boolean update(final String id,final Person person);
    public boolean delete(final String id);
}
