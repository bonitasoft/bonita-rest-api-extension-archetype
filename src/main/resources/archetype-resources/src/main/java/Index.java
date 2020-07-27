#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package $groupId;

import com.company.bonitasoft.dto.Result;
import com.company.bonitasoft.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;

/**
 * Controller class
 */
@Slf4j
public class Index extends AbstractIndex {

    /**
     * Ensure request is valid
     *
     * @param request the HttpRequest
     */
    @Override
    public void validateInputParameters(HttpServletRequest request) {
        // To retrieve query parameters use the request.getParameter(..) method.
        // Be careful, parameter values are always returned as String values

#foreach ($urlParameter in $params)
        // Retrieve $urlParameter parameter
        String $urlParameter = request.getParameter(PARAM_${urlParameter.toUpperCase()});
        if ($urlParameter == null) {
            throw new ValidationException(format("the parameter %s is missing", PARAM_${urlParameter.toUpperCase()}));
        }
#end

    }

    /**
     * Execute business logic
     *
#foreach ($urlParameter in $params)
     * @param $urlParameter
#end
     * @param paramValue
     * @return Result
     */
    @Override
    protected Result execute(#foreach($urlParameter in $params)String $urlParameter, #end String paramValue) {

        log.debug("Execute rest api call with params: #foreach($urlParameter in $params){}, #end {}", #foreach($urlParameter in $params) $urlParameter, #end paramValue);

        /*
         * TODO: Execute business logic here, your code goes here
         */

        // Prepare the result
        return Result.builder()
#foreach ($urlParameter in $params)
                .$urlParameter($urlParameter)
#end
                .myParameterKey(paramValue)
                .build();
    }
}