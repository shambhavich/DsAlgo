public class BitManipulation {
    
    public static int offToOn(int n,int k){
        int mask = (1<<k);
        return (n | mask);
    }

    public static int onToOff(int n,int k){
        int mask = (~(1<<k));
        return (n & mask);
    }

    public static boolean isBitSet(int n,int k){
        int mask = (1<<k);
        return (n & mask) != 0;
    }

    //Find out number of set bits in a integer
    //Method 1
    public static int hammingWeight01(int n){
        int count = 0;

        for(int i=0;i<32;i++){
            int mask = (1<<i);
            if((n & mask) != 0) count++;
        }
        return count;
    }

    //Method 2
    public static int hammingWeight02(int n){
        int count = 0;
        int bitsCount = 0;
        while(n!=0 && bitsCount<32){
            if((n & 1) != 0)
                count++;
            n >>= 1;
            bitsCount++;
        }
        return count;
    }

    //Method 3
    public static int hammingWeight03(int n){
        int count = 0;
        while(n!=0){//This loops runs the amount of times the bit is set
            n &= (n-1); // Turns the first set bit to zero from lsb side
            count++;
        }
        return count;
    }

    
}
