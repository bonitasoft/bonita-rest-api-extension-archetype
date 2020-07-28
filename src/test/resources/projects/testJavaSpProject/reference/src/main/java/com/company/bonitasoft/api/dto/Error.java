package com.company.bonitasoft.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "ErrorBuilder")
@JsonDeserialize(builder = Error.ErrorBuilder.class)
public class Error {
    @Builder.Default
    private final String name = "error";
    private final String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ErrorBuilder {
    }
}
