package com.company.bonitasoft.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

@JsonDeserialize(builder = Result.ResultBuilder.class)
public class Result {
    private final String myParameterKey;
    @JsonIgnore
    private final LocalDate currentDate = LocalDate.now();
    
    public Result(String myParameterKey) {
        this.myParameterKey = myParameterKey;
    }

    public String getMyParameterKey() {
        return myParameterKey;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultBuilder {
        private String myParameterKey;

        public ResultBuilder myParameterKey(String myParameterKey) {
            this.myParameterKey =  myParameterKey;
            return this;
        }

        public Result build() {
            return new Result(myParameterKey);
        }
    }

    public static ResultBuilder builder() {
        return new ResultBuilder();
    }
}

