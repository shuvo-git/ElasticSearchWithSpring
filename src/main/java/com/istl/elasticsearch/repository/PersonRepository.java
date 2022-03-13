package com.istl.elasticsearch.repository;

import com.istl.elasticsearch.model.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ElasticsearchRepository <Person, String>
{

}
