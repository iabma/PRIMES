public class PrimeSet {
    private int[] index;

    private long prime;
    private int amount;

    public PrimeSet(int size) {
        prime = 0;
        amount = 0;
        index = new int[size];
    }

    public long getPrime() {
        return prime;
    }

    public void setPrime(long prime) {
        this.prime = prime;
    }

    public void add(int i, int index) {
        amount++;
        this.index[i] = index;
    }

    public int amount() {
        return amount;
    }
}
