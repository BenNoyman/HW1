package com.example.hw1.Logic;

public class GameManager {

    private final int TOTAL_ROWS = 5;
    private final int TOTAL_COLS = 3;
    private final int [][] currentObstacles = new int[TOTAL_ROWS][TOTAL_COLS];
    private int crash =0;
    private int lane = (int) (Math.random() * TOTAL_COLS); // Randomly choose a lane
    private int circle =0;
    private int [] currentPlane;
    private int life;


    public GameManager(){
        this(3);
    }
    public GameManager(int life) {
        currentPlane = new int[3];
        currentPlane[1] = 1 ;

        for (int i = 0; i < TOTAL_ROWS; i++) {
            for (int j = 0; j < TOTAL_COLS; j++) {
                //main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
                currentObstacles[i][j] =0;
            }
        }
        this.life = life;
    }

    public void updateObstacleUI() {

        for (int i = 0; i < currentObstacles[0].length; i++) {
            if (currentObstacles[currentObstacles.length - 1][i] == 1) {
                currentObstacles[currentObstacles.length - 1][i] = 0;
            }
        }

        for (int i = currentObstacles.length - 1; i > 0; i--) {
            for (int j = 0; j < currentObstacles[0].length; j++) {
                if (currentObstacles[i - 1][j] == 1) {
                    currentObstacles[i - 1][j] = 0;
                    currentObstacles[i][j] = 1;
                }
            }
        }

        if (circle % 2 == 0) {
            int lane = (int) (Math.random() * TOTAL_COLS);
            currentObstacles[0][lane] = 1;
        }
        circle++;

        for (int i = 0; i < currentObstacles[0].length; i++) {
            if (currentObstacles[currentObstacles.length - 1][i] == 1 && currentPlane[i] == 1) {
                crash++;
            }
        }
    }


    public int getCrash() {
        return crash;
    }

    public int[][] getCurrentObstacles() {
        return currentObstacles;
    }

    public int[] getCurrentPlane() {
        return currentPlane;
    }
}
