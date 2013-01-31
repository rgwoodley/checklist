package com.codeapes.checklist.web.util;

import java.util.List;

import com.codeapes.checklist.web.util.viewhelper.AbstractViewHelper;
import com.codeapes.checklist.web.util.viewhelper.annotations.MappedProperty;
import com.codeapes.checklist.web.util.viewhelper.annotations.MappedViewHelper;

@MappedViewHelper
public class TestViewHelper extends AbstractViewHelper<TestClass> {

    private String name;
    private String description;
    private int anInteger;
    private List<String> listOfStrings;
    
    public TestViewHelper(TestClass inputObject) {
        super(inputObject);
    }

    @MappedProperty (fieldName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MappedProperty (fieldName = "description")
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
