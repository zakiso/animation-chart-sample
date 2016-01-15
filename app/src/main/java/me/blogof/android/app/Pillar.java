package me.blogof.android.app;

/**
 * Created by dzq on 16/1/12.
 */
public class Pillar {
    public float value;
    public String title;
    public String color;
    public String fillColor;
    public float currentValue;

    public Pillar() {
    }

    /**
     *
     * @param value
     * @param title
     * @param color
     * @param fillColor
     */
    public Pillar(float value, String title, String color, String fillColor) {
        this.value = value;
        this.title = title;
        this.color = color;
        this.fillColor = fillColor;
    }
}
