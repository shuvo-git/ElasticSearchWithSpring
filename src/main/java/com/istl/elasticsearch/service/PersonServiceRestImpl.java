package com.istl.elasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.istl.elasticsearch.helper.Indices;
import com.istl.elasticsearch.model.Person;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Primary
@Service
public class PersonServiceRestImpl implements PersonService {

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public PersonServiceRestImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    @Override
    public void save(Person person) {
        XContentBuilder builder;
        try {
            System.out.println("Starting...................");

            // Generating JSON from Person Object
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(person);

            IndexRequest indexRequest = new IndexRequest(Indices.PERSON_INDEX);
            indexRequest.source(json, XContentType.JSON);

            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            System.out.println("Ending......................");
        } catch (IOException e) {
            System.out.println("Exception...................");
            e.printStackTrace();
        }
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
