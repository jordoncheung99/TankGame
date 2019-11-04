import java.math.*;
public interface Collideable {
    //TODO fix so that it accounts for angles.
    public static boolean isColliding(Collideable c1, Collideable c2){
        //|Distance| < total size
        Point p1 = c1.getOrigin();
        Point p2 = c2.getOrigin();
        Point size1 = c1.getSize();
        Point size2 = c2.getSize();
        double r1 = c1.getRotation()*Math.PI/180;
        double r2 = c2.getRotation()*Math.PI/180;
        double rotatedX1 = (float)Math.sin(r1) * p1.x;
        double rotatedY1 = (float)Math.cos(r1) * p1.y;
//        System.out.println(p1.x);
//        System.out.println(p1.y);
//        System.out.println(rotatedX1);
//        System.out.println(rotatedY1);
        double distanceX = Math.abs(p1.x-p2.x);
        double distanceY = Math.abs(p1.y - p2.y);
        if (distanceX < (size1.x+size2.x)/2 && distanceY < (size1.y+size2.y)/2){
            return true;
        }
        return false;
    }

    public Point getSize();

    public int getRotation();

    public int getType();

    public Point getOrigin();
    //TODO use an enum to do the type
    //0 = Not bullet
    //1 = Bullet
    public void collide(int Type);


}
