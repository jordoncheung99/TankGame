import processing.core.*;
public class Obsticle implements Drawable,Collideable{
    Point orgin;
    Point size;
    PApplet app;
    int health;
    public Obsticle(int x, int y, PApplet pApplet){
        orgin = new Point(x,y);
        app = pApplet;
        size = new Point(app.random(50,250),app.random(50,250));
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
    public void draw(){
        app.rect(orgin.x,orgin.y,size.x,size.y);
    }

}
