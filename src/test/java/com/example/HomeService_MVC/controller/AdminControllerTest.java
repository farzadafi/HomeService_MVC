package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.model.enumoration.UserStatus;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username="admin",roles={"ADMIN","PRE_VERIFICATION_USER"})
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpertServiceImpel expertServiceImpel;
    static Expert expert = new Expert("farzad","farzad","f@gamil.com",
                               "farzad",100L, Role.ROLE_EXPERT,
                                   "kerman",null,0);

    @Test
    public void AGetAllExpertFalse() throws Exception {
        expertServiceImpel.save(expert);
        expert.setUserStatus(UserStatus.WAITING_FOR_ACCEPT);
        expertServiceImpel.updateEnable(expert);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/getAllExpertFalse")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void BConfirmExpert() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/confirmExpert/" + expert.getId())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}