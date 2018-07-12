package com.example.jmcghee.movementpatterns;

import java.util.List;

public class Movement {

    private String name;
    private List<String> tags;

    public Movement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
