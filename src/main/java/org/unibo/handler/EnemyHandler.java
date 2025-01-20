package org.unibo.handler;

import static org.unibo.utils.LoadSave.ENEMY_ATLAS;
import static org.unibo.utils.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.unibo.entities.Crabby;
import org.unibo.entities.Player;
import org.unibo.gameStates.Playing;
import org.unibo.utils.LoadSave;

public class EnemyHandler {

    private Playing playing;
    private BufferedImage[][] enemyArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyHandler(Playing playing) {
        this.playing = playing;

        loadEnemyImages();
        spawnCrabbies();
    }

    private void spawnCrabbies() {
        crabbies = LoadSave.getCrabbies();
        System.out.println("Crabbies: " + crabbies.size());
    }

    private void loadEnemyImages() {
        enemyArray = new BufferedImage[5][9];
        BufferedImage image = LoadSave.GetSpriteAtlas(ENEMY_ATLAS);
        for (int r = 0; r < enemyArray.length; r++) {
            for (int c = 0; c < enemyArray[r].length; c++) {
                enemyArray[r][c] = image.getSubimage(c * CRABBY_DEFAULT_WIDTH, r * CRABBY_DEFAULT_HEIGHT,
                        CRABBY_DEFAULT_WIDTH, CRABBY_DEFAULT_HEIGHT);
            }
        }
    }

    private void drawCrabs(Graphics g, int xLevelOffSet) {
        for (Crabby c : crabbies) {
            if (c.isAlive()) {
                g.drawImage(enemyArray[c.getEnemyState()][c.getAnimationIndex()],
                        (int) c.getHitBox().x - xLevelOffSet - CRABBY_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitBox().y - CRABBY_DRAWOFFSET_Y,
                        CRABBY_WIDTH * c.flipW(),
                        CRABBY_HEIGHT,
                        null);
                //c.drawAttackBox(g, xLevelOffSet);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies) {
            if (c.getHitBox().intersects(attackBox)) {
                c.takeDamage(10);
            }
        }
    }

    public void resetAllEnemies () {
        for (Crabby c : crabbies) {
            c.resetEnemy();
        }
    }

    public void update(int[][] levelData, Player player) {
        for (Crabby c : crabbies) {
            if (c.isAlive())
                c.update(levelData, player);
        }
    }

    public void render(Graphics g, int xLevelOffSet) {
        drawCrabs(g, xLevelOffSet);
    }
}