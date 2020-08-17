package com.company.bonitasoft.api;

import com.company.bonitasoft.api.dto.Result;
import com.company.bonitasoft.api.exception.ValidationException;

import com.bonitasoft.web.extension.rest.RestAPIContext;
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

        // Retrieve userId parameter
        String userId = request.getParameter(PARAM_USERID);
        if (userId == null) {
            throw new ValidationException(format("the parameter %s is missing", PARAM_USERID));
        }
        // Retrieve startDate parameter
        String startDate = request.getParameter(PARAM_STARTDATE);
        if (startDate == null) {
            throw new ValidationException(format("the parameter %s is missing", PARAM_STARTDATE));
        }

    }

    /**
     * Execute business logic
     *
     * @param context
     * @param userId
     * @param startDate
     * @return Result
     */
    @Override
    protected Result execute(RestAPIContext context, String userId, String startDate) {

        // Here is an example of how you can retrieve configuration parameters from a properties file
        // It is safe to remove this if no configuration is required
        Properties props = loadProperties("configuration.properties", context.getResourceProvider());
        String paramValue = props.getProperty(MY_PARAMETER_KEY);

        LOGGER.debug(String.format("Execute rest api call with params:  %s, %s, %s",  userId,  startDate,  paramValue));

        /*
         * TODO: Execute business logic here, your code goes here
         */

        // Prepare the result
        return Result.builder()
                .userId(userId)
                .startDate(startDate)
                .myParameterKey(paramValue)
                .build();
    }
}