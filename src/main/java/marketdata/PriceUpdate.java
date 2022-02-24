package marketdata;

import instrument.Instrument;

import java.util.Objects;

public class PriceUpdate {
    private final Instrument instrument;
    private final double price;

    public PriceUpdate(Instrument ticker, double price) {
        this.instrument = ticker;
        this.price = price;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceUpdate that = (PriceUpdate) o;
        return Double.compare(that.price, price) == 0 && instrument.equals(that.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, price);
    }

    @Override
    public String toString() {
        return "PriceUpdate{" +
                "instrument=" + instrument +
                ", price=" + price +
                '}';
    }
}
