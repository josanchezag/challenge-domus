package com.domus.challenge.model;

import lombok.Data;

import java.util.List;

@Data
public class MoviePageResponse {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<Movie> data;
}
