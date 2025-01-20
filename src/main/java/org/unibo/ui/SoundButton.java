package org.unibo.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.unibo.utils.LoadSave;

import static org.unibo.utils.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;
    private BufferedImage[][] soundImage;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImage();
    }

    private void loadSoundImage() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTON);
        soundImage = new BufferedImage[2][3];
        for (int r = 0; r < soundImage.length; r++) {
            for (int c = 0; c < soundImage[r].length; c++) {
                soundImage[r][c] = image.getSubimage(c * SOUND_DEFAULT_SIZE, r * SOUND_DEFAULT_SIZE, SOUND_DEFAULT_SIZE,
                     SOUND_DEFAULT_SIZE);
            }
        }
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public void update() {
        if (isMuted()) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        colIndex = 0;
        if (isMouseOver()) {
            colIndex = 1;
        }
        if (isMousePressed()) {
            colIndex = 2;
        }
    }

    public void render(Graphics g) {
        g.drawImage(soundImage[rowIndex][colIndex], x, y, width, height, null);
    }

}
