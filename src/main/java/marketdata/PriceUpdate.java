package marketdata;

import instrument.Instrument;

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
}
