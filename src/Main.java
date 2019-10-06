import processing.core.PApplet;

public class Main extends PApplet {

    Tank tank;
    Player player;
    public static void main(String[] args){
        PApplet.main("Main");
    }

    public void settings(){
        size(1000,1000);
    }

    public void setup(){
        fill(120,50,240);
        rectMode(CENTER);
        player = new Player(150,150,0,this);
        //tank = new Tank(150,150,0,this);
    }

    public void draw(){
        background(0,0,0);
        player.draw();
    }

    @Override
    public void keyPressed(){
        //Controls goes here
        player.controlPressed(key);
    }

    @Override
    public void keyReleased(){
        player.controlReleased(key);
    }
}
