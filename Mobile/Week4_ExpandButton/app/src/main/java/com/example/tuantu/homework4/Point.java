package com.example.tuantu.homework4;

/**
 * Created by TUAN TU on 3/18/2016.
 */
public class Point {
    public int x;
    public int y;
    public int Distance(Point b)
    {
        return (int)Math.sqrt(Math.pow(b.x - this.x, 2.0) + Math.pow(b.y - this.y, 2.0));
    }
}
