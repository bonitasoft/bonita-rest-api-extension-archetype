#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package $groupId;

import groovy.json.JsonBuilder
import javax.servlet.http.HttpServletRequest

import org.bonitasoft.console.common.server.page.*

class Index extends AbstractIndex implements RestApiController {

    RestApiResponse doHandle(HttpServletRequest request, PageResourceProvider pageResourceProvider, PageContext pageContext, RestApiResponseBuilder apiResponseBuilder, RestApiUtil restApiUtil) {
#foreach ($urlParameter in $params)
#set( $getter = "get"+$urlParameter.substring(0,1).toUpperCase()+$urlParameter.substring(1) )
      //Retrieve $urlParameter parameter
      def $urlParameter = $getter(request)
      if ($urlParameter == null) {
        return buildErrorResponse(apiResponseBuilder, "the parameter $urlParameter is missing",restApiUtil.logger)
      }
    
#end
      //You may retrieve configuration parameters from a property file
      Properties props = loadProperties("configuration.properties", pageResourceProvider)
      String hostName = props["hostName"]
        
      //execute business logic here
      def result = [ #foreach ($urlParameter in $params)"$urlParameter" : $urlParameter ,#end "hostName" : hostName ]
    
      //Return the result as a JSON representation
      return buildResponse(apiResponseBuilder,new JsonBuilder(result).toPrettyString())
    }

}