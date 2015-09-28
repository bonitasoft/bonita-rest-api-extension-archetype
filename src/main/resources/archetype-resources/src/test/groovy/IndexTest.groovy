#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
import groovy.json.JsonSlurper
import groovy.sql.Sql
import org.bonitasoft.console.common.server.page.PageContext
import org.bonitasoft.console.common.server.page.PageResourceProvider
import org.bonitasoft.console.common.server.page.RestApiResponse
import org.bonitasoft.console.common.server.page.RestApiResponseBuilder
import org.bonitasoft.console.common.server.page.RestApiUtil
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import java.util.logging.Logger


class IndexTest extends Specification {

    def request = Mock(HttpServletRequest)
    def pageResourceProvider = Mock(PageResourceProvider)
    def pageContext = Mock(PageContext)
    def apiResponseBuilder = new RestApiResponseBuilder()
    def restApiUtil = Mock(RestApiUtil)
    def logger = Mock(Logger)

    def index = Spy(Index)


    def "test case description goes here"() {
        //Mock example
        request.parameterNames >> (["paramName"] as Enumeration)
        request.getParameter("paramName") >> "aValue"

        pageResourceProvider.getResourceAsStream("queries.properties") >> index.class.classLoader.getResourceAsStream("configuration.properties")

        //Prepare tests here

        when:
        index.doHandle(request, pageResourceProvider, pageContext, apiResponseBuilder, restApiUtil)

        then:
        def returnedJson = new JsonSlurper().parseText(apiResponseBuilder.build().response)
        //Assertions goes here
        //returnedJson[0].clientId == "894184d6-0930-11e5-a6c0-1697f925ec7b"
        //returnedJson[0].first_name == "William"
        //returnedJson[0].last_name == "Jobs"
        //returnedJson[0].city == "Grenoble"
        //returnedJson[0].country == "France"
    }

   
}
