package com.example.demo.Demo_Movies.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Person")
public class PersonEntity {
    @Id
    private String name;
    private Integer born;
    public PersonEntity(Integer born, String name) {
        this.born = born;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Integer getBorn() {
        return born;
    }
    public void setBorn(Integer born) {
        this.born = born;
    }
    public void setName(String name) {
        this.name = name;
    }
}