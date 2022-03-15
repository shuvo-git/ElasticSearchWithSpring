package com.istl.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.istl.elasticsearch.exception.error.ErrorResponse;
import com.istl.elasticsearch.helper.Indices;
import com.istl.elasticsearch.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;


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
            return null;z

        return MAPPER.readValue(response.getSourceAsString(), Vehicle.class);
    }




}
