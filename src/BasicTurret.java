import processing.core.PApplet;
import java.lang.Math;
public class BasicTurret extends Turret{

    int clip;
    int maxClip = 3;
    BasicTurret() {
        super(10,75);
    }

    @Override
    public void drawTurret(PApplet app) {
        app.rect(0,0,15,15);
        app.translate(0,-15);
        app.rect(0,0,3,20);
    }


    @Override
    public Bullet fire(int rotation, float x, float y) {

        if (clip <= 0){
            return null;
        }
        clip --;
        float dx = (float)Math.sin(Math.toRadians(rotation))*speed;
        float dy = (float)-Math.cos(Math.toRadians(rotation))*speed;
        return new Bullet((x+dx*2),(y+dy*2),dx,dy);
    }

    @Override
    public void update(){
        if(timmer > 0 && clip < maxClip) {
            timmer--;
        }else if(timmer == 0 && clip < maxClip){
            clip++;
            timmer = fireDelay;
        }


    }


}
