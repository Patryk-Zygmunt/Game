package com.example.game.model;

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
    public int getOppositeValue(){
        if(value==1) return 2;
        else return 1;
    }

    public int get1Dim(){
      if(y==0) return x;
      if(y==1) return x+3;
               return x+6;
    };

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

    @Override
    public String toString() {
        return "Field{" +
                "x=" + x +
                ", y=" + y +
                ", value=" + value +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }
}

