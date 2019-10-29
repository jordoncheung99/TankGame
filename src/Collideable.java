import java.math.*;
public interface Collideable {
    //TODO fix so that it accounts for angles.
    public static boolean isColliding(Collideable c1, Collideable c2){
        //|Distance| < total size
        Point p1 = c1.getOrigin();
        Point p2 = c2.getOrigin();
        Point size1 = c1.getSize();
        Point size2 = c2.getSize();
        float distanceX = Math.abs(p1.x-p2.x);
        float distanceY = Math.abs(p1.y - p2.y);
        if (distanceX < (size1.x+size2.x)/2 && distanceY < (size1.y+size2.y)/2){
            return true;
        }
        return false;
    }

    public Point getSize();

    public int getRotation();

    public Point getOrigin();
    //TODO use an enum to do the type
    public void collide(int Type);


}
