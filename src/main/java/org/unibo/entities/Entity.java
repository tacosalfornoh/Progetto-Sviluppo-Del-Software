package org.unibo.entities;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.geom.Rectangle2D;


public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        System.out.println("Entity x: " + x + " y: " + y);
        this.width = width;
        this.height = height;
    }

    protected void initHitBox(float x, float y, int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    // draw the hitbox for debugging
    protected void drawHitBox(Graphics g, int xLevelOffSet) { 
        g.setColor(Color.RED);
        g.drawRect((int) hitBox.x - xLevelOffSet, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }
}
