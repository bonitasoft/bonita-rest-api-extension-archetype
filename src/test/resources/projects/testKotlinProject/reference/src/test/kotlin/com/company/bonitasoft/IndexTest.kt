package com.company.bonitasoft

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.servlet.http.HttpServletRequest
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.bonitasoft.web.extension.rest.RestAPIContext

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
        // Simulate a request with a value for each parameter 
        every { httpRequest.getParameter("userId") } returns "aValue1";
        every { httpRequest.getParameter("startDate") } returns "aValue2";

        // When
        val apiResponse = index.doHandle(httpRequest, RestApiResponseBuilder(), context)

        // Then
        val jsonResponse = index.mapper.readValue(apiResponse.response as String, Map::class.java)
        // Validate returned response
        assertThat(apiResponse.httpStatus).isEqualTo(200)
        assertThat(jsonResponse["userId"]).isEqualTo("aValue1")
        assertThat(jsonResponse["startDate"]).isEqualTo("aValue2")
        assertThat(jsonResponse["myParameterKey"]).isEqualTo("testValue")
    }

    @Test
    fun `should return an error response if userId is not set`() {
        // Given
        every { httpRequest.getParameter("userId") } returns null;
        // Other parameters return a valid value 
        every { httpRequest.getParameter("startDate") } returns "aValue2";

        // When
        val apiResponse = index.doHandle(httpRequest, RestApiResponseBuilder(), context)

        // Then
        val jsonResponse = index.mapper.readValue(apiResponse.response as String, Map::class.java)
        // Validate returned response
        assertThat(apiResponse.httpStatus).isEqualTo(400)
        assertThat(jsonResponse["error"]).isEqualTo("the parameter userId is missing")
    }

    @Test
    fun `should return an error response if startDate is not set`() {
        // Given
        every { httpRequest.getParameter("startDate") } returns null;
        // Other parameters return a valid value 
        every { httpRequest.getParameter("userId") } returns "aValue1";

        // When
        val apiResponse = index.doHandle(httpRequest, RestApiResponseBuilder(), context)

        // Then
        val jsonResponse = index.mapper.readValue(apiResponse.response as String, Map::class.java)
        // Validate returned response
        assertThat(apiResponse.httpStatus).isEqualTo(400)
        assertThat(jsonResponse["error"]).isEqualTo("the parameter startDate is missing")
    }

}