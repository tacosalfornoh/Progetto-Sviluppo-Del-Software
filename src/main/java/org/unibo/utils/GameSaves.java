package org.unibo.utils;

import static org.unibo.handler.FileHandler.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.unibo.entities.Player;

public class GameSaves {
    public static final String FOLDER = "saves/";
    private static float x;
    private static float y;

    public GameSaves() {
        if (!existsFolder(FOLDER)) {
            createFolder("", "saves");
        }
        if (isSavesFolderEmpty()) {
            initGameSaves();
        } else {
            loadLatestSave();
        }
    }

    private void initGameSaves() {
        String date_time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = "save_" + date_time + ".yaml";
        String date = "created_at: " + date_time + "\n";
        String player = "player: \n name: \"Player\"\n health: 100\n position:\n  x: 150,00\n  y: 150,00\n";
        String enemies = "enemies: []\n";
        String content = date + player + enemies;
        writeFile(FOLDER + fileName, content);
    }

    public static void saveGame(Player player) {
        String date_time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = "save_" + date_time + ".yaml";
        String date = "created_at: " + date_time + "\n";
        String playerData = String.format(
                "player: \n name: \"Player\"" +
                        "\n health: %d" +
                        "\n position:\n  x: %.2f\n  y: %.2f\n",
                player.getHealth(), player.getHitBox().x, player.getHitBox().y);
        String enemies = "enemies: []\n";
        String content = date + playerData + enemies;
        writeFile(FOLDER + fileName, content);
    }

    public void loadGame(String fileName) {
        String content = readFile(FOLDER + fileName);
        System.out.println(content);
    }

    public void getLatestSave() {
        List<String> files = getFiles(FOLDER);
        files.forEach(System.out::println);
    }

    public static boolean isSavesFolderEmpty() {
        return getFiles(FOLDER).isEmpty();
    }

    private void loadLatestSave() {
        List<String> files = getFiles(FOLDER);
        if (files.isEmpty()) {
            System.err.println("No save files found.");
            return;
        }
        String latestSave = files.get(files.size() - 1);
        System.out.println("Latest save file: " + latestSave);
        if (latestSave == null) {
            System.err.println("Latest save file is null.");
            return;
        }
        String content = readFile(latestSave);
        if (content == null) {
            System.err.println("Failed to read the latest save file.");
            return;
        }
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.contains("x:")) {
                // * Replace comma with dot for float parsing
                x = Float.parseFloat(line.split(":")[1].trim().replace(",", "."));
                System.out.println("x: " + x);
            } else if (line.contains("y:")) {
                y = Float.parseFloat(line.split(":")[1].trim().replace(",", "."));
                System.out.println("y: " + y);
            }
        }
    }

    public static float getX() {
        return x;
    }

    public static float getY() {
        return y;
    }
}

/* TODO:
 * add Spawn Position to the save file
 * 
 * spawn:
 * x: 150.00
 * y: 150.00
 */

/*
 * saves.yaml
 * 
 * created_at: 2021-09-30_12-00-00
 * player:
 * name: "Player"
 * health: 100
 * spawn:
 * x: 150.00
 * y: 150.00
 * position:
 * x: 150.00
 * y: 150.00
 * enemies: []
 */