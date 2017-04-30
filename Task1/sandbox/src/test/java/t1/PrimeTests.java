package t1;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Julia on 4/30/2017.
 */

@Test
public class PrimeTests {

    public void testPrimeTrue(){
        Assert.assertTrue(Primes.isPrime(12));
    }
}
