package me.blogof.android.app;

import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by dzq on 16/1/12.
 */
public class LinePoint {
    public float value;
    public String  title;

    private Path path;
    private Point point;
    private float x;
    private float y;

    public LinePoint(float value, String title) {
        this.value = value;
        this.title = title;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setXY(float x,float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
