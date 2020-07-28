#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#if( $urlParameters != "!"  )
#set( $params = $urlParameters.split(",") )
#end
package ${package}

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.servlet.http.HttpServletRequest
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
#if( ${sp} == 'false' )
import org.bonitasoft.web.extension.rest.RestAPIContext
#else
import com.bonitasoft.web.extension.rest.RestAPIContext
#end

class IndexTest {

    // Declare mocks here
    // Mocks are used to simulate external dependencies behavior
    val httpRequest = mockk<HttpServletRequest>()
    val resourceProvider = mockk<ResourceProvider>()
    val context = mockk<RestAPIContext>()

    lateinit var index: Index

    @BeforeEach
    fun setUp() {
        // Create a new instance per test
        index = Index();

        // Simulate access to configuration.properties resource
        every { context.getResourceProvider() } returns resourceProvider;
        every { resourceProvider.getResourceAsStream("configuration.properties") } returns
                IndexTest::class.java.getResourceAsStream("/testConfiguration.properties");
    }

    @Test
    fun `should return a json representation as result`() {
        // Given
#if( $nbParams != 0 )        // Simulate a request with a value for each parameter #end

#foreach($urlParameter in $params)
        every { httpRequest.getParameter("$urlParameter") } returns "aValue$velocityCount";
#end

        // When
        val apiResponse = index.doHandle(httpRequest, RestApiResponseBuilder(), context)

        // Then
        val jsonResponse = index.mapper.readValue(apiResponse.response as String, Map::class.java)
        // Validate returned response
        assertThat(apiResponse.httpStatus).isEqualTo(200)
#foreach($urlParameter in $params)
        assertThat(jsonResponse["$urlParameter"]).isEqualTo("aValue$velocityCount")
#end
        assertThat(jsonResponse["myParameterKey"]).isEqualTo("testValue")
    }

#foreach($urlParameter in $params)
    @Test
    fun `should return an error response if ${urlParameter} is not set`() {
        // Given
        every { httpRequest.getParameter("$urlParameter") } returns null;
#if( $nbParams != 0 )        // Other parameters return a valid value #end

#foreach($p in $params)#if($p != $urlParameter)
        every { httpRequest.getParameter("$p") } returns "aValue$velocityCount";
#end#end

        // When
        val apiResponse = index.doHandle(httpRequest, RestApiResponseBuilder(), context)

        // Then
        val jsonResponse = index.mapper.readValue(apiResponse.response as String, Map::class.java)
        // Validate returned response
        assertThat(apiResponse.httpStatus).isEqualTo(400)
        assertThat(jsonResponse["error"]).isEqualTo("the parameter $urlParameter is missing")
    }

#end
}