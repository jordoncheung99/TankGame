import processing.core.*;


public abstract class Turret extends Rotateable implements Drawable{
    protected PApplet app;
    protected int speed;
    protected int fireDelay;
    protected int timmer;
    Turret(PApplet app, int speed,int delay){
        super(2,0);
        this.app = app;
        this.speed = speed;
        fireDelay = delay;
    }

    public void draw(){
        app.pushMatrix();
        app.rotate(app.radians(rotation));
        drawTurret();
        app.popMatrix();
    }

    public abstract void drawTurret();


    public int getRotation(){
        return rotation;
    }

    public abstract Bullet fire(int rotation, float x, float y);

    public abstract void update();
}
