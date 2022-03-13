package com.istl.elasticsearch.service;

import com.istl.elasticsearch.helper.Indices;
import com.istl.elasticsearch.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class PersonServiceRestImpl implements PersonService{

    private  final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public PersonServiceRestImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void save(Person person) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(person.getId().toString())
                .withObject(person).build();

        String documentId = elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of(Indices.PERSON_INDEX));

        //return documentId;
    }


    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public Person findById(String id) {
        return null;
    }

    @Override
    public boolean update(String id, Person person) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
