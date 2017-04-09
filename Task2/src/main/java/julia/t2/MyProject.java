package julia.t2;

/**
 * Created by Julia on 4/9/2017.
 */
public class MyProject {
    public static void main(String[] args){
        Point p1 = new Point(-1, 3);
        Point p2 = new Point(2, 3);
        //System.out.println("Distance between 2 points = " + distance(p1, p2));
        System.out.println("Distance between 2 points = " + p1.distance(p2));
    }

    // This function calculates distance between 2 points
    /*public static double distance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }*/
}
