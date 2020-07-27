package com.company.bonitasoft;

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
     * @param userId
     * @param startDate
     * @param paramValue
     * @return Result
     */
    @Override
    protected Result execute(String userId, String startDate,  String paramValue) {

        log.debug("Execute rest api call with params: {}, {},  {}",  userId,  startDate,  paramValue);

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