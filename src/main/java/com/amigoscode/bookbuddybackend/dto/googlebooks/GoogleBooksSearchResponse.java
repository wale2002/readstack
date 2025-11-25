package com.amigoscode.bookbuddybackend.dto.googlebooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBooksSearchResponse {

    @JsonProperty("items")
    private List<GoogleBookVolume> items;

    public List<GoogleBookVolume> getItems() {
        return items;
    }

    public void setItems(List<GoogleBookVolume> items) {
        this.items = items;
    }
}