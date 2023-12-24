package com.onyshkiv.expandapistest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductJsonPayload {
    private String table;
    @JsonProperty("records")
    private List<Product> products;
}
