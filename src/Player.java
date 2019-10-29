import processing.core.*;

import java.util.ArrayList;

public class Player extends PApplet implements Drawable{
    Tank tank;
    GameLobby game;
    //W = 0, S = 1, A =2, D =3
    //J = 4, K = 5 ' ' = 6
    boolean[] keyInputs = {false,false,false,false,false,false,false};

    public static void main(String[] args){
        PApplet.main("Player");
    }

    public Player(){
        game = new GameLobby();
        tank = new Tank(100,100,0);
        game.addNewPlayer(this,tank);
    }



    public Player(float x, float y, int deg){
        keyInputs = new boolean[7];
        tank = new Tank(x,y,deg);
    }

    public void settings(){
        size(ScreenConfig.SCREENX,ScreenConfig.SCREENY);
    }

    public void setup(){
        fill(120,50,240);
        rectMode(CENTER);
        game = new GameLobby();
    }

    @Override
    public void draw(){
        game.update();
        background(0,0,0);
        game.draw(this);
        draw(this);
    }

    public void draw(PApplet applet){
        update();
        tank.draw(applet);
    }

    @Override
    public void keyPressed(){
        controlPressed(key);
    }

    @Override
    public void keyReleased(){
        controlReleased(key);
    }

    public void controlPressed(char key){
        keyChange(true,key);
    }


    public void controlReleased(char key){
        keyChange(false,key);
    }

    private void keyChange(boolean pressed, char key){
        switch (key){
            case 'w':
                keyInputs[0] = pressed;
                break;
            case 's':
                keyInputs[1] = pressed;
                break;
            case 'a':
                keyInputs[2] = pressed;
                break;
            case 'd':
                keyInputs[3] = pressed;
                break;
            case 'j':
                keyInputs[4] = pressed;
                break;
            case 'k':
                keyInputs[5] = pressed;
                break;
            case ' ':
                keyInputs[6] = pressed;
                break;
        }
    }

    private void update(){
        //W
        if(keyInputs[0]){
            tank.forward();
        }
        //S
        if(keyInputs[1]){
            tank.backward();
        }
        //A
        if(keyInputs[2]){
            tank.turnLeft();
        }
        //D
        if(keyInputs[3]){
            tank.turnRight();
        }
        //J
        if(keyInputs[4]){
            tank.turretTurnLeft();
        }
        //K
        if (keyInputs[5]){
            tank.turretTurnRight();
        }
        //' '
        if (keyInputs[6]){
            game.spawnBullet(tank.fire());
        }
    }

    public Tank getTank(){
        return tank;
    }

}
