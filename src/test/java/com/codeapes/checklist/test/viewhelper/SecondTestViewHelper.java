package com.codeapes.checklist.test.viewhelper;

import com.codeapes.checklist.web.viewhelper.util.AbstractViewHelper;

public class SecondTestViewHelper extends AbstractViewHelper {

    private Long objectKey;
    private String name;
    private String anObject;
    private double extra;
    private int age;

    public Long getObjectKey() {
        return objectKey;
    }

    public String getName() {
        return name;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
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

    public void setAnObject(String anObject) {
        this.anObject = anObject;
    }

    public String getAnObject() {
        return anObject;
    }

    public double getExtra() {
        return extra;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }

}
