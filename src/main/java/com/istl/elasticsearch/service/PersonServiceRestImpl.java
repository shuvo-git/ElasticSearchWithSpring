package com.istl.elasticsearch.service;

import com.istl.elasticsearch.helper.Indices;
import com.istl.elasticsearch.model.Person;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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
public class PersonServiceRestImpl implements PersonService{

    private  final RestHighLevelClient restHighLevelClient;

    @Autowired
    public PersonServiceRestImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    @Override
    public void save(Person person) {
//        IndexQuery indexQuery = new IndexQueryBuilder()
//                .withId(person.getId().toString())
//                .withObject(person).build();


        XContentBuilder builder;
        try {
            System.out.println("Starting...................");
            builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id",person.getId())
                    .field("name",person.getName())
                    .field("title",person.getTitle())
                    .field("age",person.getAge())
                    .field("occupation",person.getOccupation())
                    .endObject();
            IndexRequest indexRequest = new IndexRequest(Indices.PERSON_INDEX);
            indexRequest.source(builder);
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            //.index(indexQuery, IndexCoordinates.of(Indices.PERSON_INDEX));
            //return documentId;
            System.out.println("Ending...................");
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
