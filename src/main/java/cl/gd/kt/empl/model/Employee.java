package cl.gd.kt.empl.model;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.gd.kt.empl.util.CommonUtil;
import cl.gd.kt.empl.util.JsonUtil;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private String email;
    private String gender;
    private Integer phoneCountry;
    private Integer phoneRegion;
    private Integer phoneNumber;
    private String country;
    private String state;
    private String city;
    private String address;
    private Instant entryDate;
    private String category;
    private String level;
    private Boolean isActive;

    public Employee() {
        this.firstName = "";
        this.secondName = "";
        this.lastName = "";
        this.secondLastName = "";
        this.email = "";
        this.gender = "";
        this.phoneCountry = 0;
        this.phoneRegion = 0;
        this.phoneNumber = 0;
        this.country = "";
        this.state = "";
        this.city = "";
        this.address = "";
        this.entryDate = Instant.now();
        this.category = "";
        this.level = "";
        this.isActive = Boolean.TRUE;
    }

    public Employee (JsonObject json) {
        this.id = json.getLong("id");
        this.firstName = json.getString("first_name");
        this.secondName = json.getString("second_name");
        this.lastName = json.getString("last_name");
        this.secondLastName = json.getString("second_last_name");
        this.email = json.getString("email");
        this.gender = json.getString("gender");
        this.phoneCountry = json.getInteger("phone_country");
        this.phoneRegion = json.getInteger("phone_region");
        this.phoneNumber = json.getInteger("phone_number");
        this.country = json.getString("country");
        this.state = json.getString("state");
        this.city = json.getString("city");
        this.address = json.getString("address");
        this.entryDate = json.getInstant("entry_date");
        this.category = json.getString("category");
        this.level = json.getString("level");
        this.isActive = CommonUtil.stringToBoolean(json.getString("is_active"));
    }

    public JsonArray convert() {

        JsonArray params = new JsonArray();

        JsonUtil.addParam(params, this.firstName);
        JsonUtil.addParam(params, this.secondName);
        JsonUtil.addParam(params, this.lastName);
        JsonUtil.addParam(params, this.secondLastName);
        JsonUtil.addParam(params, this.email);
        JsonUtil.addParam(params, this.gender);
        JsonUtil.addParam(params, this.phoneCountry);
        JsonUtil.addParam(params, this.phoneRegion);
        JsonUtil.addParam(params, this.phoneNumber);
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

        JsonUtil.addParam(params, this.firstName);
        JsonUtil.addParam(params, this.secondName);
        JsonUtil.addParam(params, this.lastName);
        JsonUtil.addParam(params, this.secondLastName);
        JsonUtil.addParam(params, this.email);
        JsonUtil.addParam(params, this.gender);
        JsonUtil.addParam(params, this.phoneCountry);
        JsonUtil.addParam(params, this.phoneRegion);
        JsonUtil.addParam(params, this.phoneNumber);
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
