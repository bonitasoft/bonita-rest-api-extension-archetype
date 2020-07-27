package com.company.bonitasoft.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Result {
    private String userId;
    private String startDate;
    private String myParameterKey;
}
