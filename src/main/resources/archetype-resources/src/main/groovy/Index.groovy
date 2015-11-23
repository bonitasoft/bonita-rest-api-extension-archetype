#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package $groupId;
    
import java.io.Serializable;

import org.apache.http.HttpHeaders;
import groovy.json.JsonBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bonitasoft.web.extension.ResourceProvider;
import org.bonitasoft.web.extension.rest.RestAPIContext;
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder;
import org.bonitasoft.web.extension.rest.RestApiResponse;
import org.bonitasoft.web.extension.rest.RestApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Index implements RestApiController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Index.class)
    
    RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
#foreach ($urlParameter in $params)
      //Retrieve $urlParameter parameter
      def $urlParameter = request.getParameter "$urlParameter"
      if ($urlParameter == null) {
        return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter $urlParameter is missing"}""")
      }
    
#end
      //You can retrieve configuration parameters from a properties file
      Properties props = loadProperties("configuration.properties", context.resourceProvider)
      String hostName = props["hostName"]
        
      //Execute business logic here
      def result = [ #foreach ($urlParameter in $params)"$urlParameter" : $urlParameter ,#end "hostName" : hostName ]
    
      //Return the result as a JSON representation
      return buildResponse(responseBuilder, HttpServletResponse.SC_OK, new JsonBuilder(result).toPrettyString())
    }
    
    /**
     * Build an HTTP response
     * @param  responseBuilder the Rest API response builder
     * @param  httpStatus the status of the response
     * @param  body the response body
     * @return a RestAPIResponse
     */
    RestApiResponse buildResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Serializable body) {
        return responseBuilder.with {
            withResponseStatus(httpStatus)
            withResponse(body)
            build()
        }
    }
    
    /**
     * Build a response with content-range data in the HTTP header for pagination
     * @param  responseBuilder the Rest API response builder
     * @param  body the response body
     * @param  page the page index
     * @param  perPage the number of result per page
     * @param  total the total number of results
     * @return a RestAPIResponse
     */
    RestApiResponse buildResponseWithPagination(RestApiResponseBuilder responseBuilder, Serializable body, int page, int perPage, int total) {
        return responseBuilder.with {
            withAdditionalHeader(HttpHeaders.CONTENT_RANGE,"$page-$perPage/$total");
            withResponse(body)
            build()
        }
    }
    
    /**
     * Load a property file into a java.util.Properties
     */
    Properties loadProperties(String fileName, ResourceProvider resourceProvider) {
        Properties props = new Properties()
        resourceProvider.getResourceAsStream(fileName).withStream {
            InputStream s -> props.load s
        }
        props
    }

}
