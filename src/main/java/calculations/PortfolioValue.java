package calculations;

import instrument.Instrument;

import java.util.Objects;

public class PortfolioValue {
    private final Instrument instrument;
    private final double value;
    private final Positions positions;
    private final double price;

    //Todo: split value in to MV and NAV
    public PortfolioValue(Instrument instrument, double value, Positions positions, double price) {
        this.instrument = instrument;
        this.value = value;
        this.positions = positions;
        this.price = price;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public double getValue() {
        return value;
    }

    public Positions getPositions() {
        return positions;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "PortfolioValue{" +
                "instrument=" + instrument +
                ", value=" + value +
                ", positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PortfolioValue)) return false;
        PortfolioValue that = (PortfolioValue) o;
        return Double.compare(that.value, value) == 0 && instrument.equals(that.instrument) && positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, value, positions);
    }
}


