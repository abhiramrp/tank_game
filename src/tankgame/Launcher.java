package tankgame;

import tankgame.menus.*;
import tankgame.game.GameWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Launcher {

    private JPanel mainPanel;

    private JPanel startPanel;

    private GameWorld gamePanel;

    private JPanel endPanel;

    private JFrame jf;

    private CardLayout cl;

    // default p1 is winner
    private boolean isWinner;

    public boolean getWinner() {
        if(this.isWinner) {
            System.out.println("LF again p1 win");
        } else {
            System.out.println("LF again p2 win");
        }
        return this.isWinner;
    }

    public void setWinner(Boolean w) {
        if(w) {
            System.out.println("LF p1 win");
        } else {
            System.out.println("LF p2 win");
        }

        this.isWinner = w;

        this.endPanel = new EndGamePanel(this);
        this.mainPanel.add(endPanel, "end");
    }

    public Launcher() {
        this.jf = new JFrame();
        this.jf.setTitle("Desert Tank Wars");
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.isWinner = true;
    }

    private void initUIComponents(){
        this.mainPanel = new JPanel();
        this.startPanel = new StartMenuPanel(this);
        this.gamePanel = new GameWorld(this);
        this.gamePanel.InitializeGame();


        this.cl = new CardLayout();
        this.mainPanel.setLayout(cl);


        this.mainPanel.add(startPanel, "start");
        this.mainPanel.add(gamePanel, "game");

        this.jf.add(mainPanel);
        this.jf.setResizable(false);
        this.setFrame("start");
    }

    public void setFrame(String type){
        this.jf.setVisible(false);
        switch(type){
            case "start":
                this.jf.setSize(GameConstants.START_MENU_SCREEN_WIDTH,GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "game":

                this.jf.setSize(GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
                (new Thread(this.gamePanel)).start();
                break;
            case "end":
                this.jf.setSize(GameConstants.END_MENU_SCREEN_WIDTH,GameConstants.END_MENU_SCREEN_HEIGHT);
                break;
        }
        this.cl.show(mainPanel, type);
        this.jf.setVisible(true);
    }

    public JFrame getJf() {
        return jf;
    }

    public void closeGame(){
        this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
    }

    public static void main(String[] args) {
        (new Launcher()).initUIComponents();
    }

}
