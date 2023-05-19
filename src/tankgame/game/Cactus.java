package tankgame.game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Cactus extends Wall {
    public Cactus(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    public void setVisible(boolean b) {
        this.visible = b;
    }

}
