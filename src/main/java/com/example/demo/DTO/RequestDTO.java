package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDTO {

    @NotBlank(message = "{validation.constraints.notblank}")
    @Size(min = 1, max = 50, message = "{validation.constraints.size}")
    @JsonProperty("Name")
    private String name;

    @NotBlank(message = "{validation.constraints.notblank}")
    @Size(min = 1, max = 50, message = "{validation.constraints.size}")
    @JsonProperty("Surname")
    private String surname;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
