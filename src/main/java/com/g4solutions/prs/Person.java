package com.g4solutions.prs;

public class Person {

  private final int id;
  private final String name;
  private final int age;
  private final String gender;

  public Person() {
    this(0, null, 0, null);
  }

  public Person(int id, String name, int age, String gender) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }
}
