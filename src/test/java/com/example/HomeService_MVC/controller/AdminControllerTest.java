package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql("AdminControllerData.sql")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username="admin",roles={"ADMIN","PRE_VERIFICATION_USER"})
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpertServiceImpel expertServiceImpel;

    @Test
    public void GetAllExpertFalse() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/getAllExpertFalse")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void ConfirmExpert() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/confirmExpert/" + 2)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}