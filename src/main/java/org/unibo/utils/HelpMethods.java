package org.unibo.utils;

import static org.unibo.Game.*;

import java.awt.geom.Rectangle2D;

import org.unibo.Game;

public class HelpMethods {
        public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
            int maxWidth = levelData[0].length * TILES_SIZE;
            if (x < 0 || x + width >= maxWidth) {
                return false;
            }
            if (y < 0 || y + height >= GAME_HEIGHT) {
                return false;
            }
            if (!isSolid(x, y, levelData) && !isSolid(x + width, y, levelData) &&
                !isSolid(x, y + height, levelData) && !isSolid(x + width, y + height, levelData)) {
                return true;
            }
            return false;
        }
    
        private static boolean isSolid(float x, float y, int[][] levelData) {
            int maxWidth = levelData[0].length * TILES_SIZE; 
            if (x < 0 || x >= maxWidth) {
                return true;
            }
            if (y < 0 || y >= GAME_HEIGHT) {
                return true;
            }
            float xIndex = x / TILES_SIZE;
            float yIndex = y / TILES_SIZE;

            return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }
    

    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelData) {
		int value = levelData[yTile][xTile];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}

    public static boolean isFloor(Rectangle2D.Float hitBox, float xStep, int[][] levelData) {
        return isSolid(hitBox.x + xStep, hitBox.y + hitBox.height + 1, levelData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, levelData)) {
				return false;
            }
			if (!IsTileSolid(xStart + i, y + 1, levelData)) {
				return false;
            }
		}
		return true;
	}

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / TILES_SIZE);

		if (firstXTile > secondXTile) { 
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, levelData);
        }
		else {
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, levelData);
        }
	}

    // * Get Entity X Position If Next To The Wall
    public static float GetEntityXPosition(Rectangle2D.Float hitBox, float xStep) {
        int currentTile = (int) hitBox.x / TILES_SIZE;

        if (xStep > 0) {
            // right
            int tileXPos = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else {
            // left
            return currentTile * TILES_SIZE;
        }

    }

    // * Get Entity Y Position If Under The Roof or Abouve The Ground
    public static float GetEntityYPosition(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) hitBox.y / TILES_SIZE;

        if (airSpeed > 0) {
            // falling and after touching floor
            int tileYPos = currentTile * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else {
            // jumping
            return currentTile * TILES_SIZE;
        }
    }

    public static boolean isOnGround(Rectangle2D.Float hitBox, int[][] levelData) {
        if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData)) {
            if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData)) {
                return false;
            }
        }
        return true;
    }
}
