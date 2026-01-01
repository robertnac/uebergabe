package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class MigrateData {
    public static void main(String[] args) {
        String filePath = "persons.txt"; // Assumes run from where the file is
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No persons.txt found at " + file.getAbsolutePath());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            JsonNode root = mapper.readTree(file);
            if (root.isArray()) {
                ArrayNode newRoot = mapper.createArrayNode();
                for (JsonNode node : root) {
                    ObjectNode person = (ObjectNode) node;
                    if (person.has("name")) {
                        String fullName = person.get("name").asText();
                        String vorname = "";
                        String nachname = "";

                        int firstSpace = fullName.indexOf(" ");
                        if (firstSpace != -1) {
                            vorname = fullName.substring(0, firstSpace);
                            nachname = fullName.substring(firstSpace + 1);
                        } else {
                            vorname = fullName;
                        }

                        person.remove("name");
                        person.put("vorname", vorname);
                        person.put("nachname", nachname);
                    }
                    newRoot.add(person);
                }
                mapper.writeValue(file, newRoot);
                System.out.println("Migration completed successfully.");
            } else {
                System.out.println("Root is not an array. Check persons.txt format.");
            }
        } catch (IOException e) {
            System.err.println("Migration failed: " + e.getMessage());
        }
    }
}
