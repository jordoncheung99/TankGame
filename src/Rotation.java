public class Rotation {
    public int turnRate;
    public int angle;

    public Rotation(int rate, int ang){
        turnRate = rate;
        angle = ang;
    }

    public void turnLeft(){
        angle += turnRate;
        rotationBound();
    }

    public void turnRight(){
        angle -= turnRate;
        rotationBound();
    }

    private void rotationBound(){
        if(angle < 0){
            angle += 360;
        }else if (angle > 360){
            angle -= 360;
        }
    }
}
