package com.example.sospringtestdecodingissue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmailControllerTestRestTemplateIT {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private Integer port;

    @ParameterizedTest
    @MethodSource("inputValues")
    void testEmailInput(String inputEmail, String expectedOutputEmail) throws Exception {
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/api/email?email={email}",
                HttpMethod.GET,
                null,
                String.class,
                inputEmail);

        Assertions.assertThat(responseEntity.getBody())
                .isEqualTo(expectedOutputEmail);
    }

    static Stream<Arguments> inputValues() {
        return Stream.of(
                Arguments.of("test+email.com", "test email.com"),
                Arguments.of("test&email.com", "test"),
                Arguments.of("test%2Bemail.com", "test+email.com"),
                Arguments.of("test%26email.com", "test&email.com")
        );
    }

}