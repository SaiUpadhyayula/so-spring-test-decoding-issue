package com.example.sospringtestdecodingissue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmailControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("inputValues")
    void testEmailInput(String inputEmail, String expectedOutputEmail) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/email")
                        .param("email", inputEmail))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(expectedOutputEmail));
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