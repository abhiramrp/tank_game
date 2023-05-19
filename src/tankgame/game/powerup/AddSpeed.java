package tankgame.game.powerup;

import tankgame.game.Powerup;
import tankgame.game.Tank;

import java.awt.image.BufferedImage;

public class AddSpeed extends Powerup {
    public AddSpeed(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    @Override
    public void doPowerUp(Tank t) {
        t.addSpeed();
    }
}
