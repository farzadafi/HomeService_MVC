package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.enumoration.Role;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Customer customer = new Customer("farzad", "afshar", "farzadafi50@gmail.com",
            "aA 1!aaa", 50000L, Role.ROLE_CUSTOMER);

    @Test
    @Order(1)
    public void save() throws Exception {
        JSONObject customerJson = new JSONObject();
        customerJson.put("firstName","farzad");
        customerJson.put("lastName","afshar");
        customerJson.put("email","farzadafi50@gmail.com");
        customerJson.put("password","aA 1!aaa");
        customerJson.put("confPassword","aA 1!aaa");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson.toJSONString());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(2)
    public void updateCustomerPassword() throws Exception {
        customer.setId(1);
        JSONObject passwordJson = new JSONObject();
        passwordJson.put("password", "aA 1!aaa111");
        passwordJson.put("confPassword", "aA 1!aaa111");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/updatePassword")
                .with(SecurityMockMvcRequestPostProcessors.user(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .content(passwordJson.toJSONString());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(3)
    public void showBalance() throws Exception {
        customer.setId(1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/showBalance/")
                .with(SecurityMockMvcRequestPostProcessors.user(customer))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        if(!response.getContentAsString().equals("50000"))
            fail();
    }
}