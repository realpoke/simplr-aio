package utils;

import java.awt.*;

public class MousePointPath extends Point {

    private final long finishTime;

    public MousePointPath(int x, int y, int lastingTime){
        super(x,y);
        finishTime= System.currentTimeMillis() + lastingTime;
    }

    public boolean isUp(){
        return System.currentTimeMillis() > finishTime;
    }
}