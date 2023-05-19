package tankgame.game;

import java.awt.*;

public interface Collidible {
    Rectangle getHitBox();
    void handleCollision(Collidible with);
    boolean isVisible();

}
