package org.unibo.entities;

import static org.unibo.Game.SCALE;
import static org.unibo.utils.Constants.Directions.*;

import static org.unibo.utils.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Crabby extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitBox(x, y, 22 * (int) SCALE, 19 * (int) SCALE);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, 82 * (int) SCALE, 19 * (int) SCALE); // 30 + 30 + 22 = 82
        attackBoxOffsetX = 30 * (int) SCALE;
    }

    private void updateMove(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData); // function in the Enemy class
        }
        if (isInAir) {
            updateInAir(levelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                    }
                    if (isPlayerCloseForAttack(player)) {
                        newState(ATTACKING);
                    }
                    move(levelData);
                    break;
                case ATTACKING:
                    if (animationIndex == 0) {
                        attackChecked = false;
                    }
                    if (animationIndex == 3 && !attackChecked) {
                        checkPlayerHit(attackBox, player);
                    }
                case HIT:
                    break;
            }
        }
    }

    public int flipX() {
        if (walkDirection == RIGHT) {
            return width;
        } else {
            return 0;
        }
    }

    public int flipW() {
        if (walkDirection == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;
    }

    public void drawAttackBox(Graphics g, int xOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackBox.x - xOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);

    }

    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }
}
