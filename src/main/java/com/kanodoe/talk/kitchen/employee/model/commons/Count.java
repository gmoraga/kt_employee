package com.kanodoe.talk.kitchen.employee.model.commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vertx.core.json.JsonObject;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Count {

    private Integer employees;

    public Count(JsonObject json) {
        this.employees = json.getInteger("count");
    }
}
