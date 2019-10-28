import processing.core.*;


public abstract class Turret extends Rotateable implements Drawable{
    protected int speed;
    protected int fireDelay;
    protected int timmer;
    Turret(int speed,int delay){
        super(2,0);
        this.speed = speed;
        fireDelay = delay;
    }

    public void draw(PApplet app){
        app.pushMatrix();
        app.rotate(app.radians(rotation));
        drawTurret(app);
        app.popMatrix();
    }

    public abstract void drawTurret(PApplet app);


    public int getRotation(){
        return rotation;
    }

    public abstract Bullet fire(int rotation, float x, float y);

    public abstract void update();
}
