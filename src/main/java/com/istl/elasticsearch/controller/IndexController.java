package com.istl.elasticsearch.controller;

import com.istl.elasticsearch.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
public class IndexController {
    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @RequestMapping("/recreate")
    public void reCreateElasticSearchIndex(){
        indexService.reCreateIndices(true);
    }
}
