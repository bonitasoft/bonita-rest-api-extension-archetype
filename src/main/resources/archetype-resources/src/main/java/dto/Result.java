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
public class Result {
#foreach($urlParameter in $params)
    private String $urlParameter;
#end
    private String myParameterKey;
}
