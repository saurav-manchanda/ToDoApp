package com.bridgelabz.todoapplication.usertests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.todoapplication.ToDoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ToDoApplication.class)
public class UserTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

        @Test
        public void loginTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"email\": \"saurav.manchanda2009@gmail.com\", \"password\" : \"saurav@123\"}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Welcome to the Application.You are successfully logged in "))
                    .andExpect(jsonPath("$.status").value(200));
                   
        }
        
        @Test
        public void loginEmailEmptyStringTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"email\": \"\", \"password\" : \"saurav@123\"}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Email cannot be an empty string"))
                    .andExpect(jsonPath("$.status").value(-1));
                   
        }
        @Test
        public void loginPasswordEmptyStringTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"email\": \"saurav.manchanda2009@gmail.com\", \"password\" : \"\"}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Password cannot be an empty string"))
                    .andExpect(jsonPath("$.status").value(-1));
                   
        }
        @Test
        public void loginEmailNullTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"password\" : \"saurav@123\"}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Email cannot be Null"))
                    .andExpect(jsonPath("$.status").value(-1));
                   
        }
        @Test
        public void loginPasswordNullTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"email\": \"saurav.manchanda2009@gmail.com\"}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Password Cannot be null"))
                    .andExpect(jsonPath("$.status").value(-1));
                   
        }
        @Test
        public void registerationTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"email\": \"saurav.manchanda2009@gmail.com\", \"password\" : \"saurav@123\",\"id\":\"1\",\"userName\":\"saurav\"}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("User successfully registered saurav"))
                    .andExpect(jsonPath("$.status").value(200));
        }
}

