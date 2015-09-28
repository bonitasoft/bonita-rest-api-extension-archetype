#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
import groovy.json.JsonBuilder
import groovy.sql.Sql
import org.bonitasoft.console.common.server.page.*

import javax.naming.Context
import javax.naming.InitialContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.sql.DataSource
import java.util.logging.Logger

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

}

