import processing.core.*;

import java.util.ArrayList;

public class Player extends PApplet{
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
        game.addNewPlayer(this);
    }

    public void setTank(Tank t){
        tank = t;
    }

    public void settings(){
        size(ScreenConfig.SCREENX,ScreenConfig.SCREENY);
    }

    public void setup(){
        fill(120,50,240);
        rectMode(CENTER);
    }

    @Override
    public void draw(){
        game.update();
        background(0,0,0);
        game.draw(this);
        update();
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

    boolean fired;
    private void update(){
        if(!keyInputs[6]){
            fired = false;
        }
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
        if (keyInputs[6] && !fired){
            game.spawnBullet(tank.fire());
            fired = true;
        }
    }

    public Tank getTank(){
        return tank;
    }

}
