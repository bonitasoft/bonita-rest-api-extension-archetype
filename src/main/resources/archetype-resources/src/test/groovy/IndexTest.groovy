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

import javax.servlet.http.HttpServletRequest

import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder

import spock.lang.Specification
#if( ${sp} == 'false' )
import org.bonitasoft.web.extension.rest.RestAPIContext
#else
import com.bonitasoft.web.extension.rest.RestAPIContext
#end

/**
 * @see http://spockframework.github.io/spock/docs/1.0/index.html
 */
class IndexTest extends Specification {

    // Declare mocks here
    // Mocks are used to simulate external dependencies behavior
    def httpRequest = Mock(HttpServletRequest)
    def resourceProvider = Mock(ResourceProvider)
    def context = Mock(RestAPIContext)

    /**
     * You can configure mocks before each tests in the setup method
     */
    def setup(){
        // Simulate access to configuration.properties resource
        context.resourceProvider >> resourceProvider
        resourceProvider.getResourceAsStream("configuration.properties") >> IndexTest.class.classLoader.getResourceAsStream("testConfiguration.properties")
    }

    def should_return_a_json_representation_as_result() {
        given: "a RestAPIController"
        def index = new Index()
        #if( $nbParams != 0 )// Simulate a request with a value for each parameter
#end
#foreach($urlParameter in $params)
        httpRequest.getParameter("$urlParameter") >> "aValue$velocityCount"
#end

        when: "Invoking the REST API"
        def apiResponse = index.doHandle(httpRequest, new RestApiResponseBuilder(), context)

        then: "A JSON representation is returned in response body"
        def jsonResponse = new JsonSlurper().parseText(apiResponse.response)
        // Validate returned response
        apiResponse.httpStatus == 200
        #foreach($urlParameter in $params)jsonResponse.$urlParameter == "aValue$velocityCount"
        #end
jsonResponse.myParameterKey == "testValue"
    }

#foreach($urlParameter in $params)
    def should_return_an_error_response_if_${urlParameter}_is_not_set() {
        given: "a request without $urlParameter"
        def index = new Index()
        httpRequest.getParameter("$urlParameter") >> null
        #if( $nbParams != 0 )// Other parameters return a valid value
#end
#foreach($p in $params)#if($p != $urlParameter)
        httpRequest.getParameter("$p") >> "aValue$velocityCount"
#end#end

        when: "Invoking the REST API"
        def apiResponse = index.doHandle(httpRequest, new RestApiResponseBuilder(), context)

        then: "A JSON response is returned with a HTTP Bad Request Status (400) and an error message in body"
        def jsonResponse = new JsonSlurper().parseText(apiResponse.response)
        // Validate returned response
        apiResponse.httpStatus == 400
        jsonResponse.error == "the parameter $urlParameter is missing"
    }

#end}