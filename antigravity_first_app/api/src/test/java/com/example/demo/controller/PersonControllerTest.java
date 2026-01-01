package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetForm() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("person-form"))
                .andExpect(model().attributeExists("person"));
    }

    @Test
    public void testSubmitForm() throws Exception {
        mockMvc.perform(post("/person")
                .param("vorname", "John")
                .param("nachname", "Doe")
                .param("birthDate", "1990-01-01"))
                .andExpect(status().isOk())
                .andExpect(view().name("person-result"))
                .andExpect(model().attributeExists("person"));

        verify(personService).savePerson(any(Person.class));
    }

    @Test
    public void testListPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(view().name("person-list"))
                .andExpect(model().attributeExists("persons"));
    }
}
