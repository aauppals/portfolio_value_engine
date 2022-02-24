package calculations;

import instrument.Instrument;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return amount == position.amount && instrument.equals(position.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, instrument);
    }

    @Override
    public String toString() {
        return "Position{" +
                "amount=" + amount +
                ", instrument=" + instrument +
                '}';
    }
}
