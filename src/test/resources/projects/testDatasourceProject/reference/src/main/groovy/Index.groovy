import groovy.json.JsonBuilder
import org.bonitasoft.console.common.server.page.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Logger

class Index extends AbstractIndex implements RestApiController {

    RestApiResponse doHandle(HttpServletRequest request, PageResourceProvider pageResourceProvider, PageContext pageContext, RestApiResponseBuilder apiResponseBuilder, RestApiUtil restApiUtil) {
      //Retrieve request parameters
      //String paramValue = request.getParameter "paramName"
      //if (paramValue == null) {
      //      return buildErrorResponse(apiResponseBuilder, "the parameter paramName is missing",restApiUtil.logger)
      //  }
        
      //Retrieve configuration parameters from property file
      //Properties props = loadProperties "configuration.properties", pageResourceProvider  
      //String propertyValue = props["propertyName"]
        
      //execute business logic here

       //Return the result
       //return buildResponse(apiResponseBuilder, result)
    }


}

