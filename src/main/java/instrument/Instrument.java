package instrument;

public abstract class Instrument {

    public String getTicker() {
        return ticker;
    }

    private final String ticker;


    protected Instrument(String ticker) {
        this.ticker = ticker;
    }
}
