package org.unibo.utils;

import static org.unibo.handler.FileHandler.*;
import static org.unibo.utils.LoadSave.*;

import java.util.List;
import java.util.ArrayList;

public class Config {
    private static final String CONFIG_FILE = "config.yaml";

    public Config() {
        if (!existsConfig()) {
            initConfig();
        } else {
            loadConfig();
        }
    }

    private void initConfig() {
        shuffleWagoons();
        StringBuilder levelsBuilder = new StringBuilder("levels: [");
        for (String wagoon : WAGOONS) {
            levelsBuilder.append("\n  ").append(wagoon);
        }
        levelsBuilder.append("\n  ]");

        String content = "options:\n fullscreen: false\n volume: 50\n music: true\n sound: true\n" +
                " difficlity: easy\n" +
                levelsBuilder.toString();
        writeFile(CONFIG_FILE, content);
    }

    private boolean existsConfig() {
        return existsFile(CONFIG_FILE);
    }

    private void loadConfig() {
        String path = "src/main/assets/" + CONFIG_FILE;
        System.out.println("Loading config from: " + path);
        String content = readFile(path);
        if (content != null) {
            System.out.println("Config content: " + content);
            String[] lines = content.split("\n");
            List<String> wagoonsList = new ArrayList<>();
            boolean levelsSection = false;
            for (String line : lines) {
                if (line.trim().startsWith("levels:")) {
                    levelsSection = true;
                } else if (levelsSection) {
                    if (line.trim().equals("]")) {
                        break;
                    }
                    wagoonsList.add(line.trim());
                }
            }
            if (wagoonsList.isEmpty()) {
                System.err.println("No wagoons found in config file.");
            } else {
                WAGOONS = wagoonsList.toArray(new String[0]);
                System.out.println("WAGOONSS: " + wagoonsList);
            }
        } else {
            System.err.println("Failed to read config file.");
        }
    }
}

/* Config.yaml 
 * options:
 *  fullscreen: false
 *  volume: 50
 *  music: true
 *  sound: true
 *  difficlity: easy
 * levels: {
 *  wagoon1: {
 *   name: "wagoon1",
 *   bossAlive: true,
 *   sound: "wagoon1.mp3"
 *   music: "wagoon1.mp3"
 *  }
 * }
*/