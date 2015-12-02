#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
#set( $nbParams = 0 )
#foreach($p in $params)
#set( $nbParams = $nbParams+1)
#end
package $groupId;

import groovy.json.JsonSlurper

import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest

import com.bonitasoft.web.extension.rest.RestAPIContext;
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder

import spock.lang.Specification
    
class IndexTest extends Specification {

    def request = Mock(HttpServletRequest)
    def resourceProvider = Mock(ResourceProvider)
    def context = Mock(RestAPIContext)
    def responseBuilder = new RestApiResponseBuilder()

    def index = Spy(Index)

    def "should return a json representation as result"() {
        request.parameterNames >> ([#foreach($urlParameter in $params)"$urlParameter"#if( $velocityCount != $nbParams), #end#end] as Enumeration)
        #foreach($urlParameter in $params)request.getParameter("$urlParameter") >> "aValue$velocityCount"
        #end

        context.resourceProvider >> resourceProvider
        resourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, responseBuilder, context)

        then:
        def returnedJson = new JsonSlurper().parseText(responseBuilder.build().response)
        //Assertions
        #foreach($urlParameter in $params)returnedJson.$urlParameter == "aValue$velocityCount"
        #end
returnedJson.hostName == "bonitasoft.com"
    }

#foreach($urlParameter in $params)
    def "should return an error response if $urlParameter is not set"() {
        request.parameterNames >> ([#foreach($urlParameter in $params)"$urlParameter"#if( $velocityCount != $nbParams), #end#end] as Enumeration)
        request.getParameter("$urlParameter") >> null
        #foreach($p in $params)#if($p != $urlParameter)request.getParameter("$p") >> "aValue$velocityCount"
        #end#end

        context.resourceProvider >> resourceProvider
        resourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, responseBuilder, context)

        then:  
        RestApiResponse restApiResponse = responseBuilder.build()
        def returnedJson = new JsonSlurper().parseText(restApiResponse.response)
        //Assertions
        restApiResponse.httpStatus == 400
        returnedJson.error == "the parameter $urlParameter is missing"
    }

#end}