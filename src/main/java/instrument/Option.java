package instrument;

import java.time.Instant;

public class Option extends Instrument{

    private final Instant maturityDate;
    //Todo: convert volatility from static to realtime
    private final long strikePrice;

    public Option(String ticker,Instant maturityDate, long strikePrice) {
        super(ticker);
        this.maturityDate = maturityDate;
        this.strikePrice = strikePrice;
    }
}
