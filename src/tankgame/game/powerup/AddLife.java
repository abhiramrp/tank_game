package tankgame.game.powerup;

import tankgame.game.Collidible;
import tankgame.game.Powerup;
import tankgame.game.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AddLife extends Powerup {
    public AddLife(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    @Override
    public void doPowerUp(Tank t) {
        t.addLife();
    }

    @Override
    public void setVisible(boolean b) {
        this.visible = b;
    }


}
