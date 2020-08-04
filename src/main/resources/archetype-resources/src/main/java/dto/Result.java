#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package ${package}.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "ResultBuilder")
@JsonDeserialize(builder = Result.ResultBuilder.class)
public class Result {
#foreach($urlParameter in $params)
    private final String $urlParameter;
#end
    private final String myParameterKey;
    @Builder.Default
    private final LocalDate currentDate = LocalDate.now();

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultBuilder {
    }
}

