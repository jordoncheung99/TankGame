import processing.core.PApplet;
import java.lang.Math;
public class BasicTurret extends Turret{

    BasicTurret() {
        super(10,150);
    }

    @Override
    public void drawTurret(PApplet app) {
        app.rect(0,0,20,20);
        app.translate(0,-25);
        app.rect(0,0,5,30);
    }


    @Override
    public Bullet fire(int rotation, float x, float y) {
        /*
        if(timmer > 0){
            return null;
        }
         */
        timmer = fireDelay;
        float dx = (float)Math.sin(Math.toRadians(rotation))*speed;
        float dy = (float)-Math.cos(Math.toRadians(rotation))*speed;
        return new Bullet((x+dx*3),(y+dy*3),dx,dy);
    }

    @Override
    public void update(){
        if(timmer > 0) {
            timmer--;
        }
    }


}
