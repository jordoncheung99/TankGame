import java.math.*;
public interface Collideable {
    //TODO fix so that it accounts for angles.
    public static boolean isColliding(Point p1,Point size1,int rot1, Point p2,Point size2, int rot2){
        //|Distance| < total size
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
