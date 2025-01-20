package org.unibo.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {
    public static final String FOLDER = "src/main/assets/";

    public static boolean existsFile(String path) {
        path = FOLDER + path;
        if (Files.exists(Paths.get(path))) {
            return true;
        } else {
            return false;
        }
    }

    public static String readFile(String path) {
        System.out.println("Reading file...");
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
            return null;
        }
    }

    public static void writeFile(String path, String content) {
        System.out.println("Writing file...");
        path = FOLDER + path;
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            System.err.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }

    public static List<String> getFiles(String path) {
        path = FOLDER + path;
        try {
            System.out.println("Listing files...");
            List<String> files = Files.list(Paths.get(path))
                                      .map(Path::toString)
                                      .collect(Collectors.toList());
            return files;
        } catch (IOException e) {
            System.err.println("An error occurred while listing the files.");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean existsFolder(String path) {
        path = FOLDER + path;
        if (Files.exists(Paths.get(path))) {
            return true;
        } else {
            return false;
        }
    }

    public static void createFolder(String path, String name) {
        path = FOLDER + path + name;
        try {
            Files.createDirectory(Paths.get(path));
        } catch (IOException e) {
            System.err.println("An error occurred while creating the folder.");
            e.printStackTrace();
        }
    }

    public static String getFolders(String path) {
        path = FOLDER + path;
        try {
            return Files.list(Paths.get(path)).toString();
        } catch (IOException e) {
            System.err.println("An error occurred while listing the folders.");
            e.printStackTrace();
            return null;
        }
    }
}
