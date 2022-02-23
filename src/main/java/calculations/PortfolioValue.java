package calculations;

public class PortfolioValue {
    private final String ticker;
    private final double value;

    //Todo: split value in to MV and NAV
    public PortfolioValue(String ticker, double value) {
        this.ticker = ticker;
        this.value = value;
    }

    public String getTicker() {
        return ticker;
    }

    public double getValue() {
        return value;
    }
}
