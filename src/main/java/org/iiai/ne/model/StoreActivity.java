package org.iiai.ne.model;

public class StoreActivity {
    private int id;

    private int type;

    private String text;

    private int satisfyValue;

    private int returnValue;

    public StoreActivity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSatisfyValue() {
        return satisfyValue;
    }

    public void setSatisfyValue(int satisfyValue) {
        this.satisfyValue = satisfyValue;
    }

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }
}
