package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.enumoration.Role;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Expert expert = new Expert("farzadExpert","afshar","fe@gmail.com","aA 1!aaa",0L,
            Role.ROLE_EXPERT,"kerman",null,null);
    Customer customer = new Customer("farzadCustomer","afshar","fc@gmail.com","aA 1!aaa",0L,
            Role.ROLE_CUSTOMER);

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("OfferControllerData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    public void placeAnOffer() throws Exception {
        expert.setId(1);
        JSONObject offerJson = new JSONObject();
        offerJson.put("proposedPrice", 1000000);
        offerJson.put("durationWork", 10);
        offerJson.put("startTime", "10:10");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/offer/placeAnOffer/" + 1 )
                .with(SecurityMockMvcRequestPostProcessors.user(expert))
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerJson.toJSONString());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(2)
    public void viewOffer() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/offer/viewOffer/" + 1 )
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(customer));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(3)
    public void selectOffer() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/offer/selectOffer/" + 1 + "/" + 1 )
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(customer));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(4)
    public void viewAcceptedOffer() throws Exception {
        expert.setId(1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/offer/viewAcceptedOffer")
                .with(SecurityMockMvcRequestPostProcessors.user(expert));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}