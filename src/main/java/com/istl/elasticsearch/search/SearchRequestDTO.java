package com.istl.elasticsearch.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@Getter
@Setter
@ToString
public class SearchRequestDTO extends PageRequestDTO
{
    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder sortOrder;

}
