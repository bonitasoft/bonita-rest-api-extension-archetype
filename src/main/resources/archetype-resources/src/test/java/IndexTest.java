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
package ${groupId};

#if( ${sp} == 'false' )
import org.bonitasoft.web.extension.rest.RestAPIContext;
#else
import com.bonitasoft.web.extension.rest.RestAPIContext;
#end
import ${groupId}.dto.Error;
import ${groupId}.dto.Result;
import ${groupId}.exception.ValidationException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class IndexTest {

    // Declare mocks here
    // Mocks are used to simulate external dependencies behavior
    @Mock
    HttpServletRequest httpRequest;
    @Mock(lenient = true)
    ResourceProvider resourceProvider;
    @Mock(lenient = true)
    RestAPIContext context;

    Index indexController;

    /**
     * You can configure mocks before each tests in the setup method
     */
    @BeforeEach
    void setUp() throws FileNotFoundException {
        indexController = new Index();
        // Simulate access to configuration.properties resource
        when(context.getResourceProvider()).thenReturn(resourceProvider);
        when(resourceProvider.getResourceAsStream("configuration.properties")).thenReturn(IndexTest.class.getResourceAsStream("testConfiguration.properties"));
    }

    @Test
    void should_throw_exception_if_mandatory_input_is_missing() {
        assertThrows(ValidationException.class, () ->
                indexController.validateInputParameters(httpRequest)
        );
    }

    void should_get_result_when_params_ok() {

        // Given
#foreach ($urlParameter in $params)
        String $urlParameter = "aValue$velocityCount";
#end
        String paramValue = "testValue";

        // When
        Result result = indexController.execute(#foreach($urlParameter in $params)$urlParameter,#end paramValue);

        // Then
#foreach($urlParameter in $params)
        assertThat(result.get${urlParameter.substring(0,1).toUpperCase()}${urlParameter.substring(1)}()).isEqualTo($urlParameter);
#end
        assertThat(result.getMyParameterKey()).isEqualTo(paramValue);
    }

    void should_return_a_json_representation_as_result() throws IOException {
        // Given "a RestAPIController"

        // Simulate a request with a value for each parameter
#foreach($urlParameter in $params)
        when(httpRequest.getParameter("$urlParameter")).thenReturn("aValue$velocityCount");
#end

        // When "Invoking the REST API"
        RestApiResponse apiResponse = indexController.doHandle(httpRequest, new RestApiResponseBuilder(), context);

        // Then "A JSON representation is returned in response body"
        Result jsonResponse = indexController.getMapper().readValue((String) apiResponse.getResponse(), Result.class);

        // Validate returned response
        assertThat(apiResponse.getHttpStatus()).isEqualTo(200);
        assertThat(jsonResponse).isNotNull();
    }

#foreach($urlParameter in $params)
    void should_return_an_error_response_if_${urlParameter}_is_not_set() throws IOException {
        // Given "a request without $urlParameter"
        when(httpRequest.getParameter("$urlParameter")).thenReturn(null);
#foreach($p in $params)#if($p != $urlParameter)
        when(httpRequest.getParameter("$p")).thenReturn("aValue$velocityCount");
#end#end

        // When "Invoking the REST API"
        RestApiResponse apiResponse = indexController.doHandle(httpRequest, new RestApiResponseBuilder(), context);
        indexController.doHandle(httpRequest, new RestApiResponseBuilder(), context);

        // Then "A JSON response is returned with a HTTP Bad Request Status (400) and an error message in body"
        Error jsonResponse = indexController.getMapper().readValue((String) apiResponse.getResponse(), Error.class);
        // Validate returned response
        assertThat(apiResponse.getHttpStatus()).isEqualTo(400);
        assertThat(jsonResponse.getMessage()).isEqualTo("the parameter ${urlParameter} is missing");
    }

#end
}