package com.g4solutions.prs.dto;

public class ProduceDto {

  private final int personCount;
  private final String gender;
  private final int age;

  public ProduceDto(int personCount, int age, String gender) {
    this.personCount = personCount;
    this.age = age;
    this.gender = gender;
  }

  public int getPersonCount() {
    return personCount;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }
}
