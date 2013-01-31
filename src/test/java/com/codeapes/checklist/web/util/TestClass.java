package com.codeapes.checklist.web.util;

import java.util.List;
import java.util.Map;

public class TestClass {

    private Long objectKey;
    private Map<String, String> mapOfStrings;
    private String name;
    private String description;
    private int anInteger;
    private List<String> listOfStrings;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public int getAnInteger() {
        return anInteger;
    }

    public List<String> getListOfStrings() {
        return listOfStrings;
    }
    
    public void setAnInteger(int anInteger) {
        this.anInteger = anInteger;
    }

    public void setListOfStrings(List<String> listOfStrings) {
        this.listOfStrings = listOfStrings;
    }

    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public Map<String, String> getMapOfStrings() {
        return mapOfStrings;
    }

    public void setMapOfStrings(Map<String, String> mapOfStrings) {
        this.mapOfStrings = mapOfStrings;
    }

}
