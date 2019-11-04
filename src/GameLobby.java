import processing.core.PApplet;

import java.util.ArrayList;

public class GameLobby implements Runnable{

    ArrayList<Bullet> bullets;
    ArrayList<Obsticle> obsticles;
    Tank[] tanks;
    Player[] players;
    int playerCount = 0;

    public GameLobby(){
        bullets = new ArrayList<Bullet>();
        tanks = new Tank[4];
        int offSet = 20;
        tanks[0] = new Tank(350,offSet,180);
        tanks[1] = new Tank(350,ScreenConfig.SCREENY-offSet,0);
        tanks[2] = new Tank(offSet,350,90);
        tanks[3] = new Tank(ScreenConfig.SCREENX-offSet,350,270);
        players = new Player[4];
        //Generate Obsticles
//        obsticles = new ArrayList<>();
        obsticles = generateObsticles();



    }


    public void update(){
        for (int i =0; i < bullets.size(); i++){
            bullets.get(i).update();
            if(bullets.get(i).destoryFlag){
                bullets.remove(i);
                i--;
            }
        }
        for(int i = 0; i < obsticles.size(); i++){
            if(obsticles.get(i).destroyFlag){
                obsticles.remove(i);
                i--;
            }
        }
        collide();
    }

    public void draw(PApplet app){
        for (int i =0; i < bullets.size(); i++){
            bullets.get(i).draw(app);
        }

        for(int i = 0; i < 4; i++){
            tanks[i].draw(app);
        }

        for (int i = 0; i < obsticles.size(); i++){
            obsticles.get(i).draw(app);
        }
    }


    private void collide(){
        //TODO find a better way of having this linked list
        ArrayList<Collideable> collideables = new ArrayList<Collideable>();

        //Populate the linked list
//        //The player's tanks
        for(int i = 0; i < 4; i++){
            collideables.add(tanks[i]);
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
                if (Collideable.isColliding(collideables.get(i),collideables.get(j))){
                    int type1 = collideables.get(i).getType();
                    int type2 = collideables.get(j).getType();
                    if(type1 < type2){
                        type1 = type2;
                    }
                    collideables.get(i).collide(type1);
                    collideables.get(j).collide(type1);
                }
            }
        }
    }


    public void spawnBullet(Bullet bullet){
        if(bullet!= null) {
            bullets.add(bullet);
        }
    }

    public void addNewPlayer(Player p){
        players[playerCount] = p;
        p.setTank(tanks[playerCount]);
        playerCount++;
    }

    @Override
    public void run() {

    }

    private static final int maxOb = 50;
    private static final int minOb = 20;
    private ArrayList<Obsticle> generateObsticles(){
        ArrayList<Obsticle> obsticles = new ArrayList<>();
        int numObsticles = (int)(Math.random() *(maxOb - minOb + 1)) + minOb;
        //For collision checking
        ArrayList<Collideable> collideables = new ArrayList<Collideable>();
        //TODO add tanks to check
        for(int i = 0; i < 4 ; i++){
            collideables.add(tanks[i]);
        }


        for(int i = 0; i < numObsticles; i++){
            Obsticle temp = genObsticle(collideables);
            if (temp != null){
                obsticles.add(temp);
                collideables.add(temp);
            }
        }
        return obsticles;
    }

    private Obsticle genObsticle(ArrayList<Collideable> collideables){
        int timeOut = 20;
        Obsticle temp;
        for(int i = 0; i <timeOut; i++){
            int x = (int)(Math.random() * (ScreenConfig.SCREENX + 1));
            int y = (int)(Math.random() * (ScreenConfig.SCREENY + 1));
            int sizeX = (int)(Math.random() * (Obsticle.maxSizeX- Obsticle.minSizeX + 1)) + Obsticle.minSizeX;
            int sizeY = (int)(Math.random() * (Obsticle.maxSizeY- Obsticle.minSizeY + 1)) + Obsticle.minSizeY;
            temp = new Obsticle(x,y,sizeX,sizeY);
            collideables.add(temp);
            //Collision Check
            boolean valid = true;
            for (int j = 0; j < collideables.size()-1; j++){
                if (Collideable.isColliding(collideables.get(j),collideables.get(collideables.size()-1))){
                    valid = false;
                    collideables.remove(collideables.size()-1);
                    break;
                }
            }
            if (valid){
                return temp;
            }
        }
        return  null;
    }
}
