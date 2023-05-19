package tankgame.game.powerup;

import tankgame.game.Powerup;
import tankgame.game.Tank;

import java.awt.image.BufferedImage;

public class ResetHealth extends Powerup {
    public ResetHealth(float y, float x, BufferedImage img) {
        super(y, x, img);
    }

    @Override
    public void doPowerUp(Tank t) {
        t.resetHealth();
    }

    @Override
    public void setVisible(boolean b) {
        this.visible = b;
    }


}
