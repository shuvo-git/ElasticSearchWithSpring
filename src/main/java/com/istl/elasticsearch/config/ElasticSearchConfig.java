package com.istl.elasticsearch.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.istl.elasticsearch.repository")
//@ComponentScan(basePackages = "com.istl.elasticsearch.repository")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    public String host;

    @Value("${spring.elasticsearch.username}")
    public String elasticUsername;

    @Value("${spring.elasticsearch.password}")
    public String elasticPassword;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration configuration
                = ClientConfiguration.builder()
                .connectedTo(host)
                .withBasicAuth(elasticUsername, elasticPassword)
                .build();
        return RestClients.create(configuration).rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

}
