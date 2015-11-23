#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package $groupId;
    
import java.io.Serializable;

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
    
    protected buildResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Serializable body) {
        return responseBuilder.with {
            withResponseStatus(httpStatus)
            withResponse(body)
            build()
        }
    }
    
    protected Properties loadProperties(String fileName, ResourceProvider resourceProvider) {
        Properties props = new Properties()
        resourceProvider.getResourceAsStream(fileName).withStream {
            InputStream s -> props.load s
        }
        props
    }

}
