import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameLobby implements Runnable{

    ArrayList<Bullet> bullets;
    ArrayList<Obsticle> obsticles;
    LinkedList<Text> texts;
    ArrayList<Tank> tanks;
    ArrayList<Player> players;
    int playerCount = 0;

    public GameLobby(){
        bullets = new ArrayList<Bullet>();
        tanks = new ArrayList<>();
        int offSet = 20;
        tanks.add( new Tank(350,offSet,180));
        tanks.add(new Tank(350,ScreenConfig.SCREENY-offSet,0));
        tanks.add( new Tank(offSet,350,90));
        tanks.add(new Tank(ScreenConfig.SCREENX-offSet,350,270));
        players = new ArrayList<>(4);
        //Generate Obsticles
        obsticles = generateObsticles();
        texts = new LinkedList<>();

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

        for(int i = 0; i < texts.size(); i++){
            Text temp = texts.get(i);
            temp.timeout++;
            if(temp.timeout >= temp.maxTimeOut){
                texts.remove(i);
                i--;
            }
        }

        for(int i = 0; i < tanks.size(); i++){
            Tank tank = tanks.get(i);
            if(tank.health<=0){
                tanks.remove(i);
                i--;
            }
        }

        collide();
    }

    public void draw(PApplet app){
        for (int i =0; i < bullets.size(); i++){
            bullets.get(i).draw(app);
        }

        for(int i = 0; i < tanks.size(); i++){
            tanks.get(i).draw(app);
        }

        for (int i = 0; i < obsticles.size(); i++){
            obsticles.get(i).draw(app);
        }

        for(int i = 0; i < texts.size(); i++){
            app.fill(255);
            Text text = texts.get(i);
            app.text(text.text, text.origin.x, text.origin.y);
        }

    }


    private void collide(){
        //TODO find a better way of having this linked list
        ArrayList<Collideable> collideables = new ArrayList<Collideable>();

        //Populate the linked list
//        //The player's tanks
        for(int i = 0; i < tanks.size(); i++){
            collideables.add(tanks.get(i));
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
        players.add(p);
        p.setTank(tanks.get(playerCount));
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
        for(int i = 0; i < tanks.size() ; i++){
            collideables.add(tanks.get(i));
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

    public void addText(String text, Point origin){
        texts.add(new Text(text,origin));
    }
}
