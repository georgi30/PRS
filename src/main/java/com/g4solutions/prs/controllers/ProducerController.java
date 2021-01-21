package com.g4solutions.prs.controllers;

import com.g4solutions.prs.Person;
import com.g4solutions.prs.dto.ProduceDto;
import com.g4solutions.prs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
public class ProducerController {

  private final Utils utils;

  @Autowired
  public ProducerController(Utils utils) {
    this.utils = utils;
  }

  @PostMapping("/api/search")
  public ResponseEntity<String> produceMessages(@RequestBody ProduceDto dto) throws IOException {
    Set<Person> people = utils.findPeopleByCriteria(dto.getGender(), dto.getAge(), dto.getPersonCount());

    utils.sendRecordsToKafka(people);

    return ResponseEntity.status(HttpStatus.CREATED).body("Created");
  }

}
