package com.kanodoe.talk.kitchen.employee.model.commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    private int page;
    private int limit;
}
