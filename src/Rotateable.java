public abstract class Rotateable {
    protected int turnRate;
    //In degrees
    protected int rotation;

    Rotateable(int turnRate, int rotation){
        this.turnRate = turnRate;
        this.rotation = rotation;
    }

    public void turnLeft(){
        rotation -= turnRate;
        rotationFloor();
    }

    public void turnRight(){
        rotation += turnRate;
        rotationFloor();
    }

    private void rotationFloor(){
        if(rotation > 360){
            rotation = 0;
        }else if(rotation < 0){
            rotation = 360;
        }
    }
}
