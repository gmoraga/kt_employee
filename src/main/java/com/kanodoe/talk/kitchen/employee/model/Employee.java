package com.kanodoe.talk.kitchen.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kanodoe.talk.kitchen.employee.model.commons.TimeInstant;
import com.kanodoe.talk.kitchen.employee.util.CommonUtil;
import com.kanodoe.talk.kitchen.employee.util.JsonUtil;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String first_name;
    private String second_name;
    private String last_name;
    private String second_last_name;
    private String email;
    private String gender;
    private Integer phone_country;
    private Integer phone_region;
    private Integer phone_number;
    private String country;
    private String state;
    private String city;
    private String address;
    private TimeInstant entry_date;
    private String category;
    private String level;
    private Boolean is_active;

    public Employee() {
        this.first_name = "";
        this.second_name = "";
        this.last_name = "";
        this.second_last_name = "";
        this.email = "";
        this.gender = "";
        this.phone_country = 0;
        this.phone_region = 0;
        this.phone_number = 0;
        this.country = "";
        this.state = "";
        this.city = "";
        this.address = "";
        this.entry_date = new TimeInstant();
        this.category = "";
        this.level = "";
        this.is_active = Boolean.TRUE;
    }

    public Employee (JsonObject json) {
        this.id = json.getLong("id");
        this.first_name = json.getString("first_name");
        this.second_name = json.getString("second_name");
        this.last_name = json.getString("last_name");
        this.second_last_name = json.getString("second_last_name");
        this.email = json.getString("email");
        this.gender = json.getString("gender");
        this.phone_country = json.getInteger("phone_country");
        this.phone_region = json.getInteger("phone_region");
        this.phone_number = json.getInteger("phone_number");
        this.country = json.getString("country");
        this.state = json.getString("state");
        this.city = json.getString("city");
        this.address = json.getString("address");
        this.entry_date = new TimeInstant(json.getInstant("entry_date"));
        this.category = json.getString("category");
        this.level = json.getString("level");
        this.is_active = CommonUtil.stringToBoolean(json.getString("is_active"));
    }

    public JsonArray convert() {

        JsonArray params = new JsonArray();

        JsonUtil.addParam(params, this.first_name);
        JsonUtil.addParam(params, this.second_name);
        JsonUtil.addParam(params, this.last_name);
        JsonUtil.addParam(params, this.second_last_name);
        JsonUtil.addParam(params, this.email);
        JsonUtil.addParam(params, this.gender);
        JsonUtil.addParam(params, this.phone_country);
        JsonUtil.addParam(params, this.phone_region);
        JsonUtil.addParam(params, this.phone_number);
        JsonUtil.addParam(params, this.country);
        JsonUtil.addParam(params, this.state);
        JsonUtil.addParam(params, this.city);
        JsonUtil.addParam(params, this.address);
        JsonUtil.addParam(params, this.category);
        JsonUtil.addParam(params, this.level);
        JsonUtil.addParam(params, Instant.now());

        return params;
    }

    public JsonArray convertToPut() {

        JsonArray params = new JsonArray();

        JsonUtil.addParam(params, this.first_name);
        JsonUtil.addParam(params, this.second_name);
        JsonUtil.addParam(params, this.last_name);
        JsonUtil.addParam(params, this.second_last_name);
        JsonUtil.addParam(params, this.email);
        JsonUtil.addParam(params, this.gender);
        JsonUtil.addParam(params, this.phone_country);
        JsonUtil.addParam(params, this.phone_region);
        JsonUtil.addParam(params, this.phone_number);
        JsonUtil.addParam(params, this.country);
        JsonUtil.addParam(params, this.state);
        JsonUtil.addParam(params, this.city);
        JsonUtil.addParam(params, this.address);
        JsonUtil.addParam(params, this.category);
        JsonUtil.addParam(params, this.level);
        JsonUtil.addParam(params, this.id);

        return params;
    }
}
