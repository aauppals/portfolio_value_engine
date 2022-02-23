package instrument;

public class Stock extends Instrument {
    private final double volatility;

    public double getVolatility() {
        return volatility;
    }

    public Stock(String ticker, double volatility) {
        super(ticker);
        this.volatility = volatility;
    }
}
