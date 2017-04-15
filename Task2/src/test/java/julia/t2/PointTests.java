package julia.t2;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Julia on 4/15/2017.
 */
@Test
public class PointTests {

    public void testDistance(){
        Point p1 = new Point(1,2);
        Point p2 = new Point(1,2);
        double dCorrect = 0.0;
        double dNegative = -1.0;
        double dIncorrect = 1.0;
        //This block should pass
        Assert.assertEquals(p1.distance(p2), dCorrect);
        //This block should fail
        //Assert.assertEquals(p1.distance(p2), dNegative);
        //Assert.assertEquals(p1.distance(p2), dIncorrect);
    }
}
