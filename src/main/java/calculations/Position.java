package calculations;

import instrument.Instrument;

public class Position {

    private final long amount;
    private final Instrument instrument;

    public Position(long amount, Instrument instrument) {
        this.amount = amount;
        this.instrument = instrument;
    }

    public long getAmount() {
        return amount;
    }

    public Instrument getInstrument() {
        return instrument;
    }
}
