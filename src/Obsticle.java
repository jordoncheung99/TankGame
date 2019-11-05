import processing.core.*;
import java.lang.Math;
public class Obsticle implements Drawable,Collideable{
    Point origin;
    Point size;
    int health;
    boolean destroyFlag;

    public static final int maxSizeX = 100;
    public static final int maxSizeY = 100;
    public static final int minSizeX = 20;
    public static final int minSizeY = 20;
    private static final int maxHealth = 2;

    public Obsticle(int x, int y){
        origin = new Point(x,y);
        size = new Point(100,100);
        health = maxHealth;
        destroyFlag = false;
    }

    public Obsticle(int x, int y, int sizeX, int sizeY){
        origin = new Point(x,y);
        size = new Point(sizeX,sizeY);
        health = maxHealth;
        destroyFlag = false;
    }

    public Point getSize(){
        return size;
    }

    @Override
    public int getRotation() {
        return 0;
    }

    @Override
    public Point getOrigin() {
        return origin;
    }

    @Override
    public void collide(int Type) {
        if(Type == 1){
            health--;
            if (health <= 0){
                destroyFlag = true;
            }
        }
    }

    @Override
    public void draw(PApplet app){
        app.fill(255-125*health,125*health,0);
        app.rect(origin.x,origin.y,size.x,size.y);
    }

    private static final int type = 0;
    @Override
    public int getType(){
        return type;
    }

}
