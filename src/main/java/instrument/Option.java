package instrument;

import java.time.Instant;
import java.util.Objects;

public class Option extends Instrument{

    private final Instant maturityDate;
    //Todo: convert volatility from static to realtime
    private final double strikePrice;
    private final OptionType optionType;
    private final Stock underlying;

    public Option(String ticker, Instant maturityDate, double strikePrice, OptionType optionType, Stock underlying) {
        super(ticker);
        this.maturityDate = maturityDate;
        this.strikePrice = strikePrice;
        this.optionType = optionType;
        this.underlying = underlying;
    }

    public Instant getMaturityDate() {
        return maturityDate;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    @Override
    public String toString() {
        return "Option{" +
                "maturityDate=" + maturityDate +
                ", strikePrice=" + strikePrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return strikePrice == option.strikePrice && maturityDate.equals(option.maturityDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maturityDate, strikePrice);
    }
}
