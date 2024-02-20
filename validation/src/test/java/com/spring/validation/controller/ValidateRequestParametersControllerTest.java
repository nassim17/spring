package com.spring.validation.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class ValidateRequestParametersControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void invalidRequestParameters() throws Exception {

        mvc.perform(get("/validation/request-parameters")
                        .param("param", "3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validRequestParameters() throws Exception {

        mvc.perform(get("/validation/request-parameters")
                        .param("param", "5"))
                .andExpect(status().isOk());
    }
}
