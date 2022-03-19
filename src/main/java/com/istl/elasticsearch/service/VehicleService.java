package com.istl.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.istl.elasticsearch.exception.error.ErrorResponse;
import com.istl.elasticsearch.helper.Indices;
import com.istl.elasticsearch.model.Vehicle;
import com.istl.elasticsearch.search.SearchRequestDTO;
import com.istl.elasticsearch.search.utils.SearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class VehicleService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final RestHighLevelClient client;

    public VehicleService(RestHighLevelClient client) {
        this.client = client;
    }

    public boolean index(final Vehicle vehicle) {
        try {
            final String vehicleAsString = MAPPER.writeValueAsString(vehicle);

            IndexRequest indexRequest = new IndexRequest(Indices.VEHICLE_INDEX);
            indexRequest.id(vehicle.getId());
            indexRequest.source(vehicleAsString, XContentType.JSON);

            final IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);


        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public Vehicle getById(final String id) throws IOException, IllegalArgumentException {
        GetRequest getRequest = new GetRequest(Indices.VEHICLE_INDEX, id);
        final GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);

        if(response == null || response.isSourceEmpty())
            return null;

        return MAPPER.readValue(response.getSourceAsString(), Vehicle.class);
    }

    public List<Vehicle> search(final SearchRequestDTO dto) {

        SearchRequest request = SearchUtil.buildSearchRequest(Indices.VEHICLE_INDEX,dto);

        if(request == null){
            log.debug("Failed to create SearchRequest");
            return Collections.emptyList();
        }

        try {
            System.out.println(request);
            final SearchResponse response = client.search(request,RequestOptions.DEFAULT);
            System.out.println(response);

            final SearchHit[] hits = response.getHits().getHits();
            final List<Vehicle> vehicles = new ArrayList<>(hits.length);

            for (SearchHit hit: hits) {
                vehicles.add(MAPPER.readValue(hit.getSourceAsString(),Vehicle.class));
            }

            return vehicles;

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return Collections.emptyList();
        }

    }

    public List<Vehicle> getAll(){
        SearchRequest searchRequest = new SearchRequest(Indices.VEHICLE_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);

        try {
            final SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);

            final SearchHit[] hits = searchResponse.getHits().getHits();
            List<Vehicle> vehicles = new ArrayList<>(hits.length);

            for (SearchHit hit: hits) {
                vehicles.add(MAPPER.readValue(hit.getSourceAsString(),Vehicle.class));
            }

            return vehicles;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }




}

/*
QueryBuilder queryBuilder = QueryBuilders.matchQuery("number","Vehicle6")
                .operator(Operator.AND);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .postFilter(queryBuilder);

        SearchRequest searchRequest = new SearchRequest(Indices.VEHICLE_INDEX);
        searchRequest.source(searchSourceBuilder);

        try {
            System.out.println(searchRequest);
            final SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);

            System.out.println(searchResponse);

            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
 */
