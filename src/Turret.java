import processing.core.*;


public abstract class Turret extends Rotateable implements Drawable{
    protected PApplet app;
    Turret(PApplet app){
        super(2,0);
        this.app = app;
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

}
