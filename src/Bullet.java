import processing.core.PApplet;

public class Bullet implements Drawable, Collideable{
    protected Dimention dimentions;
    protected Point deltaMovement;
    protected boolean destoryFlag = false;


    private static final int sizeX = 7;
    private static final int sizeY = 7;
    public Bullet(float x, float y, float dx, float dy){
        dimentions = new Dimention(x,y, sizeX, sizeY);
        deltaMovement = new Point(dx,dy);
    }

    public void update(){
        Point origin = dimentions.position;
        origin.x += deltaMovement.x;
        origin.y += deltaMovement.y;
        if (origin.x < -20 || origin.x > ScreenConfig.SCREENX+20 || origin.y < -20 || origin.y > ScreenConfig.SCREENY){
            destoryFlag = true;
        }
    }

    @Override
    public void draw(PApplet applet){
        applet.fill(255,0,0);
        applet.ellipse(dimentions.position.x,dimentions.position.y,dimentions.size.x,dimentions.size.y);
    }

    @Override
    public Point getSize() {
        return dimentions.size;
    }


    //TODO change bullets to actually have a rotation
    @Override
    public int getRotation(){
        return  0;
    }

    @Override
    public Point getOrigin() {
        return dimentions.position;
    }

    @Override
    public void collide(int Type) {
        destoryFlag = true;
    }

    private static final int type = 1;
    @Override
    public int getType(){
        return type;
    }
}
