package marketdata;

import calculations.InstrumentDefinitionProvider;
import calculations.Positions;
import infra.UnaryPubSub;

import java.util.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class MarketDataSource extends UnaryPubSub<Set<PriceUpdate>> {
    public static final int HIGH = 300;
    public static final int LOW = 200;
    public static final Random RANDOM_PRICE_GENERATOR = new Random();
    public final Set<PriceUpdate> priceUpdates = new LinkedHashSet<>();
    private final Positions positions;
    private final InstrumentDefinitionProvider instrumentDefinitionProvider;

    public MarketDataSource(List<Consumer<Set<PriceUpdate>>> listeners, Positions positions, InstrumentDefinitionProvider instrumentDefinitionProvider) {
        super(listeners);
        this.positions = positions;
        this.instrumentDefinitionProvider = instrumentDefinitionProvider;
    }

    public void start() {
        for (int i = 0; i < 10; i++) {
            LockSupport.parkNanos(2000000000);
            System.out.println("New market update for below stock(s)");
            update(stockPriceMovement());
        }
    }

    public Set<PriceUpdate> stockPriceMovement() {
        final ArrayList<String> tickers = instrumentDefinitionProvider.getAllStockTickers();
        priceUpdates.clear();
        tickers.forEach(t -> {
            PriceUpdate priceUpdate = new PriceUpdate(t, RANDOM_PRICE_GENERATOR.nextInt(HIGH - LOW) + LOW);
            priceUpdates.add(priceUpdate);
            String ticker = priceUpdate.getTicker();
            double price = priceUpdate.getPrice();
            System.out.printf("%-30.30s  %-30.30s%n", ticker, price);
        });
        return priceUpdates;
    }

}


