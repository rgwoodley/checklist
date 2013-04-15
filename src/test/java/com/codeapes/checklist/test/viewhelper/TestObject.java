package com.codeapes.checklist.test.viewhelper;

public class TestObject {

    private Long objectKey;
    private String name;
    private int age;
    private String notInViewHelper;
    private Object anObject;
    private String testData;
    private String aComment;
    private ThirdTestObject thirdTestObject;

    public void setNotInViewHelper(String notInViewHelper) {
        this.notInViewHelper = notInViewHelper;
    }

    public void setAnObject(Object theObject) {
        this.anObject = theObject;
    }

    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getNotInViewHelper() {
        return notInViewHelper;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Object getAnObject() {
        return anObject;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getaComment() {
        return aComment;
    }

    public void setaComment(String aComment) {
        this.aComment = aComment;
    }

    public ThirdTestObject getThirdTestObject() {
        return thirdTestObject;
    }

    public void setThirdTestObject(ThirdTestObject thirdTestObject) {
        this.thirdTestObject = thirdTestObject;
    }

}
