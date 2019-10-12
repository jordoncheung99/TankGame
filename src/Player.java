import processing.core.*;

public class Player implements Drawable{
    Tank tank;
    PApplet app;
    Game game;
    //W = 0, S = 1, A =2, D =3
    //J = 4, K = 5 ' ' = 6
    boolean[] keyInputs;
    Player(float x, float y, int deg, PApplet app, Game game){
        keyInputs = new boolean[7];
        this.app = app;
        tank = new Tank(x,y,deg,app);
        this.game = game;
    }

    public void draw(){
        update();
        tank.draw();
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
