import processing.core.*;



public class Tank extends Rotateable implements Drawable,Collideable{


    private Point origin;
    private Turret turret;
    PApplet app;
    private static final Point size = new Point(30,45);
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
        turret.update();
        drawTank();
        turret.draw();
        app.popMatrix();
    }

    public Point getSize(){
        return  size;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

    @Override
    public Point getOrigin() {
        return origin;
    }

    @Override
    public void collide(int Type) {

    }

    private void drawTank(){
        app.rect(0,0,size.x,size.y);
    }

    public void turretTurnRight(){
        turret.turnRight();
    }

    public void turretTurnLeft(){
        turret.turnLeft();
    }

    public Bullet fire(){
        int r3 = rotationCombine(rotation,turret.getRotation());
        return turret.fire(r3,origin.x,origin.y);
    }

    private int rotationCombine(int r1, int r2){
        int r3 = r1+r2;
        if(r3 > 360){
            r3 -= 360;
        }else if(r3 < 0){
            r3 += 360;
        }
        return r3;
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
