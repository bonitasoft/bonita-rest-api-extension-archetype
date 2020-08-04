package com.company.bonitasoft

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.bonitasoft.web.extension.ResourceProvider

import org.bonitasoft.web.extension.rest.RestAPIContext
import org.bonitasoft.web.extension.rest.RestApiController

import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import java.time.LocalDate
import java.util.Properties
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import javax.servlet.http.HttpServletResponse.SC_OK

/**
 * Controller class
 */
open class Index : RestApiController {

    var mapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        private set

    override fun doHandle(request: HttpServletRequest, responseBuilder: RestApiResponseBuilder, context: RestAPIContext): RestApiResponse {
        // To retrieve query parameters use the request.getParameter(..) method.
        // Be careful, parameter values are always returned as String values

        // Retrieve userId parameter
        val userId = request.getParameter("userId")
                ?: return buildResponse(responseBuilder, SC_BAD_REQUEST, mapOf("error" to "the parameter userId is missing"))
        // Retrieve startDate parameter
        val startDate = request.getParameter("startDate")
                ?: return buildResponse(responseBuilder, SC_BAD_REQUEST, mapOf("error" to "the parameter startDate is missing"))

        // Here is an example of how you can retrieve configuration parameters from a properties file
        // It is safe to remove this if no configuration is required
        val props = loadProperties("configuration.properties", context.resourceProvider)
        val paramValue = props["myParameterKey"]

        /*
         * Execute business logic here
         *
         *
         * Your code goes here
         *
         *
         */
        // Prepare the result
        val result = mapOf("userId" to userId, "startDate" to startDate,  "myParameterKey" to paramValue, "currentDate" to LocalDate.now())

        // Send the result as a JSON representation
        // You may use buildPagedResponse if your result is multiple
        return buildResponse(responseBuilder, SC_OK, result)
    }


    /**
     * Build an HTTP response.
     *
     * @param  responseBuilder the Rest API response builder
     * @param  httpStatus the status of the response
     * @param  body the response body
     * @return a RestAPIResponse
     */
    private fun buildResponse(responseBuilder: RestApiResponseBuilder, httpStatus: Int, body: Any): RestApiResponse {
        return responseBuilder
                .withResponseStatus(httpStatus)
                .withResponse(mapper.writeValueAsString(body))
                .build()
    }

    /**
     * Returns a paged result like Bonita BPM REST APIs.
     * Build a response with a content-range.
     *
     * @param  responseBuilder the Rest API response builder
     * @param  body the response body
     * @param  p the page index
     * @param  c the number of result per page
     * @param  total the total number of results
     * @return a RestAPIResponse
     */
    private fun buildPagedResponse(responseBuilder: RestApiResponseBuilder, httpStatus: Int, body: Any, p: Int, c: Int, total: Long): RestApiResponse {
        return responseBuilder
                .withResponseStatus(httpStatus)
                .withContentRange(p, c, total)
                .withResponse(mapper.writeValueAsString(body))
                .build()
    }

    /**
     * Load a property file into a java.util.Properties
     */
    private fun loadProperties(fileName: String, resourceProvider: ResourceProvider): Properties {
        val props = Properties()
        props.load(resourceProvider.getResourceAsStream(fileName))
        return props
    }
}