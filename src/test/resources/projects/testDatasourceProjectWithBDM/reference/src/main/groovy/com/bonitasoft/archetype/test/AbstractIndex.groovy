package com.bonitasoft.archetype.test;

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
        buildResponse apiResponseBuilder, """{"error" : "$message"}"""
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
    
    protected String getUserId(HttpServletRequest request){
        request.getParameter "userId"
    }

    protected String getStartDate(HttpServletRequest request){
        request.getParameter "startDate"
    }

}