package tankgame.game;

import tankgame.Launcher;
import tankgame.GameConstants;
import tankgame.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;

    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;

    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                this.tick++;

                this.t1.update();
                this.t2.update();

                this.repaint();

                Thread.sleep( 1000 / 144);


                /**
                if (this.tick >= 144 * 8) {
                    this.lf.setFrame("end");
                    return;
                }
                 */
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }

    }

    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(500);
        this.t2.setY(500);
    }

    public void InitializeGame() {

        Resources.initImages();

        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);


        t1 = new Tank(300, 300, 0, 0, (short) 0, Resources.getImage("tank1img"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(500, 500, 0, 0, (short) 0, Resources.getImage("tank2img"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_BACK_SPACE);
        this.lf.getJf().addKeyListener(tc2);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        // this.world.getSubimage();

        g2.drawImage(world, 0, 0, null);
    }
}
