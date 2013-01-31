package com.codeapes.checklist.web.util;

import java.util.List;

import com.codeapes.checklist.web.util.viewhelper.ViewHelper;

public class TestViewHelper implements ViewHelper {

    private String name;
    private String description;
    private int anInteger;
    private List<String> listOfStrings;
    
    public void populate(Object object) {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAnInteger() {
        return anInteger;
    }

    public void setAnInteger(int anInteger) {
        this.anInteger = anInteger;
    }

    public List<String> getListOfStrings() {
        return listOfStrings;
    }

    public void setListOfStrings(List<String> listOfStrings) {
        this.listOfStrings = listOfStrings;
    }

}
