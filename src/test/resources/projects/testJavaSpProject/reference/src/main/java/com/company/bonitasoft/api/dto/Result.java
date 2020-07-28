package com.company.bonitasoft.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "ResultBuilder")
@JsonDeserialize(builder = Result.ResultBuilder.class)
public class Result {
    private final String userId;
    private final String startDate;
    private final String myParameterKey;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultBuilder {
    }
}

