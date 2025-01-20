package org.unibo.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.unibo.utils.LoadSave;

import static org.unibo.utils.Constants.UI.URMButtons.*;
import static org.unibo.utils.LoadSave.*;

public class UrmButton extends PauseButton {
    private BufferedImage[] images;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage image = LoadSave.GetSpriteAtlas(URM_BUTTON);
        images = new BufferedImage[3];
        for (int c = 0; c < images.length; c++) {
            images[c] = image.getSubimage(c * URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE,
                    URM_DEFAULT_SIZE);
        }
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
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

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public void render(Graphics g) {
        g.drawImage(images[index], x, y, URM_SIZE, URM_SIZE, null);
    }
}
