package me.blogof.android.app;

/**
 * Created by dzq on 16/1/16.
 */
public class Pie {

    private float value;
    private float currentValue;
    public String color;

    public Pie(float value, String color) {
        this.value = value;
        this.color = color;

    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }
}
