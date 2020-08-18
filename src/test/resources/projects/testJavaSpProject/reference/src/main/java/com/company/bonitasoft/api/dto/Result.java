package com.company.bonitasoft.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

@JsonDeserialize(builder = Result.ResultBuilder.class)
public class Result {
    private final String userId;
    private final String startDate;
    private final String myParameterKey;
    @JsonIgnore
    private final LocalDate currentDate = LocalDate.now();
    
    public Result(String myParameterKey, String userId, String startDate) {
        this.myParameterKey = myParameterKey;
        this.userId = userId;
        this.startDate = startDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getMyParameterKey() {
        return myParameterKey;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultBuilder {
        private String userId;
        private String startDate;
        private String myParameterKey;

        public ResultBuilder userId(String userId) {
            this.userId =  userId;
            return this;
        }

        public ResultBuilder startDate(String startDate) {
            this.startDate =  startDate;
            return this;
        }

        public ResultBuilder myParameterKey(String myParameterKey) {
            this.myParameterKey =  myParameterKey;
            return this;
        }

        public Result build() {
            return new Result(myParameterKey, userId, startDate);
        }
    }

    public static ResultBuilder builder() {
        return new ResultBuilder();
    }
}

