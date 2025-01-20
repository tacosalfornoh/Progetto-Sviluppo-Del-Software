package org.unibo.entities;

import static org.unibo.utils.Constants.EnemyConstants.*;
import static org.unibo.utils.Constants.Directions.*;
import static org.unibo.Game.*;
import static org.unibo.utils.HelpMethods.*;

import java.awt.geom.Rectangle2D;

public abstract class Enemy extends Entity {
    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean isInAir = false;
    protected float fallSpeed;
    protected float gravity = 0.04f * SCALE;
    protected float walkSpeed = 0.35f * SCALE;
    protected int walkDirection = LEFT;
    protected int tileY;
    protected float attackDistance = TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean alive = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;

        initHitBox(x, y, width, height);

        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isAlive() {
        return alive;
    }

    protected void changeWalkDirection() {
        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick > animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
                switch (enemyState) {
                    case ATTACKING, HIT -> enemyState = IDLE;
                    case DEAD -> alive = false;
                }
            }
        }
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if (firstUpdate) {
            if (!isOnGround(hitBox, levelData)) {
                isInAir = true;
            }
            firstUpdate = false;
        }
    }

    protected void updateInAir(int[][] levelData) {
        if (CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            isInAir = false;
            hitBox.y = GetEntityYPosition(hitBox, fallSpeed);
            tileY = (int) (hitBox.y / TILES_SIZE);
        }
    }

    protected void move(int[][] levelData) {
        float xStep = 0;
        if (walkDirection == LEFT) {
            xStep = -walkSpeed;
        } else {
            xStep = walkSpeed;
        }
        if (CanMoveHere(hitBox.x + xStep, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            if (isFloor(hitBox, xStep, levelData)) {
                hitBox.x += xStep;
                return;
            }
        }
        changeWalkDirection();
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            currentHealth = 0;
            newState(DEAD);
        } else {
            newState(HIT);
        }
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.getHitBox())) {
            player.changeHealth(-GetEnemyDamage(enemyType)); // diminuisce la salute del player
        }
        attackChecked = true;
    }

    protected boolean isPlayerInRange(Player player) {
        int absoluteValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absoluteValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absoluteValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absoluteValue <= attackDistance;
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getHitBox().y / TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) { // the player is in the same row
                if (IsSightClear(levelData, hitBox, player.hitBox, tileY)) { // there's nothing between the enemy and
                                                                             // the player
                    return true;
                }
            }
        }
        return false;
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitBox.x > hitBox.x) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    public void resetEnemy() {
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        alive = true;
        fallSpeed = 0;
    }
}