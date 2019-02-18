package com.example.game;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


public class Field implements Serializable {
    private String id;
    private int x;
    private int y;
    private int value;

    public int getX() {
        return x;
    }

    public Field(String id, int x, int y, int value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public Field() {
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

