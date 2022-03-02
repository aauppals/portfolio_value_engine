package instrument;

public class Stock extends Instrument {
    private final double volatility;
    public Stock(String ticker, double volatility) {
        super(ticker);
        this.volatility = volatility;
    }

    public double getVolatility() {
        return volatility;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
