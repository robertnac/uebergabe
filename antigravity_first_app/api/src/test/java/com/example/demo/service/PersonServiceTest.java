package com.example.demo.service;

import com.example.demo.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonServiceTest {

    private final PersonService personService = new PersonService();
    private final String FILE_PATH = "src/main/resources/persons.txt";

    @AfterEach
    public void cleanup() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSavePerson() throws IOException {
        Person person = new Person();
        person.setVorname("Jane");
        person.setNachname("Doe");
        person.setBirthDate(LocalDate.of(1995, 5, 15));
        person.setVerheiratet(true);

        personService.savePerson(person);

        File file = new File(FILE_PATH);
        assertTrue(file.exists());

        String content = Files.readString(file.toPath());
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Person> savedPersons = mapper.readValue(content,
                new com.fasterxml.jackson.core.type.TypeReference<List<Person>>() {
                });

        assertTrue(savedPersons.size() == 1);
        Person savedPerson = savedPersons.get(0);
        assertTrue(savedPerson.getVorname().equals("Jane"));
        assertTrue(savedPerson.getNachname().equals("Doe"));
        assertTrue(savedPerson.getBirthDate().equals(LocalDate.of(1995, 5, 15)));
        assertTrue(savedPerson.isVerheiratet());
        assertTrue(personService.getAllPersons().size() == 1);
    }

    @Test
    public void testLoadPersons() throws IOException {
        // Create a dummy JSON file
        String content = "[{\"vorname\":\"Alice\",\"nachname\":\"\",\"birthDate\":\"1980-10-10\"},{\"vorname\":\"Bob\",\"nachname\":\"\",\"birthDate\":\"1990-12-12\"}]";
        Files.writeString(new File(FILE_PATH).toPath(), content);

        personService.init(); // Trigger load

        List<Person> loaded = personService.getAllPersons();
        assertTrue(loaded.size() >= 2);
        assertTrue(loaded.stream()
                .anyMatch(p -> p.getVorname().equals("Alice") && p.getBirthDate().toString().equals("1980-10-10")));
        assertTrue(loaded.stream()
                .anyMatch(p -> p.getVorname().equals("Bob") && p.getBirthDate().toString().equals("1990-12-12")));
    }
}
