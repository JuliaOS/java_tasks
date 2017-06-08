package t1;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Julia on 4/15/2017.
 */
@Test
public class SquareTests {
    public void testArea(){
        Square s = new Square(5);
        Assert.assertEquals(s.area(), 25.0);
    }
}
