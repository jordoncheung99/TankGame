import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game extends PApplet {
    //A game has 4 players
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    ArrayList<Bullet> bullets;
    ArrayList<Obsticle> obsticles;

    public static int sizeX = 1000;
    public static int sizeY = 1000;

    public static void main(String[] args){
        PApplet.main("Game");
    }

    public void settings(){
        size(sizeX,sizeY);
    }

    public void setup(){
        fill(120,50,240);
        rectMode(CENTER);
        p1 = new Player(150,150,0,this, this);
        bullets = new ArrayList<Bullet>();
        obsticles = new ArrayList<Obsticle>();
        obsticles.add(new Obsticle(100,100,this));
    }

    @Override
    public void draw(){
        update();
        background(0,0,0);
        p1.draw();
        for (int i =0; i < bullets.size(); i++){
            bullets.get(i).draw();
        }
        for (int i = 0; i < obsticles.size(); i++){
            obsticles.get(i).draw();
        }
    }

    public void update(){
        for (int i =0; i < bullets.size(); i++){
            bullets.get(i).update();
            if(bullets.get(i).destoryFlag){
                bullets.remove(i);
                i--;
            }
        }
        collide();
    }

    private void collide(){
        //TODO find a better way of having this linked list
        ArrayList<Collideable> collideables = new ArrayList<Collideable>();
        //Populate the linked list
        //The player's tanks
        if(p1 != null && p1.getTank()!= null){
            collideables.add(p1.getTank());
        }
        if(p2 != null && p2.getTank()!= null){
            collideables.add(p2.getTank());
        }
        if(p3 != null && p3.getTank()!= null){
            collideables.add(p3.getTank());
        }
        if(p4 != null && p4.getTank()!= null){
            collideables.add(p4.getTank());
        }
        //Add the Obsticles
        for (int i = 0; i < obsticles.size(); i++){
            collideables.add(obsticles.get(i));
        }
        //add bullets
        for (int i = 0; i < bullets.size(); i++){
            collideables.add(bullets.get(i));
        }


        for (int i = 0; i < collideables.size(); i++){
            for (int j = i+1; j < collideables.size(); j++){
                if (checkCollide(collideables.get(i),collideables.get(j))){
                    System.out.println("Hit!");
                    collideables.get(i).collide(0);
                    collideables.get(j).collide(0);
                }
            }
        }
    }

    private boolean checkCollide(Collideable c1, Collideable c2){
        return Collideable.isColliding(c1.getOrigin(),c1.getSize(),c1.getRotation(),c2.getOrigin(),c2.getSize(),c2.getRotation());
    }

    @Override
    public void keyPressed(){
        //Controls goes here
        p1.controlPressed(key);
    }

    @Override
    public void keyReleased(){
        p1.controlReleased(key);
    }

    public void spawnBullet(Bullet bullet){
        if(bullet!= null) {
            bullets.add(bullet);
        }
    }

}
