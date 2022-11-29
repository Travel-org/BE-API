package com.ssu.travel.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {

    @JsonProperty("male")
    MALE,
    @JsonProperty("female")
    FEMALE,
}
