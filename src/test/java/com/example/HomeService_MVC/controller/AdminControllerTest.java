package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.model.Admin;
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
import org.springframework.security.test.context.support.WithMockUser;
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
@WithMockUser(username = "admin@gmail.com", password = "aA 1!aaa", roles = {"ADMIN"})
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Admin admin = new Admin("admin", "admin", "admin@gmail.com",
            "aA 1!aaa", 0L, Role.ROLE_ADMIN);

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("AdminControllerData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    public void GetAllExpertFalse() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/getAllExpertFalse")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(2)
    public void ConfirmExpert() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/confirmExpert/" + 1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(3)
    public void addExpertToSubServices() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/addExpertToSubServices/" + "f@gmail.com/" + 1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(4)
    public void showExpertSubServices() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/showExpertSubServices/" + "f@gmail.com")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(5)
    public void removeExpertFromSubServices() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/removeExpertSubServices/" + "f@gmail.com/" + 1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(6)
    public void updateAdminPassword() throws Exception {
        admin.setId(2);
        JSONObject passwordJson = new JSONObject();
        passwordJson.put("password", "aA 1!aaa111");
        passwordJson.put("confPassword", "aA 1!aaa111");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/updateAdminPassword")
                .with(SecurityMockMvcRequestPostProcessors.user(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(passwordJson.toJSONString());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}