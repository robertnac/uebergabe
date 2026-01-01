package com.example.demo.service;

import com.example.demo.model.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private static final String FILE_PATH = "persons.txt";
    private final List<Person> persons = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PersonService() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @PostConstruct
    public void init() {
        try {
            loadPersons();
        } catch (IOException e) {
            System.err.println("Could not load persons from file: " + e.getMessage());
        }
    }

    public void savePerson(Person person) throws IOException {
        persons.add(person);
        objectMapper.writeValue(new File(FILE_PATH), persons);
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(persons);
    }

    private void loadPersons() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try {
            List<Person> loadedPersons = objectMapper.readValue(file, new TypeReference<List<Person>>() {
            });
            persons.clear();
            persons.addAll(loadedPersons);
        } catch (Exception e) {
            System.err.println("Could not parse JSON from file: " + e.getMessage());
        }
    }
}
