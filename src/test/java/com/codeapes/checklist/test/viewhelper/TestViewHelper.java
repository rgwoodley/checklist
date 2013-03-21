package com.codeapes.checklist.test.viewhelper;

import com.codeapes.checklist.web.viewhelper.util.AbstractViewHelper;

public class TestViewHelper extends AbstractViewHelper {

    private Long objectKey;
    private String name;
    private int age;
    private String anObject;
    private String extra;

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public Long getObjectKey() {
        return objectKey;
    }
    
    public String getName() {
        return name;
    }

    public void setAnObject(String anObject) {
        this.anObject = anObject;
    }

    public String getAnObject() {
        return anObject;
    }
    
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

}
