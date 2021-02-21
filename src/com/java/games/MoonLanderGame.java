package com.java.games;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private int score;

    private Rocket rocket;
    private GameObject landscape;
    private GameObject platform;

    private boolean isUpPressed;
    private boolean isRightPressed;
    private boolean isLeftPressed;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        showGrid(false);
        createGame();
    }

    private void drawScene(){
        for (int y = 0; y < WIDTH; y++) {
            for (int x = 0; x < HEIGHT; x++) {
                setCellColor(x, y, Color.DARKORANGE);
            }
        }

        rocket.draw(this);
        landscape.draw(this);
    }

    private void createGame(){

        score = 1000;

        isGameStopped = false;
        isLeftPressed = false;
        isUpPressed = false;
        isRightPressed = false;

        createGameObjects();
        setTurnTimer(50);
        drawScene();
    }

    private void createGameObjects(){
        rocket = new Rocket(WIDTH/2, 0);
        platform = new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);

        if(score > 0)
            score--;

        check();
        setScore(score);
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if(x >= WIDTH || x < 0 || y >= HEIGHT || y < 0)
            return;

        super.setCellColor(x,y,color);
    }

    @Override
    public void onKeyPress(Key key) {
        if(key.equals(Key.UP))
            isUpPressed = true;
        else if(key.equals(Key.RIGHT)){
            isRightPressed = true;
            isLeftPressed = false;
        }else if(key.equals(Key.LEFT)){
            isLeftPressed = true;
            isRightPressed = false;
        }else if(key.equals(Key.SPACE)){
            if(isGameStopped){
                createGame();
            }
        }
    }


    @Override
    public void onKeyReleased(Key key) {
        if(key.equals(Key.UP))
            isUpPressed = false;
        else if(key.equals(Key.RIGHT))
            isRightPressed = false;
        else if(key.equals(Key.LEFT))
            isLeftPressed = false;

    }

    private void check(){
        if(rocket.isCollision(landscape) && !(rocket.isCollision(platform) && rocket.isStopped()))
            gameOver();
        else if(rocket.isCollision(platform) && rocket.isStopped())
            win();
    }

    private void win(){
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "YOU LAND THE ROCKET", Color.BLACK, 40);
        stopTurnTimer();
    }

    private void gameOver(){
        score = 0;
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "YOU CRASH THE ROCKET", Color.GOLD, 40);
        stopTurnTimer();
    }
}
