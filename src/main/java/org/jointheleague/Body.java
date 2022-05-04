package org.jointheleague;

public class Body {
    private int x;
    private int y;

    public Body(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Body b = (Body) o;

        if (x != b.x) return false;
        if (y != b.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 11 * result + y;
        return result;
    }
}