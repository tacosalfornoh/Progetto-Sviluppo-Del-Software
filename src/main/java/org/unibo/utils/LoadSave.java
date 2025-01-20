package org.unibo.utils;

import static org.unibo.Game.*;
import static org.unibo.utils.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.unibo.entities.Crabby;

public class LoadSave {
    // * Menu Sprites
    public static final String MENU_BUTTON = "menu/button_atlas.png";
    public static final String MENU_BACKGROUND = "menu/menu_background.png";
    public static final String PAUSE_BACKGROUND = "menu/pause_menu.png";
    public static final String SOUND_BUTTON = "menu/sound_button.png";
    public static final String VOLUME_BUTTON = "menu/volume_buttons.png";
    public static final String URM_BUTTON = "menu/urm_buttons.png"; // URM = unpause, replay, main menu
    public static final String BACKGROUND_MENU = "menu/background_menu.png";

    // * Tiles Sprites
    public static final String TILES_ATLAS = "tiles/tiles.png";

    // * Envirovement Sprites
    public static final String BACKGROUND_PLAYING = "envirovement/playing_bg_img.png";
    public static final String BIG_CLOUDS = "envirovement/big_clouds.png";
    public static final String SMALL_CLOUDS = "envirovement/small_clouds.png";

    // * Entites Sprites
    public static final String PLAYER_ATLAS = "entities/player_sprites.png";
    public static final String ENEMY_ATLAS = "entities/enemy_sprites.png";

    // * UI Sprites
    public static final String STATUS_BAR = "ui/health_power_bar.png";

    // * Level Map Sprites
    public static final String WAGOON_START = "wagoons/1.png";
    public static String[] WAGOONS = {
        "wagoons/2.png",
        "wagoons/3.png",
        "wagoons/4.png",
    };
    public static final String WAGOON_END = "wagoons/5.png";

    public static void shuffleWagoons() {
        // Convert the array to a list
        List<String> wagoonsList = new ArrayList<>(Arrays.asList(WAGOONS));

        // Shuffle the list
        Collections.shuffle(wagoonsList);
        System.out.println("WagoonsList: " + wagoonsList);

        // Add the start and end wagoon to the front and back of the list
        wagoonsList.add(0, WAGOON_START);
        wagoonsList.add(WAGOON_END);

        // Convert the list back to an array
        WAGOONS = wagoonsList.toArray(new String[0]);
        System.out.println("Wagoons: " + Arrays.toString(WAGOONS));
    }

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Image file not found: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static ArrayList<Crabby> getCrabbies() {
        ArrayList<Crabby> crabbies = new ArrayList<>();
        int xOffset = 0;

        for (String wagoon : WAGOONS) {
            BufferedImage image = GetSpriteAtlas(wagoon);
            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();
            for (int r = 0; r < imageHeight; r++) {
                for (int c = 0; c < imageWidth; c++) {
                    Color color = new Color(image.getRGB(c, r));
                    int value = color.getGreen();
                    if (value == CRABBY) {
                        crabbies.add(new Crabby((c + xOffset) * TILES_SIZE, r * TILES_SIZE));
                    }
                }
            }
            xOffset += imageWidth;
        }
        return crabbies;
    }

    public static int[][] GetLevelData(String fileName) {
        BufferedImage image = GetSpriteAtlas(fileName);
        int[][] levelData = new int[image.getHeight()][image.getWidth()];

        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                Color color = new Color(image.getRGB(c, r));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                levelData[r][c] = value;
            }
        }
        return levelData;
    }

    private static int calcFullLevelsWidth() {
        int fullLevelWidth = 0;
        for (int i = 0; i < WAGOONS.length; i++) {
            BufferedImage image = GetSpriteAtlas(WAGOONS[i]);
            fullLevelWidth += image.getWidth();
        }
        return fullLevelWidth;
    }

    public static int getFullLevelWitdh() {
        return calcFullLevelsWidth();
    }
}