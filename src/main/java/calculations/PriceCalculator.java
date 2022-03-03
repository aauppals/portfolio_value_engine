package calculations;

import infra.UnaryPubSub;
import instrument.Option;
import instrument.OptionType;
import marketdata.PriceUpdate;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class PriceCalculator extends UnaryPubSub<Set<PriceUpdate>> implements Consumer<Set<PriceUpdate>> {
    static final double PRECISION_DECIMAL_PLACES = 0.001;
    private static final double INTEREST_RATE = 0.02;
    private static final double NUMBER_OF_SECONDS_IN_YEAR = 365 * 24 * 60 * 60;
    private final InstrumentDefinitionProvider instrumentDefinitionProvider;
    private final Positions positions;

    public PriceCalculator(Consumer<Set<PriceUpdate>> listener, InstrumentDefinitionProvider instrumentDefinitionProvider, Positions positions) {
        super(Collections.singletonList(listener));
        this.instrumentDefinitionProvider = instrumentDefinitionProvider;
        this.positions = positions;
    }

    @Override
    public void accept(Set<PriceUpdate> underlyingPriceUpdates) {
        final Set<PriceUpdate> optionPriceUpdates = new HashSet<>();
        underlyingPriceUpdates.forEach(priceUpdate -> optionPriceUpdates.addAll(calculatePrice(priceUpdate)));
        update(optionPriceUpdates);
    }

    private Set<PriceUpdate> calculatePrice(PriceUpdate underlyingPriceUpdate) {
        Set<PriceUpdate> optionPriceUpdates = new HashSet<>();
        Position underlyingPosition = positions.getPosition(underlyingPriceUpdate.getTicker());
        if (underlyingPosition == null) {
            throw new IllegalArgumentException("No options found in positions for underlying: " + underlyingPriceUpdate.getTicker());
        }
        optionPriceUpdates.add(underlyingPriceUpdate);
        List<Option> options = instrumentDefinitionProvider.getOptionByUnderlyingStock(underlyingPriceUpdate.getTicker());
        if (options == null) {
            throw new IllegalArgumentException("No options found in database for underlying: " + underlyingPriceUpdate.getTicker());
        }
        for (Option option : options) {
            if (positions.getPosition(option.getTicker()) != null) {
                if (option.getOptionType() == OptionType.CALL) {
                    double price = callPrice(underlyingPriceUpdate.getPrice(),
                            option.getStrikePrice(),
                            INTEREST_RATE, option.getUnderlyingStock().getVolatility(),
                            getYearsToMaturity(option.getMaturityDate()));
                    optionPriceUpdates.add(new PriceUpdate(option.getTicker(), price));
                } else if (option.getOptionType() == OptionType.PUT) {
                    double price = putPrice(underlyingPriceUpdate.getPrice(),
                            option.getStrikePrice(),
                            INTEREST_RATE, option.getUnderlyingStock().getVolatility(),
                            getYearsToMaturity(option.getMaturityDate()));
                    optionPriceUpdates.add(new PriceUpdate(option.getTicker(), price));
                } else {
                    throw new IllegalArgumentException("Unknown options type");
                }
            }
        }
        return optionPriceUpdates;
    }

    private double getYearsToMaturity(Instant maturityDate) {
        Instant now = Instant.now();
        Duration between = Duration.between(now, maturityDate);
        return between.get(ChronoUnit.SECONDS) / NUMBER_OF_SECONDS_IN_YEAR;
    }

    public double callPrice(double s, double k, double r, double sigma, double t) {
        double d1 = (Math.log(s / k) + (r + sigma * sigma / 2) * t) / (sigma * Math.sqrt(t));
        double d2 = d1 - sigma * Math.sqrt(t);
        return s * cumNormalDistProb(d1) - k * Math.exp(-r * t) * cumNormalDistProb(d2);
    }

    public double putPrice(double s, double k, double r, double sigma, double t) {
        double d1 = (Math.log(s / k) + (r + sigma * sigma / 2) * t) / (sigma * Math.sqrt(t));
        double d2 = d1 - sigma * Math.sqrt(t);
        return k * Math.exp(-r * t) * cumNormalDistProb(-d2) - s * cumNormalDistProb(-d1);
    }

    private double cumNormalDistProb(double x) {
        int neg = (x < 0d) ? 1 : 0;
        if (neg == 1)
            x *= -1d;

        double k = (1d / (1d + 0.2316419 * x));
        double y = ((((1.330274429 * k - 1.821255978) * k + 1.781477937) *
                k - 0.356563782) * k + 0.319381530) * k;
        y = 1.0 - 0.398942280401 * Math.exp(-0.5 * x * x) * y;
        return (1d - neg) * y + neg * (1d - y);
    }
}
