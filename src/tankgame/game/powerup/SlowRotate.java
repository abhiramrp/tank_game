package tankgame.game.powerup;

import tankgame.game.Powerup;
import tankgame.game.Tank;

import java.awt.image.BufferedImage;

public class SlowRotate extends Powerup {
    public SlowRotate(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    @Override
    public void doPowerUp(Tank t) {
        t.slowRotate();

    }

    @Override
    public void setVisible(boolean b) {
        this.visible = b;
    }

}
