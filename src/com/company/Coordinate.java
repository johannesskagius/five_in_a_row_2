//  author josk3261 Johannes Skagius
// Stockholms university
// Kurs: ALDA - algoritmer och datastrukturer

package com.company;

import java.util.Objects;

public class Coordinate{
    private int x;
    private int y;
    public Coordinate (int y,int x) {
        this.x = x;
        this.y = y;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode () {
        return Objects.hash ( x,y );
    }

    @Override
    public String toString () {
        return "x: "+ x +", y: "+y;
    }
}
