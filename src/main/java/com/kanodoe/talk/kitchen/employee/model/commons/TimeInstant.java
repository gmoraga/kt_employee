package com.kanodoe.talk.kitchen.employee.model.commons;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeInstant {

    transient Instant time;

    public TimeInstant(Instant time){
        this.time=time;
    }


    @JsonIgnore
    public Instant getTime() {
        return this.time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    @JsonGetter
    private Long getEpochTime() {
        return time != null ? this.time.toEpochMilli() : null;
    }

    @JsonSetter
    private void setEpochTime(Long time) {
        this.time = time != null ? Instant.ofEpochMilli(time) : null;
    }

    @JsonGetter
    private String getLabel() {
        return time != null ? time.toString() : null;
    }
}
