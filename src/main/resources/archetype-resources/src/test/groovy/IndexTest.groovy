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
import groovy.json.JsonSlurper

import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest

import org.bonitasoft.console.common.server.page.PageContext
import org.bonitasoft.console.common.server.page.PageResourceProvider
import org.bonitasoft.console.common.server.page.RestApiResponse
import org.bonitasoft.console.common.server.page.RestApiResponseBuilder
import org.bonitasoft.console.common.server.page.RestApiUtil

import spock.lang.Specification
    
class IndexTest extends Specification {

    def request = Mock(HttpServletRequest)
    def pageResourceProvider = Mock(PageResourceProvider)
    def pageContext = Mock(PageContext)
    def apiResponseBuilder = new RestApiResponseBuilder()
    def restApiUtil = Mock(RestApiUtil)
    def logger = Mock(Logger)

    def index = Spy(Index)

    def "should return a json representation as result"() {
        request.parameterNames >> ([#foreach($urlParameter in $params)"$urlParameter"#if( $velocityCount != $nbParams), #end#end] as Enumeration)
        #foreach($urlParameter in $params)request.getParameter("$urlParameter") >> "aValue$velocityCount"
        #end

        pageResourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, pageResourceProvider, pageContext, apiResponseBuilder, restApiUtil)

        then:
        def returnedJson = new JsonSlurper().parseText(apiResponseBuilder.build().response)
        //Assertions
        #foreach($urlParameter in $params)returnedJson.$urlParameter == "aValue$velocityCount"
        #end
returnedJson.hostName == "bonitasoft.com"
    }

#foreach($urlParameter in $params)
    def "should return an error response if $urlParameter is not set"() {
        request.parameterNames >> ([#foreach($urlParameter in $params)"$urlParameter"#if( $velocityCount != $nbParams), #end#end] as Enumeration)
        #foreach($p in $params)#if($p != $urlParameter)request.getParameter("$p") >> "aValue$velocityCount"#end#end

        request.getParameter("$urlParameter") >> null
        restApiUtil.logger >> logger
        

        pageResourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, pageResourceProvider, pageContext, apiResponseBuilder, restApiUtil)

        then:
        //Verify
        1 * logger.severe("the parameter $urlParameter is missing")
        
        RestApiResponse restApiResponse = apiResponseBuilder.build()
        def returnedJson = new JsonSlurper().parseText(restApiResponse.response)
        //Assertions
        restApiResponse.httpStatus == 400
        returnedJson.error == "the parameter $urlParameter is missing"
    }

#end}