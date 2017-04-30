package t1;

/**
 * Created by Julia on 4/30/2017.
 */
public class Primes {
    public static boolean isPrime(int n){
        int i;
        for(i = 2; i < n/2 && n%i != 0; i++){
        }
        return i == n/2;
    }
}
