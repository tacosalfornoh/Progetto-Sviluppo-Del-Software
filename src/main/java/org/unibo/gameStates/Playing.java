package org.unibo.gameStates;

import static org.unibo.Game.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.unibo.Game;
import org.unibo.entities.Player;
import org.unibo.handler.EnemyHandler;
import org.unibo.handler.LevelHandler;
import org.unibo.ui.GameOverOverlay;
import org.unibo.ui.PausedOverlay;
import org.unibo.utils.GameSaves;
import org.unibo.utils.LoadSave;
import org.unibo.ui.GameOverOverlay;

import static org.unibo.utils.LoadSave.*;
import static org.unibo.utils.Constants.Environment.*;

public class Playing extends State implements StateMethods {
    private Player player;
    private LevelHandler levelHandler;
    private EnemyHandler enemyHandler;
    private PausedOverlay pausedOverlay;
    private GameOverOverlay gameOverOverlay;

    private boolean paused = false;

    private int xLevelOffSet;
    private int leftBorder = (int) (0.2 * GAME_WIDTH);
    private int rightBorder = (int) (0.8 * GAME_WIDTH);
    private int levelTileWide = LoadSave.getFullLevelWitdh();
    private int maxTilesOffset = levelTileWide - TILES_WIDTH;
    private int maxLevelOffset = maxTilesOffset * TILES_SIZE;

    private BufferedImage backgroungPlayingImage, bigClouds, smallClouds;
    private int[] smallCloudsPos;
    private Random rnd = new Random();
    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroungPlayingImage = LoadSave.GetSpriteAtlas(BACKGROUND_PLAYING);
        bigClouds = LoadSave.GetSpriteAtlas(BIG_CLOUDS);
        smallClouds = LoadSave.GetSpriteAtlas(SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++) {
            smallCloudsPos[i] = 70 * (int) SCALE + rnd.nextInt(150 * (int) SCALE);
        }
    }

    private void initClasses() {
        levelHandler = new LevelHandler(game);
        enemyHandler = new EnemyHandler(this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);
        player.loadLevelData(levelHandler.getLevels().getLevelData());
        pausedOverlay = new PausedOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);

        // Spawn the player in the position of the last save
        if (!GameSaves.isSavesFolderEmpty()) {
            player.setPosition(GameSaves.getX(), GameSaves.getY());
        }
    }

    public void pauseGame() {
        this.paused = !paused;
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.setLeft(false);
        player.setRight(false);
        player.setJumping(false);
        player.setDown(false);
        player.setAttacking(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pausedOverlay.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pausedOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pausedOverlay.mouseMoved(e);
            }
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverOverlay.keyPressed(e);
            return;
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setJumping(true);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setAttacking(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    pauseGame();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setJumping(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setAttacking(false);
                    break;
                case KeyEvent.VK_R:
                    GameState.state = GameState.MENU;
                    break;
                default:
                    break;
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pausedOverlay.mouseDragged(e);
            }
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLevelOffSet;

        if (diff > rightBorder) {
            xLevelOffSet += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLevelOffSet += diff - leftBorder;
        }

        int extendedLevelWidth = levelTileWide * TILES_SIZE;
        if (xLevelOffSet > extendedLevelWidth - GAME_WIDTH) {
            xLevelOffSet = extendedLevelWidth - GAME_WIDTH;
        } else if (xLevelOffSet < 0) {
            xLevelOffSet = 0;
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigClouds, (int) ((i * BIG_CLOUDS_WIDTH) - 0.3 * xLevelOffSet), (int) (204 * SCALE),
                    BIG_CLOUDS_WIDTH, BIG_CLOUDS_HEIGHT, null);
            g.drawImage(bigClouds, (int) ((i * BIG_CLOUDS_WIDTH) - 0.3 * xLevelOffSet), (int) (204 * SCALE),
                    BIG_CLOUDS_WIDTH, BIG_CLOUDS_HEIGHT, null);
        }
        for (int i = 0; i < smallCloudsPos.length; i++) {
            g.drawImage(smallClouds, (int) ((SMALL_CLOUDS_WIDTH * 4 * i) - 0.7 * xLevelOffSet), smallCloudsPos[i],
                    SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
            g.drawImage(smallClouds, (int) ((SMALL_CLOUDS_WIDTH * 4 * i) - 0.7 * xLevelOffSet), smallCloudsPos[i],
                    SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
        }
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyHandler.resetAllEnemies();
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyHandler.checkEnemyHit(attackBox);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public void update() {
        if (!paused && !gameOver) {
            levelHandler.update();
            player.update();
            enemyHandler.update(levelHandler.getLevels().getLevelData(), player);
            checkCloseToBorder();
        } else {
            pausedOverlay.update();
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(backgroungPlayingImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        drawClouds(g);

        levelHandler.render(g, xLevelOffSet);
        player.render(g, xLevelOffSet);
        enemyHandler.render(g, xLevelOffSet);
        levelHandler.renderEndLevelMarker(g, xLevelOffSet);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pausedOverlay.render(g);
        } else if (gameOver) {
            gameOverOverlay.render(g);
        } else if (gameOver) {
            gameOverOverlay.render(g);
        }
    }

    public void saveGame() {
        GameSaves.saveGame(player);
    }
}
