import processing.core.*;

import javax.swing.*;
import java.util.ArrayList;

public class Player extends PApplet{
    Tank tank;
    GameLobby game;
    //W = 0, S = 1, A =2, D =3
    //J = 4, K = 5 ' ' = 6 T = 7
    boolean[] keyInputs = {false,false,false,false,false,false,false,false};

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
        textSize(25);
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
            case 't':
                keyInputs[7] = pressed;
                break;
        }
    }

    boolean fired;
    boolean talked;
    private void update(){
        if(!keyInputs[7]){
            talked = false;
        }
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

        if(keyInputs[7] && !talked){
            talk();
            talked = true;
        }
    }

    public Tank getTank(){
        return tank;
    }

    public void talk(){
        System.out.println("talking");
        //JOptionPane.showMessageDialog(null, "java is fun");
        String s = JOptionPane.showInputDialog("What is your message");
        if(s.contains("/ban")){
            String bannedUser = s.replace("/ban","");
            System.out.println("User banned");
        }else{
            game.addText(s,tank.getOrigin());
        }

    }


}
