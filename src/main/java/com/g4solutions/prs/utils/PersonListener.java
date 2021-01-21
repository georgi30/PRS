package com.g4solutions.prs.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.g4solutions.prs.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PersonListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(PersonListener.class);
  private static final String FILE_NAME = "/Users/gvbox/IdeaProjects/prs/src/main/resources/result.json";

  @KafkaListener(topics = "demo", groupId = "prs")
  public void personListener(Person person) {
    LOGGER.info("Message has been consumed: {} {} {}", person.getName(), person.getAge(), person.getGender());
    ObjectMapper mapper = new ObjectMapper();

    try {
      ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

      List<Person> people = fileExists(FILE_NAME) ? readFileContents(FILE_NAME) : new ArrayList<>();

      if(!containsPerson(people, person)) {
        people.add(person);

        writer.writeValue(Paths.get(FILE_NAME).toFile(), people);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean fileExists(String file) {
    return new File(file).exists();
  }

  private boolean containsPerson(List<Person> people, Person person) {
    return people.stream().anyMatch(p -> p.getId() == person.getId());
  }

  private ArrayList<Person> readFileContents(String file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    return new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(file).toFile(), Person[].class)));
  }

}
