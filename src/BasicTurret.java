import processing.core.PApplet;

public class BasicTurret extends Turret{

    BasicTurret(PApplet app) {
        super(app);
    }

    @Override
    public void drawTurret() {
        app.rect(0,0,20,20);
        app.translate(0,-25);
        app.rect(0,0,5,30);
    }
}
