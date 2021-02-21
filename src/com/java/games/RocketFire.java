package com.java.games;

import com.javarush.engine.cell.Game;

import java.util.List;

public class RocketFire extends GameObject{

    private List<int[][]> frames;
    private int frameIndex;

    private boolean isVisible;

    public RocketFire(List<int[][]> frameList){
        super(0, 0, frameList.get(0));
        frames = frameList;
        frameIndex = 0;
        isVisible = false;
    }

    private void nextFrame(){
        frameIndex++;
        frameIndex = frameIndex >= frames.size() ? 0 : frameIndex;
        matrix = frames.get(frameIndex);
    }

    @Override
    public void draw(Game game) {
        if(!isVisible)
            return;
        nextFrame();
        super.draw(game);
    }

    public void show(){
        isVisible = true;
    }

    public void hide(){
        isVisible = false;
    }
}
