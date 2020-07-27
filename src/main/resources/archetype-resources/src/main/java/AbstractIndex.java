#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package ${groupId};

#if( ${sp} == 'false' )
import org.bonitasoft.web.extension.rest.RestAPIContext;
import org.bonitasoft.web.extension.rest.RestApiController;
#else
import com.bonitasoft.web.extension.rest.RestAPIContext;
import com.bonitasoft.web.extension.rest.RestApiController;
#end
import ${groupId}.dto.Error;
import ${groupId}.dto.Result;
import ${groupId}.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.bonitasoft.web.extension.ResourceProvider;
import org.bonitasoft.web.extension.rest.RestApiResponse;
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * Parent Controller class to hide technical parts
 */
@Slf4j
public abstract class AbstractIndex implements RestApiController {


    public static final String MY_PARAMETER_KEY = "myParameterKey";
#foreach ($urlParameter in $params)
    public static final String PARAM_${urlParameter.toUpperCase()} = "$urlParameter";
#end

    private final ObjectMapper mapper = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .findAndRegisterModules();

    public ObjectMapper getMapper() {
        return mapper;
    }

    @Override
    public RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {

        // Validate request
        try {
            validateInputParameters(request);
        } catch (ValidationException e) {
            log.error("Request for this REST API extenstion is not valid", e);
            return jsonResponse(responseBuilder, SC_BAD_REQUEST, Error.builder().message(e.getMessage()).build());
        }
#foreach ($urlParameter in $params)
        String $urlParameter = request.getParameter(PARAM_${urlParameter.toUpperCase()});
#end

        // Here is an example of how you can retrieve configuration parameters from a properties file
        // It is safe to remove this if no configuration is required
        Properties props = loadProperties("configuration.properties", context.getResourceProvider());
        String paramValue = props.getProperty(MY_PARAMETER_KEY);

        // Execute business logic
        Result result = execute(#foreach ($urlParameter in $params)$urlParameter,#end paramValue);

        // Send the result as a JSON representation
        // You may use pagedJsonResponse if your result is multiple
        return jsonResponse(responseBuilder, SC_OK, result);
    }

    protected abstract Result execute(#foreach ($urlParameter in $params)String $urlParameter,#end String paramValue);

    protected abstract void validateInputParameters(HttpServletRequest request);

    /**
     * Build an HTTP response.
     *
     * @param responseBuilder the Rest API response builder
     * @param httpStatus      the status of the response
     * @param body            the response body
     * @return a RestAPIResponse
     */
    RestApiResponse jsonResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Object body) {
        try {
            return responseBuilder
                    .withResponseStatus(httpStatus)
                    .withResponse(mapper.writeValueAsString(body))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to write body response as JSON: " + body, e);
        }
    }


    /**
     * Returns a paged result like Bonita BPM REST APIs.
     * Build a response with a content-range.
     *
     * @param responseBuilder the Rest API response builder
     * @param body            the response body
     * @param p               the page index
     * @param c               the number of result per page
     * @param total           the total number of results
     * @return a RestAPIResponse
     */
    RestApiResponse pagedJsonResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Object body, int p, int c, long total) {
        try {
            return responseBuilder
                    .withContentRange(p, c, total)
                    .withResponseStatus(httpStatus)
                    .withResponse(mapper.writeValueAsString(body))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to write body response as JSON: " + body, e);
        }
    }

    /**
     * Load a property file into a java.util.Properties
     */
    private Properties loadProperties(String fileName, ResourceProvider resourceProvider) {
        try {
            Properties props = new Properties();
            props.load(resourceProvider.getResourceAsStream(fileName));
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties for REST API extenstion");
        }
    }
}
