package com.codeapes.checklist.web.form;

public class TestForm {

    private String notInTarget;
    private String name;
    private Long objectKey;
    private int age;

    public String getNotInTarget() {
        return notInTarget;
    }

    public void setNotInTarget(String notInTarget) {
        this.notInTarget = notInTarget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getObjectKey() {
        return objectKey;
    }

    public int getAge() {
        return age;
    }
    
    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
