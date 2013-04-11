package com.codeapes.checklist.test.viewhelper;

import com.codeapes.checklist.web.viewhelper.AbstractViewHelper;
import com.codeapes.checklist.web.viewhelper.annotation.Mapped;

public class TestViewHelper extends AbstractViewHelper {

    private Long objectKey;
    private String name;
    private int age;
    private String anObject;
    private String extra;
    private String testData;
    private String theComment;
    private String fourthName;
    private String mappedButDoesNotExistInTarget;

    public TestViewHelper(Class<?> parameterizedType) {
        super(parameterizedType);
    }

    @Mapped(source = "aComment")
    public String getTheComment() {
        return theComment;
    }

    public void setTheComment(String theComment) {
        this.theComment = theComment;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    @Mapped
    public Long getObjectKey() {
        return objectKey;
    }

    @Mapped
    public String getName() {
        return name;
    }

    public void setAnObject(String anObject) {
        this.anObject = anObject;
    }

    public String getAnObject() {
        return anObject;
    }

    @Mapped
    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Mapped(source = "thirdTestObject.fourthTestObject.name")
    public String getFourthName() {
        return fourthName;
    }

    public void setFourthName(String fourthName) {
        this.fourthName = fourthName;
    }

    @Mapped(source = "doesNotExist.name")
    public String getMappedButDoesNotExistInTarget() {
        return mappedButDoesNotExistInTarget;
    }

    public void setMappedButDoesNotExistInTarget(String mappedButDoesNotExistInTarget) {
        this.mappedButDoesNotExistInTarget = mappedButDoesNotExistInTarget;
    }

}
