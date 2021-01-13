package com.epam.izh.rd.online.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {
    public short getStat() {
        return stat;
    }

    @JsonProperty("base_stat")
    private short stat;
}
