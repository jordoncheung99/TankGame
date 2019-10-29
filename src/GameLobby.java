import processing.core.PApplet;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class GameLobby implements Runnable{

    ArrayList<Bullet> bullets;
    ArrayList<Obsticle> obsticles;
    Tank[] tanks;
    Player[] players;
    int playerCount = 0;

    public GameLobby(){
        bullets = new ArrayList<Bullet>();
        obsticles = new ArrayList<Obsticle>();
        obsticles.add(new Obsticle(100,100));
        tanks = new Tank[4];
        players = new Player[4];
        tanks[0] = new Tank(100,100,0);
    }

    public GameLobby(Socket s){
        bullets = new ArrayList<Bullet>();
        obsticles = new ArrayList<Obsticle>();
        obsticles.add(new Obsticle(100,100));
        tanks = new Tank[4];
        players = new Player[4];
        tanks[0] = new Tank(100,100,0);
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

    public void draw(PApplet app){
        for (int i =0; i < bullets.size(); i++){
            bullets.get(i).draw(app);
        }
        for (int i = 0; i < obsticles.size(); i++){
            obsticles.get(i).draw(app);
        }
    }

    private void collide(){
        //TODO find a better way of having this linked list
        ArrayList<Collideable> collideables = new ArrayList<Collideable>();
        //Populate the linked list
        //The player's tanks
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
                if (Collideable.isColliding(collideables.get(i),collideables.get(j))){
                    System.out.println("Hit!");
                    collideables.get(i).collide(0);
                    collideables.get(j).collide(0);
                }
            }
        }
    }


    public void spawnBullet(Bullet bullet){
        if(bullet!= null) {
            bullets.add(bullet);
        }
    }

    public void addNewPlayer(Player p, Tank t){
        players[playerCount] = p;
        tanks[playerCount] = t;
        playerCount++;

    }

    @Override
    public void run() {

    }
}
