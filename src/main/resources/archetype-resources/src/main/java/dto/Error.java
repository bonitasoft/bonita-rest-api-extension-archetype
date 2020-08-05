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
