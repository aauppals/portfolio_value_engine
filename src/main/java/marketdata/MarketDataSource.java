package marketdata;

import infra.UnaryPubSub;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class MarketDataSource extends UnaryPubSub<Set<PriceUpdate>> implements Runnable {

    public MarketDataSource(List<Consumer<Set<PriceUpdate>>> listeners) {
        super(listeners);
    }

    @Override
    public void run() {
        System.out.println("new market update");
        //Todo: every x seconds update
        update(new HashSet<>());
    }
}
