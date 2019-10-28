import processing.core.*;
import java.lang.Math;
public class Obsticle implements Drawable,Collideable{
    Point orgin;
    Point size;
    int health;
    public Obsticle(int x, int y){
        orgin = new Point(x,y);
        size = new Point(100,150);
        health = 100;
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
        return orgin;
    }

    @Override
    public void collide(int Type) {

    }

    @Override
    public void draw(PApplet app){
        app.rect(orgin.x,orgin.y,size.x,size.y);
    }

}
