package Ejemplo;

public class Position extends Dao {
    public int id;
    public String name;
    public float xposition;
    public float yposition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getXposition() {
        return xposition;
    }

    public void setXposition(float xposition) {
        this.xposition = xposition;
    }

    public float getYposition() {
        return yposition;
    }

    public void setYposition(float yposition) {
        this.yposition = yposition;
    }
}
