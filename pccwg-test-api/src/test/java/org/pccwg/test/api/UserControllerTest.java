package org.pccwg.test.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.pccwg.test.api.producer.EventSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = { "spring.datasource.initialization-mode=never" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class UserControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EventSender mockEventSender;
    
    @Test
    @DisplayName("Register Check")   
    public void registerCheck() throws Exception {
    	
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"John Doe\","
        		+ "\"email\": \"john.doe@gmail.com\","
        		+ "\"password\": \"secret1\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Update Check")   
    public void updateCheck() throws Exception {
    	
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"John Doe\","
        		+ "\"email\": \"john.doe@gmail.com\","
        		+ "\"password\": \"secret1\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"))
                .andExpect(status().is2xxSuccessful());
        
        mockMvc.perform(put("/api/update").contentType(MediaType.APPLICATION_JSON).content(
        		"[{\"id\": 1,"
        		+ "\"name\": \"John W Doe\","
        		+ "\"email\": \"john.doe@yahoo.com\","
        		+ "\"password\": \"secret2\","
        		+ "\"deleted\": false}]"))
                .andDo(print())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("john.doe@yahoo.com"))
                .andExpect(jsonPath("$[0].name").value("John W Doe"))
                .andExpect(jsonPath("$[0].password").value("secret2"))
                .andExpect(status().is2xxSuccessful());        
    }
    
    @Test
    @DisplayName("Delete Check")   
    public void deleteCheck() throws Exception {
    	
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"John Doe\","
        		+ "\"email\": \"john.doe@gmail.com\","
        		+ "\"password\": \"secret1\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"))
                .andExpect(status().is2xxSuccessful());
        
        mockMvc.perform(delete("/api/delete").contentType(MediaType.APPLICATION_JSON).content("[1]"))
                .andDo(print())
                .andExpect(content().string("Users marked as deleted"))
                .andExpect(status().is2xxSuccessful());        
    }
    
    @Test
    @DisplayName("List Check")   
    public void listCheck() throws Exception {
    	
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"John Doe\","
        		+ "\"email\": \"john.doe@gmail.com\","
        		+ "\"password\": \"secret1\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"))
                .andExpect(status().is2xxSuccessful());
        
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"Jane Doe\","
        		+ "\"email\": \"jane.doe@gmail.com\","
        		+ "\"password\": \"secret2\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("jane.doe@gmail.com"))
                .andExpect(status().is2xxSuccessful());        
        
        mockMvc.perform(get("/api/list"))
                .andDo(print())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("john.doe@gmail.com"))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].password").value("secret1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("jane.doe@gmail.com"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].password").value("secret2"))                
                .andExpect(status().is2xxSuccessful());            
    }
    
    @Test
    @DisplayName("Duplicate Email Check")   
    public void duplicateEmailCheck() throws Exception {
    	
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"John Doe\","
        		+ "\"email\": \"john.doe@gmail.com\","
        		+ "\"password\": \"secret1\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"))
                .andExpect(status().is2xxSuccessful());
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"Jane Doe\","
        		+ "\"email\": \"jane.doe@gmail.com\","
        		+ "\"password\": \"secret2\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("jane.doe@gmail.com"))
                .andExpect(status().is2xxSuccessful());        
        
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
        		"{\"id\": null,"
        		+ "\"name\": \"Jane Doe\","
        		+ "\"email\": \"john.doe@gmail.com\","
        		+ "\"password\": \"secret2\","
        		+ "\"deleted\": false}"))
                .andDo(print())
                .andExpect(jsonPath("$.errors").value("Error: Email already used: john.doe@gmail.com"))
                .andExpect(status().is4xxClientError());
        
        mockMvc.perform(put("/api/update").contentType(MediaType.APPLICATION_JSON).content(
        		"["
        		+ "{\"id\": 1,"
        		+ "\"name\": \"John Doe\","
        		+ "\"email\": \"jane.doe@gmail.com\","
        		+ "\"password\": \"secret1\","
        		+ "\"deleted\": false},"
        		+ "{\"id\": 2,"
        		+ "\"name\": \"Jane Doe\","
        		+ "\"email\": \"john.doe@gmail.com\","
        		+ "\"password\": \"secret2\","
        		+ "\"deleted\": false}"        		
        		+ "]"))
                .andDo(print())
                .andExpect(jsonPath("$.errors").value("Error: Emails already used: jane.doe@gmail.com,john.doe@gmail.com"))
                .andExpect(status().is4xxClientError());
    }
}
