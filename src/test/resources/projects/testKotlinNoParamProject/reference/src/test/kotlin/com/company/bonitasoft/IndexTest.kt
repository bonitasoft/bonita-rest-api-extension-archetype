package com.company.bonitasoft

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import jakarta.servlet.http.HttpServletRequest
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.bonitasoft.web.extension.rest.RestAPIContext
import java.time.LocalDate

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


        // When
        val apiResponse = index.doHandle(httpRequest, RestApiResponseBuilder(), context)

        // Then
        val jsonResponse = index.mapper.readValue(apiResponse.response as String, Map::class.java)
        // Validate returned response
        assertThat(apiResponse.httpStatus).isEqualTo(200)
        assertThat(jsonResponse["myParameterKey"]).isEqualTo("testValue")
        assertThat(jsonResponse["currentDate"]).isEqualTo(LocalDate.now().toString())
    }

}