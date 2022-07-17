package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConfirmTokenServiceImpel confirmTokenServiceImpel;

    ExpertDto expertDto = new ExpertDto(null,"farzad","afshar"
            ,"farzadafi50@gmail.com","aA 1!aaa","aA 1!aaa","kerman",null);

    @Test
    @Order(1)
    public void save() throws Exception {
        Path path = Paths.get("src/test/resources/image.jpg");//read image from resource directory
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            log.warn("read image ");
        }
        MultipartFile multipartFile = new MockMultipartFile(path.getFileName().toString(),
                                path.getFileName().toString(), "file/image", content);
        expertDto.setImage(multipartFile);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/expert/save")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .flashAttr("expertDto",expertDto);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(2)
    public void ConfirmAccount() throws Exception {
        ConfirmationToken confirmationToken = confirmTokenServiceImpel.findConfirmationTokensByUserId(1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/token/confirm/" + confirmationToken.getConfirmToken())
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        if(!response.getContentAsString().equals("ایمیل شما با موفقیت تایید شد!"))
            fail();
    }
}