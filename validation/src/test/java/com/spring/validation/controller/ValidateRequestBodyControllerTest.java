package com.spring.validation.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.validation.dto.PayLoadDto;

@SpringBootTest
@AutoConfigureMockMvc
class ValidateRequestBodyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void invalidPayLoad() throws Exception {
        // JDD
        PayLoadDto payLoadDto = new PayLoadDto();
        String body = objectMapper.writeValueAsString(payLoadDto);

        mvc.perform(post("/validation/validateBody/valid")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    private PayLoadDto validPayLoad(){
        PayLoadDto payLoadDto = new PayLoadDto();

        return payLoadDto;
    }
}
