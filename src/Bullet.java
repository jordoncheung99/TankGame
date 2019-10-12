import processing.core.PApplet;

public class Bullet implements Drawable, Collideable{
    protected Point origin;
    protected Point deltaMovement;
    protected Point size;
    protected PApplet app;
    protected boolean destoryFlag = false;

    public Bullet(float x, float y, float dx, float dy, PApplet applet){
        origin = new Point(x,y);
        deltaMovement = new Point(dx,dy);
        app = applet;
        size = new Point(5,5);
    }

    public void update(){
        origin.x += deltaMovement.x;
        origin.y += deltaMovement.y;
        if (origin.x < -20 || origin.x > (Game.sizeX+20) || origin.y < -20 || origin.y > (Game.sizeY+20)){
            destoryFlag = true;
        }
    }

    @Override
    public void draw(){
        app.ellipse(origin.x,origin.y,size.x,size.y);
    }

    @Override
    public Point getSize() {
        return size;
    }


    //TODO change bullets to actually have a rotation
    @Override
    public int getRotation(){
        return  0;
    }

    @Override
    public Point getOrigin() {
        return origin;
    }

    @Override
    public void collide(int Type) {
        destoryFlag = true;
    }
}
