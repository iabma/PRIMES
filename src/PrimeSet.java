public class PrimeSet {
    private long prime;
    private int amount;

    public PrimeSet() {
        prime = 0;
        amount = 0;
    }

    public void setPrime(long prime) {
        this.prime = prime;
    }

    public void add() {
        amount++;
    }

    public int amount() {
        return amount;
    }
}
