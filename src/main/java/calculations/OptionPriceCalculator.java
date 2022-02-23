package calculations;

import infra.UnaryPubSub;
import marketdata.PriceUpdate;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

public class OptionPriceCalculator extends UnaryPubSub<Set<PriceUpdate>> implements Consumer<Set<PriceUpdate>> {

    public OptionPriceCalculator(PortfolioValueCalculator listener) {
        super(Collections.singletonList(listener));
    }

    @Override
    public void accept(Set<PriceUpdate> priceUpdates) {
        //Todo: calculate and return price of instrument
        System.out.println("new option prices update");

        update(priceUpdates);
    }
}
