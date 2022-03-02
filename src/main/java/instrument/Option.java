package instrument;

import java.time.Instant;
import java.util.Objects;

public class Option extends Instrument {

    private final Instant maturityDate;
    //Todo: convert volatility from static to realtime
    private final double strikePrice;
    private final OptionType optionType;
    private final Stock underlyingStock;

    public Option(String ticker, Instant maturityDate, double strikePrice, OptionType optionType, Stock underlyingStock) {
        super(ticker);
        this.maturityDate = maturityDate;
        this.strikePrice = strikePrice;
        this.optionType = optionType;
        this.underlyingStock = underlyingStock;
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
                "maturityDate='" + maturityDate + '\'' +
                ", strikePrice=" + strikePrice +
                ", optionType=" + optionType +
                ", underlyingStock='" + underlyingStock + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        if (!super.equals(o)) return false;
        Option option = (Option) o;
        return Double.compare(option.strikePrice, strikePrice) == 0 && maturityDate.equals(option.maturityDate) && optionType == option.optionType && underlyingStock.equals(option.underlyingStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maturityDate, strikePrice, optionType, underlyingStock);
    }

    public Stock getUnderlyingStock() {
        return underlyingStock;
    }
}
