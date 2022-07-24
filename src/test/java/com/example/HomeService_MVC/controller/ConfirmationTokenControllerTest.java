package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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
class ConfirmationTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConfirmTokenServiceImpel confirmTokenServiceImpel;

    @Autowired
    private CustomerServiceImpel customerServiceImpel;

    Customer customer = new Customer("farzad", "afshar", "farzadafi50@gmail.com",
            "aA 1!aaa",null,null);

    @Test
    @Order(1)
    public void confirmAccount() throws Exception {
        customerServiceImpel.save(customer);
        ConfirmationToken confirmationToken = new ConfirmationToken(customer);
        confirmTokenServiceImpel.save(confirmationToken);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/token/confirm/" + confirmationToken.getConfirmToken())
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        if(!response.getContentAsString().equals("ایمیل شما با موفقیت تایید شد!"))
            fail();
    }
}