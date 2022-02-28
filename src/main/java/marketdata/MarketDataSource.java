package marketdata;

import java.util.concurrent.ThreadLocalRandom;

import calculations.CalcObject;
import calculations.InstrumentDefinitionProvider;
import calculations.Positions;
import infra.UnaryPubSub;

import java.util.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class MarketDataSource extends UnaryPubSub<Set<PriceUpdate>> implements Runnable {
    private final Positions positions;

    public MarketDataSource(List<Consumer<Set<PriceUpdate>>> listeners, Positions positions) {
        super(listeners);
        this.positions = positions;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            LockSupport.parkNanos(2000000000);
            System.out.println("new market update");
            //Todo: every x seconds update
            update(stockPriceMovement());
        }
    }

    HashSet<PriceUpdate> priceUpdates = new HashSet<>();

    public HashSet<PriceUpdate> stockPriceMovement() {
        ArrayList<String> tickers = InstrumentDefinitionProvider.getAllStockTickers("Instruments.db");
        tickers.forEach(t -> {
            CalcObject calcObject = InstrumentDefinitionProvider.getStockByTicker("Instruments.db", t);
            PriceUpdate priceUpdate = new PriceUpdate(t, calc(calcObject));
            priceUpdates.add(priceUpdate);
            System.out.println(priceUpdate);
        });
        return priceUpdates;
    }

    public double calc(CalcObject calcObject) {
        double delta_t = 2.0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double epsilon = random.nextDouble(-1.0, 1.0);
        double price = calcObject.getPrice();
        double expectedReturn = calcObject.getExpectedReturn();
        double volatility = calcObject.getVolatility();
        double delta_s = ((expectedReturn * ((delta_t) / 7257600)) + (volatility * epsilon * (Math.sqrt(delta_t / 7257600)))) * price;
        double new_price = price + delta_s;
        new_price = Math.round(new_price * 100.0) / 100.0;
        return new_price;

    }

}


