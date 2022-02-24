package calculations;

import instrument.Instrument;

public class PortfolioValue {
    private final Instrument instrument;
    private final double value;

    //Todo: split value in to MV and NAV
    public PortfolioValue(Instrument instrument, double value) {
        this.instrument = instrument;
        this.value = value;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public double getValue() {
        return value;
    }
}
