package org.unibo;

import javax.swing.*;

import org.unibo.handler.KeyboardHandler;
import org.unibo.handler.MouseHandler;
import static org.unibo.Game.*;

import java.awt.*;

public class GamePanel extends JPanel {
    private MouseHandler mouseHandler;
    private Game game;

    public GamePanel(Game game) {
        mouseHandler = new MouseHandler(this);
        this.game = game;
        
        setPanelSize();
        addKeyListener(new KeyboardHandler(this));
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("GamePanel size: " + size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }

}
