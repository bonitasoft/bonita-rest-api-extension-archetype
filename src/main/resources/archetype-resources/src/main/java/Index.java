#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package ${package};

import ${package}.dto.Result;
import ${package}.exception.ValidationException;

#if( ${sp} == 'false' )
import org.bonitasoft.web.extension.rest.RestAPIContext;
#else
import com.bonitasoft.web.extension.rest.RestAPIContext;
#end
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

/**
 * Controller class
 */
public class Index extends AbstractIndex {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Index.class.getName());

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
     * @param context
#foreach ($urlParameter in $params)
     * @param $urlParameter
#end
     * @return Result
     */
    @Override
    protected Result execute(RestAPIContext context#foreach ($urlParameter in $params), String $urlParameter#end) {

        // Here is an example of how you can retrieve configuration parameters from a properties file
        // It is safe to remove this if no configuration is required
        Properties props = loadProperties("configuration.properties", context.getResourceProvider());
        String paramValue = props.getProperty(MY_PARAMETER_KEY);

        LOGGER.debug(String.format("Execute rest api call with params:  #foreach($urlParameter in $params)%s, #end%s", #foreach($urlParameter in $params) $urlParameter, #end paramValue));

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