#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package ${groupId}.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Error {
    @Builder.Default
    private String name = "error";
    private String message;
}
