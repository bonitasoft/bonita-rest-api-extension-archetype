package com.company.bonitasoft.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Error {
    @Builder.Default
    private String name = "error";
    private String message;
}
