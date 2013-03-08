package com.codeapes.checklist.test.viewhelper;

public class SecondTestObject {

    private Object anObject;
    private String notInViewHelper;
    private Long objectKey;
    private String name;
    private int age;

    public Long getObjectKey() {
        return objectKey;
    }

    public int getAge() {
        return age;
    }
    
    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAnObject() {
        return anObject;
    }

    public void setAnObject(Object anObject) {
        this.anObject = anObject;
    }

    public void setNotInViewHelper(String notInViewHelper) {
        this.notInViewHelper = notInViewHelper;
    }

    public String getNotInViewHelper() {
        return notInViewHelper;
    }

}
