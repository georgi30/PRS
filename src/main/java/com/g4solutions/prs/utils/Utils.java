package com.g4solutions.prs.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.g4solutions.prs.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Utils {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final ObjectReader READER = MAPPER.reader().forType(new TypeReference<Set<Person>>() {
  });
  private final KafkaTemplate<String, Person> template;

  @Value(value = "${kafka.topic}")
  private String topic;


  @Autowired
  public Utils(KafkaTemplate<String, Person> template) {
    this.template = template;
  }

  public Set<Person> findPeopleByCriteria(String gender, int age, long maxCount) throws IOException {
    Set<Person> people =
        READER.readValue(new FileInputStream("/Users/gvbox/IdeaProjects/prs/src/main/resources/people.json"));

    return people.stream()
        .filter(person -> person.getAge() == age && person.getGender().equals(gender))
        .limit(maxCount)
        .collect(Collectors.toSet());
  }

  public void sendRecordsToKafka(Set<Person> people) {
    people.forEach(person -> template.send(topic, person));
  }

}
