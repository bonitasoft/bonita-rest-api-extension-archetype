#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package ${package}.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

@JsonDeserialize(builder = Result.ResultBuilder.class)
public class Result {
#foreach($urlParameter in $params)
    private final String $urlParameter;
#end
    private final String myParameterKey;
    @JsonIgnore
    private final LocalDate currentDate = LocalDate.now();
    
    public Result(String myParameterKey#foreach($urlParameter in $params), String $urlParameter#end) {
        this.myParameterKey = myParameterKey;
#foreach($urlParameter in $params)
        this.$urlParameter = $urlParameter;
#end
    }

#foreach($urlParameter in $params)
#set( $getterFirstChar = $urlParameter.substring(0, 1).toUpperCase())
#set( $getterEnd = $urlParameter.substring(1))
    public String get$getterFirstChar$getterEnd() {
        return $urlParameter;
    }

#end
    public String getMyParameterKey() {
        return myParameterKey;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultBuilder {
#foreach($urlParameter in $params)
        private String $urlParameter;
#end
        private String myParameterKey;

#foreach($urlParameter in $params)
        public ResultBuilder $urlParameter(String $urlParameter) {
            this.$urlParameter =  $urlParameter;
            return this;
        }

#end
        public ResultBuilder myParameterKey(String myParameterKey) {
            this.myParameterKey =  myParameterKey;
            return this;
        }

        public Result build() {
            return new Result(myParameterKey#foreach($urlParameter in $params), $urlParameter#end);
        }
    }

    public static ResultBuilder builder() {
        return new ResultBuilder();
    }
}

