import processing.core.*;



public class Tank extends Rotateable implements Drawable{
    private Point origin;

    private Turret turret;
    PApplet app;
    private static final int sizeX = 30;
    private static final int sizeY = 45;
    private static final int moveSpeed = 2;
    public Tank(float x, float y, int deg,PApplet app){
        super(1,deg);
        origin = new Point(x,y);
        this.app = app;
        turret = new BasicTurret(app);
    }
    public void draw(){
        app.pushMatrix();
        app.translate(origin.x,origin.y);
        app.rotate(app.radians(rotation));
        drawTank();
        turret.draw();
        app.popMatrix();
    }

    private void drawTank(){
        app.rect(0,0,sizeX,sizeY);
    }

    public void turretTurnRight(){
        turret.turnRight();
    }

    public void turretTurnLeft(){
        turret.turnLeft();
    }

    public void fire(){
        System.out.println("pew");
    }

    public void forward(){
        move(true);
    }

    public void backward(){
        move(false);
    }

    private void move(boolean isForward){

        if(isForward) {
            origin.x += app.sin(app.radians(rotation)) * moveSpeed;
            origin.y -= app.cos(app.radians(rotation)) * moveSpeed;
        }else{
            origin.x -= app.sin(app.radians(rotation)) * moveSpeed;
            origin.y += app.cos(app.radians(rotation)) * moveSpeed;
        }
    }
}
