package org.unibo.test;

import java.awt.Color;
import java.awt.Graphics;

public class Health {
    private static int maxHealth;
        private static int currentHealth;
        
            public Health(int maxHealth) {
                this.maxHealth = maxHealth;
                this.currentHealth = maxHealth;
            }
        
            public static int getCurrentHealth() {
                return currentHealth;
            }
        
            public static void setHealth(int health) {
                if (health >= 0 && health <= maxHealth) {
                currentHealth = health;
            System.out.println("Health: " + currentHealth);
        }
        if (currentHealth == 0) {
            System.out.println("Game Over");
        }

    }

    public void update() {
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        for (int i = 0; i < maxHealth; i++) {
            g.fillRect(32 + i * 48, 10, 32, 32);
        }
        g.setColor(Color.GREEN);
        for (int i = 0; i < currentHealth; i++) {
            g.fillRect(32 + i * 48, 10, 32, 32);
        }
    }
}
