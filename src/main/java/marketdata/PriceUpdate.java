package marketdata;

import calculations.InstrumentDefinitionProvider;
import instrument.Instrument;

import java.util.Objects;

public class PriceUpdate {
    private final String ticker;
    private final double price;

    public PriceUpdate(String ticker, double price) {
        this.ticker = ticker;
        this.price = price;
    }

    public String getTicker() { return ticker;}

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "PriceUpdate{" +
                "ticker='" + ticker + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceUpdate)) return false;
        PriceUpdate that = (PriceUpdate) o;
        return Double.compare(that.price, price) == 0 && ticker.equals(that.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, price);
    }
}

