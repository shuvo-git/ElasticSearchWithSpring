package com.istl.elasticsearch.service;

import com.istl.elasticsearch.helper.Indices;
import com.istl.elasticsearch.helper.Util;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;


@Service
@Slf4j
public class IndexService {
    private final List<String> INDICES_TO_CREATE = org.elasticsearch.core.List.of(Indices.VEHICLE_INDEX);
    private final RestHighLevelClient client;

    @Autowired
    public IndexService(RestHighLevelClient client) {
        this.client = client;
    }

    @PostConstruct
    public void createIndices() {
        reCreateIndices(true);
    }

    public void reCreateIndices(final boolean deleteExisting) {
        final String settings = Util.loadAsString("static/es-settings.json");
        for (final String indexName : INDICES_TO_CREATE) {
            try {

                // Check if index is already exists
                boolean indexIfExists = client
                        .indices()
                        .exists(
                                new GetIndexRequest(indexName),
                                RequestOptions.DEFAULT
                        );
                if (indexIfExists) {
                    if (deleteExisting == false)
                        continue;

                    client.indices().delete(new DeleteIndexRequest(indexName),RequestOptions.DEFAULT);
                }

                // loading mapping for the index
                final String mapping = Util.loadAsString("static/mappings/" + indexName + ".json");
                if (settings == null || mapping == null) {
                    log.error("Failed to create index with name {}", indexName);
                    continue;
                }

                // Create Index
                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.settings(settings, XContentType.JSON);
                createIndexRequest.mapping(mapping, XContentType.JSON);

                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);

            } catch (final Exception e) {
                e.printStackTrace();
            }


        }
    }
}
