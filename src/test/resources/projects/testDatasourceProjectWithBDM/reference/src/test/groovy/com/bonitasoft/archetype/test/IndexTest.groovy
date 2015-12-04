package com.bonitasoft.archetype.test;

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

    def "should_return_a_json_representation_as_result"() {
        request.parameterNames >> (["userId", "startDate"] as Enumeration)
        request.getParameter("userId") >> "aValue1"
        request.getParameter("startDate") >> "aValue2"
        
        context.resourceProvider >> resourceProvider
        resourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, responseBuilder, context)

        then:
        def returnedJson = new JsonSlurper().parseText(responseBuilder.build().response)
        //Assertions
        returnedJson.userId == "aValue1"
        returnedJson.startDate == "aValue2"
        returnedJson.hostName == "bonitasoft.com"
    }

    def "should_return_an_error_response_if_userId_is_not_set"() {
        request.parameterNames >> (["userId", "startDate"] as Enumeration)
        request.getParameter("userId") >> null
        request.getParameter("startDate") >> "aValue2"
        
        context.resourceProvider >> resourceProvider
        resourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, responseBuilder, context)

        then:  
        RestApiResponse restApiResponse = responseBuilder.build()
        def returnedJson = new JsonSlurper().parseText(restApiResponse.response)
        //Assertions
        restApiResponse.httpStatus == 400
        returnedJson.error == "the parameter userId is missing"
    }

    def "should_return_an_error_response_if_startDate_is_not_set"() {
        request.parameterNames >> (["userId", "startDate"] as Enumeration)
        request.getParameter("startDate") >> null
        request.getParameter("userId") >> "aValue1"
        
        context.resourceProvider >> resourceProvider
        resourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, responseBuilder, context)

        then:  
        RestApiResponse restApiResponse = responseBuilder.build()
        def returnedJson = new JsonSlurper().parseText(restApiResponse.response)
        //Assertions
        restApiResponse.httpStatus == 400
        returnedJson.error == "the parameter startDate is missing"
    }

}