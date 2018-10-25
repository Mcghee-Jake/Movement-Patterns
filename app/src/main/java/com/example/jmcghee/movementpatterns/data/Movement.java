package com.example.jmcghee.movementpatterns.data;

import java.io.Serializable;

public class Movement implements Serializable {

    private int id;
    private String name;

    public Movement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
