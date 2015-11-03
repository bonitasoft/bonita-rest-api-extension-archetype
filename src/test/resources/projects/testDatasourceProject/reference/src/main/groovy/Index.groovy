import groovy.json.JsonBuilder
import javax.servlet.http.HttpServletRequest

import org.bonitasoft.console.common.server.page.*

class Index extends AbstractIndex implements RestApiController {

    RestApiResponse doHandle(HttpServletRequest request, PageResourceProvider pageResourceProvider, PageContext pageContext, RestApiResponseBuilder apiResponseBuilder, RestApiUtil restApiUtil) {
      //Retrieve userId parameter
      def userId = getUserId(request)
      if (userId == null) {
        return buildErrorResponse(apiResponseBuilder, "the parameter userId is missing",restApiUtil.logger)
      }
    
      //Retrieve startDate parameter
      def startDate = getStartDate(request)
      if (startDate == null) {
        return buildErrorResponse(apiResponseBuilder, "the parameter startDate is missing",restApiUtil.logger)
      }
    
      //You may retrieve configuration parameters from a property file
      Properties props = loadProperties("configuration.properties", pageResourceProvider)
      String hostName = props["hostName"]
        
      //execute business logic here
      def result = [ "userId" : userId ,"startDate" : startDate , "hostName" : hostName ]
    
      //Return the result as a JSON representation
      return buildResponse(apiResponseBuilder,new JsonBuilder(result).toPrettyString())
    }

}