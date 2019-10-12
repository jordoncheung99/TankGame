import processing.core.PApplet;
public class BasicTurret extends Turret{

    BasicTurret(PApplet app) {
        super(app, 10,150);
    }

    @Override
    public void drawTurret() {
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
        float dx = app.sin(app.radians(rotation))*speed;
        float dy = -app.cos(app.radians(rotation))*speed;
        return new Bullet((x+dx*3),(y+dy*3),dx,dy,app);
    }

    @Override
    public void update(){
        if(timmer > 0) {
            timmer--;
        }
    }


}
