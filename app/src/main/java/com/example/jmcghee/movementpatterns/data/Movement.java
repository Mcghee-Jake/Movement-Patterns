package com.example.jmcghee.movementpatterns.data;

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

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Movement.class) {
            Movement mov = (Movement) obj;
            if (this.getName().equals(mov.getName())) return true;
        }
        return false;
    }
}
