package com.java.games;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

public class GameObject {

    public double x;
    public double y;

    public int width;
    public int height;
    public int [][] matrix;

    public GameObject(double x, double y, int [][] matrix){
        this.x = x;
        this.y = y;

        this.matrix = matrix;
        this.width = matrix[0].length;
        this.height = matrix.length;
    }

    public void draw(Game game){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int colorIndex = matrix[j][i];
                game.setCellColor((int) x + i, (int) y + j, Color.values()[colorIndex]);
            }
        }
    }
}
