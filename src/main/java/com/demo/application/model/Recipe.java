package com.demo.application.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

@JsonProperty("name")
public String name;
@JsonProperty("ingredients")
public List<String> ingredients = null;
@JsonProperty("instructions")
public List<String> instructions = null;

}