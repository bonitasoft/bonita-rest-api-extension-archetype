#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package $groupId;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
    
import org.bonitasoft.console.common.server.page.PageResourceProvider;
import org.bonitasoft.console.common.server.page.RestApiController;
import org.bonitasoft.console.common.server.page.RestApiResponse;
import org.bonitasoft.console.common.server.page.RestApiResponseBuilder;

abstract class AbstractIndex implements RestApiController {

    protected RestApiResponse buildErrorResponse(RestApiResponseBuilder apiResponseBuilder, String message, Logger logger ) {
        logger.severe message

        apiResponseBuilder.withResponseStatus(HttpServletResponse.SC_BAD_REQUEST)
        buildResponse apiResponseBuilder, """{"error" : "${symbol_dollar}message"}"""
    }

    protected RestApiResponse buildResponse(RestApiResponseBuilder apiResponseBuilder, Serializable result) {
        apiResponseBuilder.with {
            withResponse(result)
            build()
        }
    }

    protected Properties loadProperties(String fileName, PageResourceProvider pageResourceProvider) {
        Properties props = new Properties()
        pageResourceProvider.getResourceAsStream(fileName).withStream {
            InputStream s -> props.load s
        }
        props
    }
    
#foreach ($urlParameter in $params)
    protected String get$urlParameter.substring(0,1).toUpperCase()$urlParameter.substring(1)(HttpServletRequest request){
        request.getParameter "$urlParameter"
    }

#end
}