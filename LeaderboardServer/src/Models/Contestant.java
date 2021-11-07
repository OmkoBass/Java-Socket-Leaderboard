package Models;

import java.io.Serializable;

public class Contestant implements Serializable {
    public final String name;
    public final int score;

    public Contestant(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + " " + score;
    }
}
