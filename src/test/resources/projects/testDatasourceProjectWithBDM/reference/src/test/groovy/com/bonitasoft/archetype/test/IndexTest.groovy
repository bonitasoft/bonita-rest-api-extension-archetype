package com.bonitasoft.archetype.test;

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
        request.parameterNames >> (["userId", "startDate"] as Enumeration)
        request.getParameter("userId") >> "aValue1"
        request.getParameter("startDate") >> "aValue2"
        
        pageResourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, pageResourceProvider, pageContext, apiResponseBuilder, restApiUtil)

        then:
        def returnedJson = new JsonSlurper().parseText(apiResponseBuilder.build().response)
        //Assertions
        returnedJson.userId == "aValue1"
        returnedJson.startDate == "aValue2"
        returnedJson.hostName == "bonitasoft.com"
    }

    def "should return an error response if userId is not set"() {
        request.parameterNames >> (["userId", "startDate"] as Enumeration)
        request.getParameter("startDate") >> "aValue2"
        request.getParameter("userId") >> null
        restApiUtil.logger >> logger
        

        pageResourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, pageResourceProvider, pageContext, apiResponseBuilder, restApiUtil)

        then:
        //Verify
        1 * logger.severe("the parameter userId is missing")
        
        RestApiResponse restApiResponse = apiResponseBuilder.build()
        def returnedJson = new JsonSlurper().parseText(restApiResponse.response)
        //Assertions
        restApiResponse.httpStatus == 400
        returnedJson.error == "the parameter userId is missing"
    }

    def "should return an error response if startDate is not set"() {
        request.parameterNames >> (["userId", "startDate"] as Enumeration)
        request.getParameter("userId") >> "aValue1"
        request.getParameter("startDate") >> null
        restApiUtil.logger >> logger
        

        pageResourceProvider.getResourceAsStream("configuration.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        when:
        index.doHandle(request, pageResourceProvider, pageContext, apiResponseBuilder, restApiUtil)

        then:
        //Verify
        1 * logger.severe("the parameter startDate is missing")
        
        RestApiResponse restApiResponse = apiResponseBuilder.build()
        def returnedJson = new JsonSlurper().parseText(restApiResponse.response)
        //Assertions
        restApiResponse.httpStatus == 400
        returnedJson.error == "the parameter startDate is missing"
    }

}