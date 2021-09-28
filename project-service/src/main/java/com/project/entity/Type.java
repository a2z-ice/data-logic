package com.project.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;

@Getter
public enum Type {
    TYPE_1("TYPE-1"), TYPE_2("TYPE-2"), TYPE_3("TYPE-3");
    private String name;

    Type(String name) {
        this.name = name;
    }

    @JsonCreator
    public static Type fromName(String type) {
        if(null == type) return null;
        switch (type) {
            case "TYPE-1":
                return Type.TYPE_1;
            case "TYPE-2":
                return Type.TYPE_2;
            case "TYPE-3":
                return Type.TYPE_3;
            default:
                return null;

        }
    }

    @JsonValue
    public String getValue() {
        return name;
    }

//    @Override
//    public String toString() {
//        return name;
//    }
}
