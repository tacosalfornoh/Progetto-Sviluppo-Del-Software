package org.unibo.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import static org.unibo.Game.*;

import org.unibo.gameStates.GameState;
import org.unibo.gameStates.Playing;

public class GameOverOverlay {

    private Playing playing;
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color (0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString("Game Over", GAME_WIDTH / 2, 150);
        g.drawString("Press Enter to enter the Menu", GAME_WIDTH / 2, 300);
    }
}
