package calculations;

import instrument.Instrument;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "PortfolioValue{" +
                "instrument=" + instrument +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PortfolioValue)) return false;
        PortfolioValue that = (PortfolioValue) o;
        return Double.compare(that.value, value) == 0 && instrument.equals(that.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, value);
    }
}


