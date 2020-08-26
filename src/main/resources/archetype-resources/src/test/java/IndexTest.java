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
package ${package};

#if( ${sp} == 'false' )
import org.bonitasoft.web.extension.rest.RestAPIContext;
#else
import com.bonitasoft.web.extension.rest.RestAPIContext;
#end
#if( $urlParameters != "!"  )
import ${package}.dto.Error;
#end
import ${package}.dto.Result;
#if( $urlParameters != "!"  )
import ${package}.exception.ValidationException;
#end
import org.bonitasoft.web.extension.ResourceProvider;
import org.bonitasoft.web.extension.rest.RestApiResponse;
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
#if( $urlParameters != "!"  )
import static org.junit.jupiter.api.Assertions.assertThrows;
#end
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class IndexTest {

    // Declare mocks here
    // Mocks are used to simulate external dependencies behavior
    @Mock(lenient = true)
    private HttpServletRequest httpRequest;
    @Mock(lenient = true)
    private ResourceProvider resourceProvider;
    @Mock(lenient = true)
    private RestAPIContext context;

    // The controller to test
    private Index index;

    /**
     * You can configure mocks before each tests in the setup method
     */
    @BeforeEach
    void setUp() throws FileNotFoundException {
        // Create a new instance under test
        index = new Index();

        // Simulate access to configuration.properties resource
        when(context.getResourceProvider()).thenReturn(resourceProvider);
        when(resourceProvider.getResourceAsStream("configuration.properties"))
                .thenReturn(IndexTest.class.getResourceAsStream("/testConfiguration.properties"));
    }

#if($nbParams > 0 )
    @Test
    void should_throw_exception_if_mandatory_input_is_missing() {
        assertThrows(ValidationException.class, () ->
                index.validateInputParameters(httpRequest)
        );
    }
#end

    @Test
    void should_get_result_when_params_ok() {

        // Given
#foreach ($urlParameter in $params)
        String $urlParameter = "aValue$velocityCount";
#end

        // When
        Result result = index.execute(context#foreach($urlParameter in $params), $urlParameter#end);

        // Then
#foreach($urlParameter in $params)
        assertThat(result.get${urlParameter.substring(0,1).toUpperCase()}${urlParameter.substring(1)}()).isEqualTo($urlParameter);
#end
        assertThat(result.getMyParameterKey()).isEqualTo("testValue");
        assertThat(result.getCurrentDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void should_return_a_json_representation_as_result() throws IOException {
        // Given "a RestAPIController"

        // Simulate a request with a value for each parameter
#foreach($urlParameter in $params)
        when(httpRequest.getParameter("$urlParameter")).thenReturn("aValue$velocityCount");
#end

        // When "Invoking the REST API"
        RestApiResponse apiResponse = index.doHandle(httpRequest, new RestApiResponseBuilder(), context);

        // Then "A JSON representation is returned in response body"
        Result jsonResponse = index.getMapper().readValue((String) apiResponse.getResponse(), Result.class);

        // Validate returned response
        assertThat(apiResponse.getHttpStatus()).isEqualTo(200);
        assertThat(jsonResponse).isNotNull();
    }

#foreach($urlParameter in $params)
    @Test
    void should_return_an_error_response_if_${urlParameter}_is_not_set() throws IOException {
        // Given "a request without $urlParameter"
        when(httpRequest.getParameter("$urlParameter")).thenReturn(null);
#foreach($p in $params)#if($p != $urlParameter)
        when(httpRequest.getParameter("$p")).thenReturn("aValue$velocityCount");
#end#end

        // When "Invoking the REST API"
        RestApiResponse apiResponse = index.doHandle(httpRequest, new RestApiResponseBuilder(), context);

        // Then "A JSON response is returned with a HTTP Bad Request Status (400) and an error message in body"
        Error jsonResponse = index.getMapper().readValue((String) apiResponse.getResponse(), Error.class);
        // Validate returned response
        assertThat(apiResponse.getHttpStatus()).isEqualTo(400);
        assertThat(jsonResponse.getMessage()).isEqualTo("the parameter ${urlParameter} is missing");
    }

#end
}