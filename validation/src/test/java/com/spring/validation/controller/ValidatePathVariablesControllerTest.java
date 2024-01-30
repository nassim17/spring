package com.spring.validation.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class ValidatePathVariablesControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void invalidRequestParameters() throws Exception {

        mvc.perform(get("/validation/path-variables/3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validRequestParameters() throws Exception {

        mvc.perform(get("/validation/path-variables/5"))
                .andExpect(status().isOk());
    }
}
