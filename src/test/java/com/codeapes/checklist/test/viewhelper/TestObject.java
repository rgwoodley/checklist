package com.codeapes.checklist.test.viewhelper;

public class TestObject {

    private Long objectKey;
    private String name;
    private int age;
    private Object anObject;
    private String notInViewHelper;

    public String getNotInViewHelper() {
        return notInViewHelper;
    }

    public void setNotInViewHelper(String notInViewHelper) {
        this.notInViewHelper = notInViewHelper;
    }
    
    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Object getAnObject() {
        return anObject;
    }

    public void setAnObject(Object anObject) {
        this.anObject = anObject;
    }

}
