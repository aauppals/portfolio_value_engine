package instrument;

import java.util.Objects;

public abstract class Instrument {

    private final String ticker;

    protected Instrument(String ticker) {
        this.ticker = ticker;
    }


    public String getTicker() {
        return ticker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument)) return false;
        Instrument that = (Instrument) o;
        return ticker.equals(that.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "ticker='" + ticker + '\'' +
                '}';
    }
}
