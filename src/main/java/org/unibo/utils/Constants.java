package org.unibo.utils;

import static org.unibo.Game.SCALE;

public class Constants {

    public static class Environment {
        public static final int BIG_CLOUDS_DEFAULT_WIDTH = 448;
        public static final int BIG_CLOUDS_DEFAULT_HEIGHT = 101;
        public static final int SMALL_CLOUDS_DEFAULT_WIDTH = 74;
        public static final int SMALL_CLOUDS_DEFAULT_HEIGHT = 24;

        public static final int BIG_CLOUDS_WIDTH = (int) (BIG_CLOUDS_DEFAULT_WIDTH * SCALE);
        public static final int BIG_CLOUDS_HEIGHT = (int) (BIG_CLOUDS_DEFAULT_HEIGHT * SCALE);
        public static final int SMALL_CLOUDS_WIDTH = (int) (SMALL_CLOUDS_DEFAULT_WIDTH * SCALE);
        public static final int SMALL_CLOUDS_HEIGHT = (int) (SMALL_CLOUDS_DEFAULT_HEIGHT * SCALE);

    }

    public static class EnemyConstants {
        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACKING = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_DEFAULT_WIDTH = 72;
        public static final int CRABBY_DEFAULT_HEIGHT = 32;

        public static final int CRABBY_WIDTH = (int) (CRABBY_DEFAULT_WIDTH * SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_DEFAULT_HEIGHT * SCALE);

        public static final int CRABBY_DRAWOFFSET_X = 26 * (int) SCALE;
        public static final int CRABBY_DRAWOFFSET_Y = 9 * (int) SCALE;

        public static final int GetSpriteAmount(int enemyType, int enemyState) {
            switch (enemyType) {
                case CRABBY:
                    switch (enemyState) {
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case ATTACKING:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                        default:
                            return 1;
                    }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemyType) {
            switch (enemyType) {
                case CRABBY:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDamage(int enemyType) {
            switch (enemyType) {
                case CRABBY:
                    return 15;
                default:
                    return 0;
            }
        }
    }

    public static class UI {
        public static class Buttons {
            public static final int BTN_DEFAULT_WIDTH = 140;
            public static final int BTN_DEFAULT_HEIGHT = 56;
            public static final int BTN_WIDTH = (int) (BTN_DEFAULT_WIDTH * SCALE);
            public static final int BTN_HEIGHT = (int) (BTN_DEFAULT_HEIGHT * SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_DEFAULT_SIZE = 42;
            public static final int SOUND_SIZE = (int) (SOUND_DEFAULT_SIZE * SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * SCALE);
        }
    }

    public static class Directions {
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }

    public static class PlayerConstats {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int FALLING = 3;
        public static final int ATTACKING = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case DEAD:
                    return 8;
                case RUNNING:
                    return 5;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMPING:
                case ATTACKING:
                    return 3;
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}
