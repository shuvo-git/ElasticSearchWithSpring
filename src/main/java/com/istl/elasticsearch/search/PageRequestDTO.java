package com.istl.elasticsearch.search;


import lombok.Getter;
import lombok.Setter;

public class PageRequestDTO {

    private static int DEFAULT_SIZE = 100;

    @Getter
    @Setter
    private int page;

    @Setter
    private int size;

    public int getSize() {
        return size != 0 ? size : DEFAULT_SIZE;
    }
}
